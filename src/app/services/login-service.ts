import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class LoginService {
  private apiUrl = 'http://localhost:8081/controller/login';


  constructor(private http: HttpClient){}  
    
  login(datos: any):Observable<any>{
      return this.http.post(this.apiUrl, datos);
    }   

}

