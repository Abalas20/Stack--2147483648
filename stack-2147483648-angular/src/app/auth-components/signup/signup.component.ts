import { Component, OnInit } from '@angular/core';
import { AuthService } from '../../auth-services/auth-service/auth.service';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MatSnackBar } from '@angular/material/snack-bar';
import { Router } from '@angular/router';

@Component({
  selector: 'app-signup',
  templateUrl: './signup.component.html',
  styleUrl: './signup.component.scss'
})
export class SignupComponent {

  signupForm: FormGroup;
  
  constructor(
    private service: AuthService,
    private fb: FormBuilder,
    private snackBar: MatSnackBar,
    private router: Router
  ) {
    this.signupForm = this.fb.group({
      username:['', Validators.required],
      password:['', Validators.required],
      firstName:['', Validators.required],
      lastName:['', Validators.required],
      email:['', Validators.required],
      phone:['', Validators.required],
    });
  }
  
  async signup() {
    console.log(this.signupForm.value);
    try {
      const response = await this.service.signup(this.signupForm.value).toPromise();
      console.log(response);
      if (response.id != null) {
        this.snackBar.open(
          "Your account has been successfully created!",
          'Close',
          { duration: 5000 }
        );
        this.router.navigateByUrl('/login');
      } else {
        this.snackBar.open(response.message, 'Close', { duration: 5000 });
      }
    } catch (error) {
      console.error(error);
      this.snackBar.open(
        "Registration failed, please try again",
        'Close', { duration: 5000 }
      );
    }
  }

}