import {Routes, RouterModule} from "@angular/router";
import {AuthGuard} from "../guards/auth.guard";
import {NgModule} from "@angular/core";

import {UserFormComponent} from "./user-form.component";

const userRoutes: Routes = [
  { path: 'users/new', component: UserFormComponent}
];


@NgModule({
  imports: [
    RouterModule.forChild(userRoutes)
  ],
  exports: [
    RouterModule
  ]
})

export class UserRoutingModule { }
