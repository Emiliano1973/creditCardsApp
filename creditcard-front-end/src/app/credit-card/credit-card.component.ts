import {Component, Input, OnDestroy, OnInit, ViewChild} from '@angular/core';
import {Router} from "@angular/router";
import {Observable} from "rxjs/Observable";
import {MdDialog, MdSidenav, MdSnackBar} from "@angular/material";
import 'rxjs/add/observable/of';
import {CreditCard} from "./credit-card-model";
import {CreditCardService} from "./credit-card.service";

@Component({
  selector: 'app-credit-card',
  templateUrl: './credit-card.component.html',
  styleUrls: ['./credit-card.component.css']
})
export class CreditCardComponent implements OnInit {

   creditCards:CreditCard[]=[];
   searchLike:string="";
  hasError: boolean = false;
  errorMessage:string = '';

  loading:boolean=false;
  constructor(private _router: Router,
              private _creditCardService:CreditCardService,
              public dialog: MdDialog) { }

  ngOnInit() {

    this.loading=true;
    let creditCardStream: Observable<any> = this._creditCardService.getCreditCards();
    creditCardStream.subscribe(result => {
          this.setList(result)
      },
      error => {

        this.handleError(error);
        this.loading=false;
      });

  }

  private setList(creditCardsNew:CreditCard[]) {
    this.creditCards = creditCardsNew;
    this.loading=false;
  }

  onDeleteClick(id: number) {
    let confirmDel:boolean=confirm("Delete the credit card?");
    if(!confirmDel)
        return;
    this.loading=true
    this._creditCardService.deleteCreditCard(id).subscribe(
        data => {
            this.resetCards();
            this.loadCards();
        },
        error => {
          this.hasError=true;
          this.errorMessage=(error.message)?error.message:error;
          this.loading=false;
        }
      );
  }

  private resetCards(){
    this.creditCards=[];
  }

  private loadCards(){
    this.loading=true;
    this._creditCardService.getCreditCards(this.searchLike).subscribe(result => {
        this.setList(result)
      },
      error => {
        this.hasError=true;
        //this.errorMessage=(error.message)?error.message:error;
        this.loading=false;
      });

  }



  searchBox(search: string) {
    this.searchLike = "";
    if((search) && (search.trim()!='')){
      this.searchLike=search;
    }

    this.resetCards();
    this.loadCards();
  }

  onEditClick(id: number) {
    let destination: string = '/creditcards/' + id + '/edit';
    this._router.navigate([destination]);
  }

  handleError(result: any) {
    this.errorMessage = result.json().message;
    this.hasError = true;
  }
}
