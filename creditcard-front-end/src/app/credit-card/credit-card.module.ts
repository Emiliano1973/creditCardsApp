import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { CreditCardComponent } from './credit-card.component';
import { CreditCardFormComponent } from './credit-card-form/credit-card-form.component';
import {NgxDatatableModule} from "@swimlane/ngx-datatable";
import {ReactiveFormsModule} from "@angular/forms";
import {MaterialModule} from "@angular/material";
import {CreditCardService} from "./credit-card.service";
import {AuthGuard} from "../guards/auth.guard";
import {AppAuthHttpService} from "../app-auth-http.service";
import {CreditCardRoutingModule} from "./credit-card-routing.module";

@NgModule({
  imports: [
    MaterialModule,
    CommonModule,
    ReactiveFormsModule,
    NgxDatatableModule,
    CreditCardRoutingModule
  ],
  declarations: [CreditCardComponent, CreditCardFormComponent],
  exports: [CreditCardComponent],
  providers: [
    CreditCardService,AppAuthHttpService,
    AuthGuard ]

})
export class CreditCardModule { }
