import { RepaymentComponent } from './home-page/repayment/repayment.component';
import { PrepaymentComponent } from './home-page/prepayment/prepayment.component';
import { LoanDetailsComponent } from './home-page/loan-details/loan-details.component';
import { ApplyLoanComponent } from './home-page/apply-loan/apply-loan.component';
import { HomePageComponent } from './home-page/home-page.component';

import { LoginPageComponent } from './login-page/login-page.component';
import { NgModule, Component } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { SavingAccountComponent } from './home-page/saving-account/saving-account.component';

const routes: Routes = [
 {path:'', component:LoginPageComponent},
  //{path:'/home', component:HomePageComponent},
  {path:'home', component:HomePageComponent},
  {path:'home/apply', component:ApplyLoanComponent},
  {path: 'home/loan_details', component:LoanDetailsComponent},
  {path: 'home/prepayment', component:PrepaymentComponent},
  {path: 'home/repayment', component:RepaymentComponent},
  {path: 'home/account_details', component: SavingAccountComponent}

];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
