import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { FormsModule }   from '@angular/forms';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { LoginPageComponent } from './login-page/login-page.component';
import { HomePageComponent } from './home-page/home-page.component';
import { ApplyLoanComponent } from './home-page/apply-loan/apply-loan.component';
import { LoanDetailsComponent } from './home-page/loan-details/loan-details.component';
import { PrepaymentComponent } from './home-page/prepayment/prepayment.component';
import { RepaymentComponent } from './home-page/repayment/repayment.component';
import { HttpClient , HttpHeaders,HttpParams} from '@angular/common/http';
import { HttpClientModule } from '@angular/common/http';
import { SavingAccountComponent } from './home-page/saving-account/saving-account.component';
import {SavingsAccount} from './savings-account';
import {RepaymentSchedule} from './repayment-schedule';
import {Login} from './login';
import {Applyloan} from './applyloan';
import {LoanAccounts} from './loan-accounts';


@NgModule({
  declarations: [
    AppComponent,
    LoginPageComponent,
    HomePageComponent,
    ApplyLoanComponent,
    LoanDetailsComponent,
    PrepaymentComponent,
    RepaymentComponent,
    SavingAccountComponent,
    

  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    FormsModule,
    HttpClientModule
  ],
  providers: [SavingsAccount,RepaymentSchedule,Login,Applyloan,LoanAccounts],
  bootstrap: [AppComponent]
})
export class AppModule { }
