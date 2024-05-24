import { Component, OnInit } from '@angular/core';
import { StorageService } from './auth-services/storage/storage.service';
import { NavigationEnd, Router } from '@angular/router';
import { MatDialog } from '@angular/material/dialog';
import { DashboardComponent } from './user/components/dashboard/dashboard.component';
import { DialogBoxComponent } from './user/components/dialog-box/dialog-box.component';
import { QuestionService } from './user/user-services/question-service/question.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrl: './app.component.scss'
})
export class AppComponent implements OnInit {

  title = 'stack-2147483648-angular';
  isUserLoggedIn!: boolean;
  animal!: string;
  user: any;
  role!: string

  constructor(
    private router: Router,
    private dialog: MatDialog,
    private service: QuestionService,
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
    this.role = StorageService.getUserRole();
    this.isUserLoggedIn = StorageService.isUserLoggedIn();
  }

  logout() {
    StorageService.logout();
    this.router.navigateByUrl('/login');
  }

  openDialog() {
    const dialogRef = this.dialog.open(DialogBoxComponent, {
      width: '250px',
    });

    dialogRef.afterClosed().subscribe(result => {
      if (result) {
        console.log(result);
        this.router.navigate(['/user/question/filtered/' + result]);
        window.location.href = '/user/question/filtered/' + result;
      }
    });
  }

  openDialog2() {
    const dialogRef = this.dialog.open(DialogBoxComponent, {
      width: '250px',
    });

    dialogRef.afterClosed().subscribe(result => {
      if (result) {
        console.log(result);
        this.router.navigate(['/user/question/filtered/username/' + result]);
        window.location.href = '/user/question/filtered/username/' + result;
      }
    });
  }

  openDialog3() {
    const dialogRef = this.dialog.open(DialogBoxComponent, {
      width: '250px',
    });

    dialogRef.afterClosed().subscribe(result => {
      if (result) {
        console.log(result);
        this.router.navigate(['/user/question/filtered/tag/' + result]);
        window.location.href = '/user/question/filtered/tag/' + result;
      }
    });
  }

  myQuestions() {
    console.log(StorageService.getUserId());
     this.user = this.service.getUser(StorageService.getUserId()).subscribe((response) => {
      this.navigateToMyQuestions(response.username);
     },       (error) => {
      console.log(error);
    });
  }

  navigateToMyQuestions(username: any) {
    this.router.navigate(['/user/question/filtered/username/' + username]);
    window.location.href = '/user/question/filtered/username/' + username;
  }
}