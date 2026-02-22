import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { AuthService } from 'src/app/core/services/auth.service';

@Component({
  selector: 'app-sso-callback',
  templateUrl: './sso-callback.component.html'
})
export class SsoCallbackComponent implements OnInit {

  loading = true;
  message = '';

  constructor(
    private route: ActivatedRoute,
    private authService: AuthService
  ) {}

  ngOnInit(): void {
    const code = this.route.snapshot.queryParamMap.get('code');

    if (!code) {
      this.message = 'Código no recibido';
      this.loading = false;
      return;
    }

    this.authService.handleSsoCallback(code)
      .subscribe({
        next: () => {
          this.message = 'SSO correcto';
          this.loading = false;
        },
        error: () => {
          this.message = 'Error en autenticación SSO';
          this.loading = false;
        }
      });
  }
}