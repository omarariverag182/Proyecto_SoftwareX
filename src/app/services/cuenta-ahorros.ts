import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

export interface CuentaAhorrosRequest {
  nombreTitular: string;
  saldo: number;
}

export interface CuentaAhorrosResponse {
  id: number;
  saldo: number;
  cliente: {
    id_cliente: number;
    nombre: string;
    apellido: string;
    rol: string;
  };
}

@Injectable({
  providedIn: 'root'
})
export class CuentaAhorrosService {
  private apiUrl = 'http://localhost:8081/cuentas/ahorros';

  constructor(private http: HttpClient) {}

  crearCuentaAhorros(request: CuentaAhorrosRequest): Observable<CuentaAhorrosResponse> {
    return this.http.post<CuentaAhorrosResponse>(this.apiUrl, request);
  }
}