import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import {RouterModule, Routes} from "@angular/router";
import {LoginComponent} from "./login/login.component";

const appRoutes: Routes = [
  { path: 'login', component: LoginComponent },
  { path: '', redirectTo: "creditcards", pathMatch: 'full' }
]

@NgModule({
  imports: [
    RouterModule.forRoot(appRoutes, {enableTracing: false})
  ],
  exports: [
    RouterModule
  ]
})

export class AppRoutingModule {}


