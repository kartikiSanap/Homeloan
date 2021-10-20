import { Component, OnInit } from '@angular/core';
import { HttpClient , HttpHeaders,HttpParams} from '@angular/common/http';
import { LoanAccounts } from 'src/app/loan-accounts';
@Component({
  selector: 'app-loan-details',
  templateUrl: './loan-details.component.html',
  styleUrls: ['./loan-details.component.css']
})
export class LoanDetailsComponent implements OnInit {
  
  httpOptions={
    headers: new HttpHeaders({'Content-Type': 'application/json'})
  }
  constructor(private http : HttpClient) { }

  username:any;
  loan_accounts:any
  account_number: any;
loan_id:any;
total_loan: any;
interest: any;
tenure: any;
status: any;

  ngOnInit(): void {
     this.username=sessionStorage.getItem('user');
     this.http.post<LoanAccounts>("http://localhost:8080/Home/Loan",{
       username:this.username
     }).subscribe((data:LoanAccounts) =>
     {
     this.account_number=data.account_no;
     this.loan_id=data.loan_id;
     this.total_loan=data.total_loan;
     this.interest=data.interest_rate;
     this.tenure=data.tenure;
     this.status=data.loan_status;

     })
     
  }

}
