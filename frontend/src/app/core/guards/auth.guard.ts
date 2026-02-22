import { Injectable } from '@angular/core';
import { CanActivate, Router } from '@angular/router';
import { TokenService } from '../services/token.service';

@Injectable({
  providedIn: 'root'
})
export class AuthGuard implements CanActivate {

  constructor(
    private tokenService: TokenService,
    private router: Router
  ) {}

  canActivate(): boolean {

    const token = this.tokenService.getAccessToken();

    if (!token) {
      this.router.navigate(['/']);
      return false;
    }

    // Si quieres validar expiración JWT real:
    if (this.tokenService.isTokenExpired?.()) {
      this.tokenService.clearTokens();
      this.router.navigate(['/']);
      return false;
    }

    return true;
  }
}