import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable, tap } from 'rxjs';
import { LoginRequest } from '../models/login-request.model';
import { LoginResponse } from '../models/login-response.model';
import { TokenService } from './token.service';
import { catchError } from 'rxjs/operators';
import { throwError } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  private readonly API_URL = 'http://localhost:8080/api/auth';

  constructor(
    private http: HttpClient,
    private tokenService: TokenService
  ) {}

  login(request: LoginRequest): Observable<LoginResponse> {
    return this.http.post<LoginResponse>(`${this.API_URL}/login`, request)
      .pipe(
        tap(response => {
          if (response.accessToken) {
            this.tokenService.setAccessToken(response.accessToken);
          }
          if (response.refreshToken) {
            this.tokenService.setRefreshToken(response.refreshToken);
          }
        }),
        catchError(err => {
          const msg = err.error?.errorMessage || 'Error desconocido';
          return throwError(() => new Error(msg));
        })
      );
  }

  startSso(): void {
    // Redirige directamente al backend (maneja el 302)
    window.location.href = `${this.API_URL}/sso`;
  }

  handleSsoCallback(code: string): Observable<LoginResponse> {
    return this.http.get<LoginResponse>(
      `${this.API_URL}/sso/callback?code=${code}`
    ).pipe(
      tap(response => {
        this.tokenService.setAccessToken(response.accessToken);
        if (response.refreshToken) {
          this.tokenService.setRefreshToken(response.refreshToken);
        }
      })
    );
  }

  logout(): void {
    this.tokenService.clearTokens();
  }
}