import { Component, OnInit } from '@angular/core';
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {Location} from "@angular/common";
import {ActivatedRoute, Params, Router} from "@angular/router";
import {CreditCardService} from "../credit-card.service";
import {CreditCard} from "../credit-card-model";
import {Observable} from "rxjs/Observable";
import 'rxjs/add/observable/forkJoin';
@Component({
  selector: 'app-credit-card-form',
  templateUrl: './credit-card-form.component.html',
  styleUrls: ['./credit-card-form.component.css']
})
export class CreditCardFormComponent implements OnInit {
  private creditCardForm: FormGroup;

  private creditCard:CreditCard;
   hasError:boolean=false;
  errorMessage:string="";
 private  title:string="New";
  monthsArr:number[]=[1,2,3,4,5,6,7,8,9,10, 11, 12];
   yearsArr:number[]=[]
   initialized:boolean=false;
  constructor(private _formBuilder: FormBuilder,
              private _creditCardService:CreditCardService,
              private _route: ActivatedRoute,
              private _location: Location
              ) { }

  ngOnInit() {
    let d:Date=new Date();
    let yearStart:number=d.getFullYear()-5;
    let yearEnd:number=d.getFullYear()+10;
    for (let i:number=yearStart; i<=yearEnd; i++){
      this.yearsArr.push(i);
    }


    this._route.url
      .subscribe( value => {
        if(value[value.length - 1].path.slice(0,4) === 'edit') {
          this.title="Edit"
          this._route.params.flatMap((params: Params) => {
            return Observable.forkJoin([
                this._creditCardService.getCreditCardByCode(params['cardCode'])
              ])
          }).subscribe( result => {

            this.setCreditCard(result[0])
              this.initialized = true;


          })
        } else {

          this.title="New";
           this.initNewCreditCard();
          this.initialized = true;
        }
      });

  }


  private setCreditCard(creditCard:any){
    this.creditCard=creditCard;
    this.creditCardForm=this._formBuilder.group({
      cardCode: [{value: creditCard.cardCode, disabled: false}, [Validators.required, Validators.minLength(15), Validators.maxLength(16)]],
      name: [{value: creditCard.name, disabled: false}, [Validators.required]],
      expiryMonth: [{value: creditCard.expiryMonth, disabled: false}, [Validators.required]],
      expiryYear: [{value: creditCard.expiryYear, disabled: false},[ Validators.required]]
    });
  }

  private initNewCreditCard(){
    this.creditCard=new CreditCard();
    this.creditCardForm=this._formBuilder.group({
      cardCode: [{value: "", disabled: false}, [Validators.required, Validators.minLength(15), Validators.maxLength(16)]],
      name: [{value: "", disabled: false}, Validators.required],
      expiryMonth: [{value: "", disabled: false}, Validators.required],
      expiryYear: [{value: "", disabled: false}, Validators.required]
    });

  }


  cancelClick() {
    this._location.back();
  }

  onSubmit(event:any) {
    this.creditCard.cardCode= this.creditCardForm.get('cardCode').value;
    this.creditCard.name = this.creditCardForm.get('name').value;
    this.creditCard.expiryMonth = this.creditCardForm.get('expiryMonth').value;
    this.creditCard.expiryYear = this.creditCardForm.get('expiryYear').value;

     if (this.creditCard.id>0){
       this._creditCardService.updateCreditCard(this.creditCard.id, this.creditCard).subscribe(
         data => {

           this._location.back();
         },
         error => {
           this.handleError(error);
         }
       );
     }else {
       this._creditCardService.addCreditCard(this.creditCard).subscribe(
         data => {

           this._location.back();
         },
         error => {
            this.handleError(error);
         }
       );

     }

  }

  handleError(result: any) {
    this.errorMessage = result.json().message;
    this.hasError = true;
  }

}
