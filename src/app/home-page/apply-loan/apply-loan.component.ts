import { Component, OnInit } from '@angular/core';
import { HttpClient , HttpHeaders,HttpParams} from '@angular/common/http';
import { ActivatedRoute, Router } from '@angular/router';
import {Applyloan} from '../../applyloan';
@Component({
  selector: 'app-apply-loan',
  templateUrl: './apply-loan.component.html',
  styleUrls: ['./apply-loan.component.css']
})
export class ApplyLoanComponent implements OnInit {
  
  username:any;
  accountnumber:any;
  salary:any;
  loanammount:any;
  loantenure:any;
  propertyaddress:any;
  propertyimage:any;
  value1:any;
  value2:any
  constructor(private http : HttpClient,private router:Router,private route: ActivatedRoute) { }

  ngOnInit(): void {
    this.value1=true;
    this.value2=true;
  }

  onClickSubmit(formdata:any)
  {
    this.username=sessionStorage.getItem('user');
    this.loanammount=formdata.lamount;
    this.salary=formdata.sal;
    this.loantenure=formdata.tenure;
    console.log(this.loanammount);
    this.propertyaddress=formdata.address;
    this.propertyimage=formdata.img;

    //let params = new HttpParams().set('username',this.username).set('account_no',this.accountnumber).set('salary', this.salary).set('loan_amount',this.loanammount).set('tenure', this.loantenure).set('property_address', this.propertyaddress).set('property_image', this.propertyimage)

    this.http.post<Applyloan>("http://localhost:8080/Home/Loan/ApplyLoan",{
      username:this.username,
      salary:this.salary,
      tenure:this.loantenure,
      loan_amount: this.loanammount,
property_address:this.propertyaddress,

property_image: this.propertyimage


    }).subscribe((data:Applyloan) =>
    {
      console.log(data.result);
      if(data.result=="Loan Already Exists")
      {
          this.value1=false;

          window.alert('Loan Already Exists')
          this.router.navigate(['home']);
      }
      if(data.result=="Loan Not Approved")
      {
         window.alert('Since Loan Amount exceeds 50 times of the salary,your loan is not approved.')
      }
      if(data.result=="Loan Approved")
      {
        window.alert('Loan Appoved');
        this.router.navigate(['home']);
      }

    })
   
   

  

  }

}
