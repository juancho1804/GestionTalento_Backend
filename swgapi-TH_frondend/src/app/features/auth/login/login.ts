import { Component } from '@angular/core';
import { SharedModule } from '../../../core/shared/shared-module';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { AuthService } from '../auth-service';
import { Router } from '@angular/router';
import { MatSnackBar } from '@angular/material/snack-bar';

@Component({
  selector: 'app-login',
  imports: [SharedModule],
  templateUrl: './login.html',
  styleUrl: './login.css'
})
export class Login {

  showPassword = false;
  form: FormGroup;
  loading = false;

  constructor(
    private fb: FormBuilder,
    private authService: AuthService,
    private router: Router,
    private _snackBar: MatSnackBar) {
    this.form = this.fb.group({
      username: ['', Validators.required],
      password: ['', Validators.required]
    })
  }

  ingresar() {
    const { username, password } = this.form.value;
    if (this.authService.login(username, password)) {
      const role = this.authService.getRole();
      const planId = this.authService.getPlanId();

      this.fakeloading();
      switch (role) {
        case 'admin':
          this.fakeloading();
          this.router.navigate(['/admin-dashboard']);
          break;
        case 'bienestar':
        case 'capacitacion':
        case 'incentivos':
          this.fakeloading();
          this.router.navigate([`/encargada-dashboard/${role}/${planId}`]);
          break;
        default:
          this.error();
          break;
      }
    } else {
      this.error();
      this.form.reset();
    }
  }

  error() {
    this._snackBar.open("Usuario o contraseña incorrecto", '', {
      duration: 3000,
      horizontalPosition: 'right',
      verticalPosition: 'top',
    });
  }

  fakeloading() {
    this.loading = true;
    setTimeout(() => {
      // redirección completada
    }, 1500);
  }


}
