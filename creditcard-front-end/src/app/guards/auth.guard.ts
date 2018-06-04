/**
 * Created by Emiliano Echinofora.
 */

import { Injectable } from '@angular/core';
import {Router, CanActivate, CanActivateChild} from '@angular/router';
import {JwtHelper } from "angular2-jwt";
import {LoginService} from "../login/login.service";

@Injectable()
export class AuthGuard implements CanActivate, CanActivateChild {

    private jwtHelpter: JwtHelper = new JwtHelper();
    constructor(private router: Router, private _loginService: LoginService) { }

    canActivate() {
/*      return true;*/
        if(this._loginService.isLoggedIn()) {
          return true
        }

        // not logged in so redirect to login page
        this.router.navigate(['/login']);
        return false;
    }

    canActivateChild() {
/*      return true;*/
      return this.canActivate();
    }

}
