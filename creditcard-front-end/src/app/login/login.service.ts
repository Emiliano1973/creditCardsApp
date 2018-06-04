
import {Injectable} from "@angular/core";
import {Headers, Http, RequestOptions, Response} from "@angular/http";
import {Observable} from "rxjs/Observable";
import {AuthHttp, JwtHelper} from "angular2-jwt";
import 'rxjs/add/operator/map';
import 'rxjs/add/operator/toPromise';
import 'rxjs/add/operator/share';
import 'rxjs/add/observable/timer';
import 'rxjs/add/observable/interval';
import {Router} from "@angular/router";

import * as glob from "./../globals"

@Injectable()
export class LoginService {
  private token: string;
  refreshSubscription: any;
  private refreshing: boolean = false;
  private jwtHelper: JwtHelper = new JwtHelper();
  private headers = new Headers({'Content-Type': 'application/json', 'Cache-Control': 'no-cache', 'X-Requested-With': 'XMLHttpRequest' });

  constructor (private _http: Http, private _router: Router, private _authHttp: AuthHttp ) {
    let currentUser = JSON.parse(localStorage.getItem('currentUser'));
    this.token = currentUser && currentUser.token;
  }

  isRefreshing(): boolean {
    return this.refreshing;
  }



  isAdmin(): boolean {
    const token = localStorage.getItem('token');
    if(token) {
      let decodedToken: any = this.jwtHelper.decodeToken(token);
      if(decodedToken.scopes[0] === 'ROLE_ADMIN') {
        return true;
      }
    }
    return false;
  }


  isLoggedIn() {
    return ((localStorage.getItem('token')) && (!this.jwtHelper.isTokenExpired(localStorage.getItem('token'))));
  }


  public scheduleRefresh() {
    // If the user is authenticated, use the token stream
    // provided by angular2-jwt and flatMap the token

    let source = this._authHttp.tokenStream.flatMap(
      token => {
        // The delay to generate in this case is the difference
        // between the expiry time and the issued at time
        let jwtIat = this.jwtHelper.decodeToken(token).iat;
        let jwtExp = this.jwtHelper.decodeToken(token).exp;
        let iat = new Date(0);
        let exp = new Date(0);
        let delay = (exp.setUTCSeconds(jwtExp) - iat.setUTCSeconds(jwtIat));

        return Observable.interval(delay);
      });

    this.refreshSubscription = source.subscribe(() => {
      this.refreshing = true;
      this.refreshToken();

    });
  }

  public startupTokenRefresh() {
    // If the user is authenticated, use the token stream
    // provided by angular2-jwt and flatMap the token
    if (this.isLoggedIn()) {
      let source = this._authHttp.tokenStream.flatMap(
        token => {
          // Get the expiry time to generate
          // a delay in milliseconds
          let now: number = new Date().valueOf();
          let jwtExp: number = this.jwtHelper.decodeToken(token).exp;
          let exp: Date = new Date(0);
          exp.setUTCSeconds(jwtExp);
          let delay: number = exp.valueOf() - now;

          // Use the delay in a timer to
          // run the refresh at the proper time
          return Observable.timer(delay);
        });
      // Once the delay time from above is
      // reached, get a new JWT and schedule
      // additional refreshes
      source.subscribe(() => {
        this.refreshing = true;
        this.refreshToken();
        this.scheduleRefresh();
      });
    }
  }

  public unscheduleRefresh() {
    // Unsubscribe fromt the refresh
    if (this.refreshSubscription) {
      this.refreshSubscription.unsubscribe();
    }
  }

  login(username: string, password: string): Observable<any> {
    let options = new RequestOptions({headers: this.headers});
    let body = JSON.stringify({
      'username': username,
      'password': password});

    return this._http.post(glob.BACKENDURL + '/auth/login', body, options)
      .map((response: Response) => {
        let token = response.json().token;
        let refreshToken = response.json().refreshToken;

        if (token) {
          this.token = token;
          localStorage.setItem('token', token);
          localStorage.setItem('refreshToken', refreshToken);
          this.scheduleRefresh();
          return {status: 200, error: '', errorCode: null, success: true};
        } else {
          return {status: response.json().body.status, error: response.json().body.message, errorCode: response.json().body.errorCode, success: false};
        }
      })
  }

  logout() {
    this.token = null;
    localStorage.removeItem('token');
    localStorage.removeItem('refreshToken');
    this._router.navigate(['/login']);
  }

  refreshToken(){
    let options = new RequestOptions();

    if (this.headers.has('Authorization')){
      this.headers.delete('Authorization');
    }
    this.headers.append('Authorization', 'Bearer ' + localStorage.getItem('refreshToken'));
    options.headers = this.headers;

    this._http.get(glob.BACKENDURL + '/auth/token', options)
      .subscribe((response: Response) => {
          let token = response.json().token;

          if (token) {
            this.token = token;
            localStorage.setItem('token', token);
          }
          this.refreshing = false;
        },
        err => {
          if(err.status == 401) {
            this._router.navigate(['/login']);
          }
        })
  }
}
