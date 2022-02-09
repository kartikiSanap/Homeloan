import { Component, OnInit } from '@angular/core';
import { HttpClient , HttpHeaders,HttpParams} from '@angular/common/http';
import {SavingsAccount} from '../../savings-account';
import { Observable } from 'rxjs';
import { from,  throwError } from 'rxjs';
import { map, catchError } from 'rxjs/operators';
import { Injectable } from '@angular/core';



@Component({
  selector: 'app-saving-account',
  templateUrl: './saving-account.component.html',
  styleUrls: ['./saving-account.component.css']
})
@Injectable({
  providedIn: 'root'
})
export class SavingAccountComponent implements OnInit {
  

      account_no: any; 
      name: any; 
      balance: any; 
      email: any; 
      loan_exists: any; 
      username:any;
      
  constructor(private http:HttpClient,private saving_account:SavingsAccount)
  {

  }

  ngOnInit(): void {
    this.username=sessionStorage.getItem('user');
    this.http.post<SavingsAccount>("http://localhost:8080/Home/Saving",{username:this.username}).subscribe((data:SavingsAccount)=>
    {
        this.account_no=data.account_no;
        this.balance=data.balance;
        this.email=data.email;
        this.loan_exists=data.loan_exists;
        this.name=data.name;
    })
    
}
  
  
   
  

}
