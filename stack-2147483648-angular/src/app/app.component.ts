import { Component, OnInit } from '@angular/core';
import { StorageService } from './auth-services/storage/storage.service';
import { NavigationEnd, Router } from '@angular/router';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrl: './app.component.scss'
})
export class AppComponent implements OnInit {
  title = 'stack-2147483648-angular';
  isUserLoggedIn!: boolean;

  constructor(
    private router: Router,

  ) {}

  ngOnInit() {
    this.updateUserLoggedInStatus();
    this.router.events.subscribe(event => {
      if (event instanceof NavigationEnd) {
        this.updateUserLoggedInStatus();
      }
    })
  }

  private updateUserLoggedInStatus(): void {
    this.isUserLoggedIn = StorageService.isUserLoggedIn();
  }

  logout() {
    StorageService.logout();
    this.router.navigateByUrl('/login');
  }
}
