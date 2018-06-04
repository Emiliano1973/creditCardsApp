
import {Injectable} from "@angular/core";
import {Headers, Http, RequestOptions, Response} from "@angular/http";
import {Observable} from "rxjs/Observable";
import 'rxjs/add/operator/toPromise';
import 'rxjs/add/operator/share';
import 'rxjs/add/observable/timer';
import 'rxjs/add/observable/interval';

import * as glob from "./../globals"
import {User} from "./user-model";



@Injectable()
export class UserService {

  private readonly URL: string = glob.BACKENDURL + '/ext/users';
  private readonly headers = new Headers({'Content-Type': 'application/json', 'Cache-Control': 'no-cache', 'X-Requested-With': 'XMLHttpRequest' });
  constructor(private http:Http) {

  }


  addUser(user:User): Observable<any>{
    let options = new RequestOptions();
    options.headers = this.headers;
    options.body = user;

    return this.http.post(this.URL, user, options).map((response: Response) => { let body = response.json();
      return body || { };
    });
  }

}
