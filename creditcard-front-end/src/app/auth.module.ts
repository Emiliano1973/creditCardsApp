import {Http, RequestOptions} from "@angular/http";
import {AuthHttp, AuthConfig} from "angular2-jwt";
import {NgModule} from "@angular/core";

const headers = {
  'Content-Type': 'application/json',
  'Cache-Control': 'no-cache',
  'X-Requested-With': 'XMLHttpRequest'
}

export function authHttpServiceFactory(http: Http, options: RequestOptions) {
  return new AuthHttp(new AuthConfig({
    tokenName: 'token',
    tokenGetter: (() => localStorage.getItem('token')),
    globalHeaders: [headers]
  }), http, options);
}
@NgModule({
  providers: [{
    provide: AuthHttp,
    useFactory: authHttpServiceFactory,
    deps: [Http, RequestOptions]
  }]
})

export class AuthModule { }
