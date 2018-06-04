import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { UserFormComponent } from './user-form.component';
import {UserRoutingModule} from "./user-routing.module";
import {ReactiveFormsModule} from "@angular/forms";
import {MaterialModule} from "@angular/material";
import {UserService} from "./user.service";
import {HttpModule} from "@angular/http";
import {AuthGuard} from "../guards/auth.guard";
import {AppAuthHttpService} from "../app-auth-http.service";

@NgModule({
  imports: [
    MaterialModule,
    ReactiveFormsModule,
    CommonModule,
    HttpModule,
    UserRoutingModule

  ],
  declarations: [UserFormComponent],
  exports: [UserFormComponent],
  providers: [
    UserService,AuthGuard
    ]
})
export class UserFormModule { }
