import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class Usuario {
  private apiUrl = 'http://localhost:8081/controller/registro';


  constructor(private http: HttpClient){}  
    
  registrar(usuario: any):Observable<any>{
      return this.http.post(this.apiUrl, usuario);
    } 
}
