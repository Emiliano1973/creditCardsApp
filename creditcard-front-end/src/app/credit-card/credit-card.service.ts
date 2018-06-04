
import {Injectable} from "@angular/core";
import {Headers,  RequestOptions, Response} from "@angular/http";
import {Observable} from "rxjs/Observable";
import 'rxjs/add/operator/toPromise';
import 'rxjs/add/operator/share';
import 'rxjs/add/observable/timer';
import 'rxjs/add/observable/interval';

import * as glob from "./../globals"

import {CreditCard} from "./credit-card-model";
import {AppAuthHttpService} from "../app-auth-http.service";

@Injectable()
export class CreditCardService {
  private readonly URL: string = glob.BACKENDURL + '/creditcards';
  private readonly headers = new Headers({'Content-Type': 'application/json', 'Cache-Control': 'no-cache', 'X-Requested-With': 'XMLHttpRequest' });


  constructor (private _appAuthHttp: AppAuthHttpService) {}

  addCreditCard(creditCard:CreditCard): Observable<any>{
    let options = new RequestOptions();
    options.headers = this.headers;
    options.body = creditCard;

    return this._appAuthHttp.post(this.URL, creditCard, options).map((response: Response) => { let body = response.json();
      return body || { };});
  }


  updateCreditCard(id:number,creditCard:CreditCard): Observable<any>{
    let options = new RequestOptions();
    options.headers = this.headers;
    options.body = creditCard;

    return this._appAuthHttp.put(this.URL+"/"+id, creditCard, options).map((response: Response) => { let body = response.json();
      return body || { };});
  }


  getCreditCards(cardCode?:string): Observable<any>{
    let par:string="";
    if(cardCode){
      par="?searchLike="+cardCode;
    }

    return  this._appAuthHttp
      .get(this.URL + par)
      .map((response: Response) => { let body = response.json();
        return body || { };});
  }

  getCreditCardByCode(id:number): Observable<any>{
    let par:string="";

    return  this._appAuthHttp
      .get(this.URL+"/" + id)
      .map((response: Response) => { let body = response.json();
        return body || { };});
  }

  deleteCreditCard(id: number): Observable<any> {
    let uri: string = this.URL + "/" + id;
    return this._appAuthHttp.delete(uri).map((response: Response) => { let body = response.json();
      return body || { };});;
  }

}
