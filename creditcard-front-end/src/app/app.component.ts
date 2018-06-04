import {Component, OnInit} from '@angular/core';
import {LoginService} from "./login/login.service";
import {Platform} from "@angular/material";

@Component({
  selector: 'app',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css'],
  providers: [ Platform, LoginService ]
})
export class AppComponent {
  constructor(private _platform: Platform,
              private _appLoginService: LoginService) {

    if (localStorage.getItem('token')) {
      localStorage.removeItem('token');
    }
  }


  get loggedIn(): boolean {
    return this._appLoginService.isLoggedIn();
  }


  get isAdmin(): boolean {
    return this._appLoginService.isAdmin();
  }


  onLogout() {
    this._appLoginService.logout();
  }
}
