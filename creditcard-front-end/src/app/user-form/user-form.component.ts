import { Component, OnInit } from '@angular/core';
import {Location} from "@angular/common";
import {UserService} from "./user.service";
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {User} from "./user-model";

@Component({
  selector: 'app-user-form',
  templateUrl: './user-form.component.html',
  styleUrls: ['./user-form.component.css']
})
export class UserFormComponent implements OnInit {

   hasError:boolean=false;
   errorMessage:string="";

    userForm: FormGroup;

  constructor(private _userService:UserService,
              private _formBuilder: FormBuilder,
              private _location: Location
                )
 { }

  ngOnInit() {
    this.initForm();

  }

  cancelClick() {
    this._location.back();
  }



  private initForm() {
    this.userForm = this._formBuilder.group({
      email: [{value: "", disabled: false}, [Validators.required, Validators.email]],
      password: [{value: "", disabled: false}, [Validators.required]],
    });
  }

  onSubmit(event: Event) {
    let user:User=new User();
    user.username= this.userForm.get('email').value;
    user.password = this.userForm.get('password').value;
    this._userService.addUser(user).subscribe(
      data => {
        this._location.back();
      },
      err => {

          this.handleError(err);
      }
    );


  }


  handleError(result: any) {
    this.errorMessage = result.json().message;
    this.hasError = true;
  }
}
