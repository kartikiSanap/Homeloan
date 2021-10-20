import { Component, OnInit } from '@angular/core';
import { HttpClient , HttpHeaders,HttpParams} from '@angular/common/http';
import * as $ from 'jquery'
import { DOCUMENT } from '@angular/common';

@Component({
  selector: 'app-prepayment',
  templateUrl: './prepayment.component.html',
  styleUrls: ['./prepayment.component.css']
})
export class PrepaymentComponent implements OnInit {

  httpOptions={
    headers: new HttpHeaders({'Content-Type': 'application/json'})
  }

  constructor(private http : HttpClient) {

  
   }

  ngOnInit(): void {
    
    
  }
  
  
     
 


}
