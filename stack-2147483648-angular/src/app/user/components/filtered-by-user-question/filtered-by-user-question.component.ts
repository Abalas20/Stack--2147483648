import { Component } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { StorageService } from '../../../auth-services/storage/storage.service';
import { QuestionService } from '../../user-services/question-service/question.service';

@Component({
  selector: 'app-filtered-by-user-question',
  templateUrl: './filtered-by-user-question.component.html',
  styleUrl: './filtered-by-user-question.component.scss'
})
export class FilteredByUserQuestionComponent {
  questions: any[] = [];
  pageNum: number = 0;
  total: number = 0;
  userId: number;

    constructor(
        private service: QuestionService,
        private route: ActivatedRoute,
    ) { 
        this.getAllQuestions();
        this.userId = StorageService.getUserId();
    }

    getAllQuestions() {
      this.service.getAllQuestionsByUsername(this.route.snapshot.params['username'], this.pageNum).subscribe((response) => {
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
