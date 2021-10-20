import { Component, OnInit } from '@angular/core';
import { HttpClient , HttpHeaders,HttpParams} from '@angular/common/http';
import { Repayment_schedule } from 'src/app/shared/project.model';
@Component({
  selector: 'app-repayment',
  templateUrl: './repayment.component.html',
  styleUrls: ['./repayment.component.css']
})
export class RepaymentComponent implements OnInit {

  httpOptions={
    headers: new HttpHeaders({'Content-Type': 'application/json'})
  }
  repayment_schedules:any;
  id: any; 
    month_year: any;
    loan_id: any;
    emi: any;
    principal_component: any;
    interest_component: any;
    principal_outstanding: any;
    payment_status: any;
    username:any;
  constructor(private http : HttpClient) { }

  ngOnInit(): void {
    this.username=sessionStorage.getItem('user');
     this.http.post<Repayment_schedule[]>("http://localhost:8080/Home/Loan/Reshedule",{
       username:this.username
     }).subscribe((data:Repayment_schedule[]) =>
     {
         this.repayment_schedules=data;
         console.log(this.repayment_schedules);
         /*this.id=data.id;
         this.month_year=data.month_year;
         this.loan_id=data.loan_id;
         this.emi=data.emi;
         this.principal_component=data.principal_component;
         this.interest_component=data.interest_component;
         this.principal_outstanding=data.principal_outstanding;
         this.payment_status=data.payment_status;*/

     })

  }

}
