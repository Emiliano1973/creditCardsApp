import {Component, OnInit} from "@angular/core";
import {LoginService} from "./login.service";
import {Router} from "@angular/router";
import {Validators, FormBuilder} from "@angular/forms";


@Component({
  selector: 'User-component',
  templateUrl: './login.component.html',
  styleUrls : ['./login.component.css'],
  providers: [ LoginService ]
})

export class LoginComponent implements OnInit{

  private loading = false;
  hasError: boolean = false;
   errorMessage:string = '';


  constructor (private _router: Router,
               private _loginService: LoginService,
               public  _formBuilder: FormBuilder) {}

  public loginForm = this._formBuilder.group({
    email: ["", Validators.required],
    password: ["", Validators.required]
  });

  ngOnInit(): void {}

  doLogin(event: Event) {
    let username = this.loginForm.get('email').value;
    let password = this.loginForm.get('password').value;

    this.login(username, password)
  }

  login(username: string, password: string) {
    this.loading = true;

    this._loginService.login(username, password)
      .subscribe(
        result => {
          if ( result.success == true ) {
            this._router.navigate(['/']);
          }
        },
        error => {
          this.handleError(error);
          this.loading = false;
        });
  }

  handleError(result: any) {
    this.errorMessage = result.json().message;
    this.hasError = true;
  }

}
