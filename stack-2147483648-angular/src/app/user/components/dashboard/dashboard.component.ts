import { Component } from '@angular/core';
import { QuestionService } from '../../user-services/question-service/question.service';
import { get } from 'http';
import { StorageService } from '../../../auth-services/storage/storage.service';

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

    constructor(
        private service: QuestionService,
    ) { 
        this.getAllQuestions();
        this.userId = StorageService.getUserId();
    }

    getAllQuestions() {
      this.service.getAllQuestions(this.pageNum).subscribe((response) => {
        console.log(response);
        this.questions = response.questions;
        this.total = response.totalPages * 5;
      });
    }

    pageIndexChange(event: any) {
      this.pageNum = event.pageIndex;
      this.getAllQuestions();
    }
}
