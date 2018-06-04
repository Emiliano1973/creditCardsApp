import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import {RouterModule, Routes} from "@angular/router";
import {AuthGuard} from "../guards/auth.guard";
import {CreditCardComponent} from "./credit-card.component";
import {CreditCardFormComponent} from "./credit-card-form/credit-card-form.component";

const userRoutes: Routes = [
  { path: 'creditcards', component: CreditCardComponent, canActivate: [ AuthGuard ] },
  { path: 'creditcards/new', component: CreditCardFormComponent, canActivate: [ AuthGuard ]},
  { path: 'creditcards/:cardCode/edit', component: CreditCardFormComponent, canActivate: [ AuthGuard ]}
];


@NgModule({
  imports: [
    RouterModule.forChild(userRoutes)
  ],
  exports: [
    RouterModule
  ]
})
export class CreditCardRoutingModule { }
