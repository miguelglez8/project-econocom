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

  goHome(): void {
    window.location.href = 'http://localhost:4200/';
  }

  ngOnInit(): void {
    const code = this.route.snapshot.queryParamMap.get('code');

    if (!code) {
      alert('Código no recibido');
      window.location.href = "http://localhost:4200/"; // volver al inicio
      return;
    }

    this.authService.handleSsoCallback(code).subscribe();
  }
}