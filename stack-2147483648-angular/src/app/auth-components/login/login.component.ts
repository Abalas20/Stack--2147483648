import { Component } from '@angular/core';
import { AuthService } from '../../auth-services/auth-service/auth.service';
import { FormBuilder, Validators, FormGroup } from '@angular/forms';
import { Router } from '@angular/router';
import { error } from 'console';
import { MatSnackBar } from '@angular/material/snack-bar';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrl: './login.component.scss'
})
export class LoginComponent {

  loginForm: FormGroup;

  constructor(
    private service: AuthService,
    private fb: FormBuilder,
    private router: Router,
    private snackbar: MatSnackBar,
  ) {
      this.loginForm = fb.group({
        email:['', Validators.required],
        password:['', Validators.required],
      })
  }

  async login() {
    console.log(this.loginForm.value);
    try {
      const response = await this.service.login(this.loginForm.value).toPromise();
      console.log(response);
      this.router.navigateByUrl("user/dashboard");
    } catch (error) {
      console.error(error);
      this.snackbar.open('Bad credentials', 'Close', { duration: 5000, panelClass: 'error-snackbar' });
    }
  }
  
}
