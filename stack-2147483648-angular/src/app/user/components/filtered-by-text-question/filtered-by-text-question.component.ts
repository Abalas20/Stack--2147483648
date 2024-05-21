import { Component } from '@angular/core';
import { StorageService } from '../../../auth-services/storage/storage.service';
import { QuestionService } from '../../user-services/question-service/question.service';
import { ActivatedRoute } from '@angular/router';

@Component({
  selector: 'app-filtered-by-text-question',
  templateUrl: './filtered-by-text-question.component.html',
  styleUrl: './filtered-by-text-question.component.scss'
})
export class FilteredByTextQuestionComponent {
    
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
      this.service.getAllQuestionsByText(this.route.snapshot.params['text'], this.pageNum).subscribe((response) => {
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
