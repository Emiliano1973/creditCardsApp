///<reference path="../../node_modules/@angular/material/typings/icon/index.d.ts"/>
import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';


import { AppComponent } from './app.component';
import {LoginModule} from "./login/login.module";
import {HttpModule} from "@angular/http";
import {HttpClientModule} from "@angular/common/http";
import {FormsModule} from "@angular/forms";
import {BrowserAnimationsModule} from "@angular/platform-browser/animations";
import {
  MaterialModule, MdButtonModule, MdDatepickerModule, MdIconModule, MdMenuModule, MdNativeDateModule,
  MdToolbarModule
} from "@angular/material";
import {AuthModule} from "./auth.module";
import {UserFormModule} from "./user-form/user-form.module";
import {CreditCardModule} from "./credit-card/credit-card.module";
import {AppRoutingModule} from "./app-routing.module";
import {AuthGuard} from "./guards/auth.guard";


@NgModule({
  declarations: [
    AppComponent
  ],
  imports: [
    BrowserModule,
    BrowserAnimationsModule,
    MaterialModule,
    MdToolbarModule,
    MdIconModule,
    MdMenuModule,
    MdButtonModule,
    HttpClientModule, //Possibly remove this later
    MdDatepickerModule,
    MdNativeDateModule,
    FormsModule,
    HttpModule,
    AuthModule,
    LoginModule,
    UserFormModule,
    CreditCardModule,
    AppRoutingModule,


  ],
  providers: [AuthGuard],
  bootstrap: [AppComponent]
})
export class AppModule { }
