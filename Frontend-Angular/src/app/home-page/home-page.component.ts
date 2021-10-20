import { Component, OnInit } from '@angular/core';
import { HttpClient , HttpHeaders,HttpParams} from '@angular/common/http';
@Component({
  selector: 'app-home-page',
  templateUrl: './home-page.component.html',
  styleUrls: ['./home-page.component.css']
})
export class HomePageComponent implements OnInit {

  username:any;
  httpOptions={
    headers: new HttpHeaders({'Content-Type': 'application/json'})
  }
  constructor(private http : HttpClient) { }

  ngOnInit(): void {
    this.username=sessionStorage.getItem('user');
    console.log(this.username);
  }

}
