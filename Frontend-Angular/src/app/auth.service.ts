import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  user:any

  constructor() { 
    //this.user=sessionStorage.getItem('user');
  }

  isloggedin()
  {
    this.user=sessionStorage.getItem('user');
    if(this.user===null)
    {
      return false;
    }
    return true; 
  }
}
