import { Component, OnInit } from '@angular/core';

import { HttpClient , HttpHeaders,HttpParams} from '@angular/common/http';
import { FormsModule, FormGroup, FormControl, Validators } from '@angular/forms';
import { Observable } from 'rxjs';
import { ActivatedRoute, Router } from '@angular/router';
import {Login} from '../login';
import { Injectable } from '@angular/core';

@Component({
  selector: 'app-login-page',
  templateUrl: './login-page.component.html',
  styleUrls: ['./login-page.component.css']
})
@Injectable({
  providedIn: 'root'
})
export class LoginPageComponent implements OnInit {

  username:any;
  password:any;
  response:any;
  result:any;
  value:any;
  values:any;
  
  constructor(private http : HttpClient,private router:Router,private route: ActivatedRoute,private login: Login ) {

    
   }

  
   

  ngOnInit(): void {
    this.value=true;
  }

  
 onClickSubmit(formdata:any)
  {
   
    
     this.username=formdata.uname;
     this.password=formdata.psw;
     console.log(this.username);
     
     //this.router.navigate(['home']);
     this.http.post<Login>('http://localhost:8080/Home/Login',{
       username:this.username,
       password:this.password


     }).subscribe((data:Login) =>{
       if(data.result=="fail")
       {
         this.value=false;
         window.alert('Invalid Login Credentials')
       }
      if(data.result=="success")
     {
      sessionStorage.setItem('user',this.username);
      this.router.navigate(['home']);
     }
     
     
     });
     
     
     


    
}



     
  
}
