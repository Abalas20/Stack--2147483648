import { Component } from '@angular/core';
import { QuestionService } from '../../user-services/question-service/question.service';
import { StorageService } from '../../../auth-services/storage/storage.service';
import { MatDialog } from '@angular/material/dialog';
import { DialogConfirmComponent } from '../dialog-confirm/dialog-confirm.component';

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrl: './dashboard.component.scss'
})
export class DashboardComponent {

  questions: any[] = [];
  pageNum: number = 0;
  total: number = 0;
  userId: number;
  role: string;

    constructor(
        private service: QuestionService,
        private dialog: MatDialog,
    ) { 
        this.getAllQuestions();
        this.userId = StorageService.getUserId();
        this.role = StorageService.getUserRole();
    }

    getAllQuestions() {
      this.service.getAllQuestions(this.pageNum).subscribe((response) => {
        console.log(response);
        this.questions = response.questions;
        this.total = response.totalPages * 5;
      });
    }

    delete(id : number) {
      const dialogRef = this.dialog.open(DialogConfirmComponent, {
        width: '250px',
      });
  
      dialogRef.afterClosed().subscribe(result => {
        if (result) {
          console.log(result);
          this.service.deleteQuestionById(id, this.userId, this.role).subscribe((response) => {
            console.log(response);
            this.getAllQuestions();
          });
        }
      });
    }

    pageIndexChange(event: any) {
      this.pageNum = event.pageIndex;
      this.getAllQuestions();
    }
}
