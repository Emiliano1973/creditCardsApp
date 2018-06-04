import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { LoginComponent } from './login.component';
import {LoginService} from "./login.service";
import {MaterialModule} from "@angular/material";
import {ReactiveFormsModule} from "@angular/forms";
import {AppAuthHttpService} from "../app-auth-http.service";

@NgModule({
  imports:        [
    CommonModule,
    ReactiveFormsModule,
    MaterialModule,
  ],
  declarations:   [ LoginComponent ],
  exports:        [ LoginComponent ],
  providers:      [
    LoginService,
    AppAuthHttpService,
  ]
})

export class LoginModule { }
