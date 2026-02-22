import { Component } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { AuthService } from 'src/app/core/services/auth.service';
import { TranslateService } from '@ngx-translate/core';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent {
  loginForm: FormGroup;
  loading = false;
  hidePassword = true;

  constructor(
    private fb: FormBuilder,
    private authService: AuthService,
    public translate: TranslateService
  ) {
    this.loginForm = this.fb.group({
      email: ['', [Validators.required, Validators.email]], // campo obligatorio y formato email
      password: [
        '', 
        [
          Validators.required,            // campo obligatorio
          Validators.minLength(4),        // ejemplo: mínimo 4 caracteres
          Validators.pattern(/^(?=.*[A-Za-z])(?=.*\d).+$/) // opcional: al menos 1 letra y 1 número
        ]
      ]
    });
      this.translate.setDefaultLang('es');
  }

  changeLang(lang: string): void {
    this.translate.use(lang);
  }

  submit(): void {
    if (this.loginForm.invalid) {
      this.loginForm.markAllAsTouched();
      return;
    }

    this.loading = true;

    this.authService.login(this.loginForm.value)
      .subscribe({
        next: () => {
          this.loading = false;
          alert('Login correcto');
        },
        error: (err) => {
          this.loading = false;
          alert('Error de autenticación: ' + (err.message || 'Revise sus credenciales'));
        }
      });
  }

  loginWithSso(): void {
    this.authService.startSso();
  }

  togglePassword(): void {
    this.hidePassword = !this.hidePassword;
  }
}