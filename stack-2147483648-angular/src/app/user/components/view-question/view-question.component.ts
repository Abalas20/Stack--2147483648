import { Component } from '@angular/core';
import { QuestionService } from '../../user-services/question-service/question.service';
import { ActivatedRoute } from '@angular/router';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { StorageService } from '../../../auth-services/storage/storage.service';
import { AnswerServiceService } from '../../user-services/answer-service.service';

@Component({
  selector: 'app-view-question',
  templateUrl: './view-question.component.html',
  styleUrl: './view-question.component.scss'
})
export class ViewQuestionComponent {

  questionId: number = this.route.snapshot.params['questionId'];
  question: any;
  answers: any;
  userId: number;
  validateForm!: FormGroup;

  constructor(
    private questionService: QuestionService,
    private route: ActivatedRoute,
    private fb: FormBuilder,
    private answerService: AnswerServiceService,
  ) { 
    this.userId = StorageService.getUserId();
    this.questionService.getQuestionById(this.questionId).subscribe(
      (response) => {
        this.question = response.question;
        this.answers = response.answers;
        console.log(response);
        this.validateForm = this.fb.group({
          url: ['', [Validators.required]],
          body: [null, [Validators.required]]
        });
      },
      (error) => {
        console.log(error);
      }
    );
  }


  addAnswer() {
    let requestBody: any = {
      body: this.validateForm.value.body,
      userId: StorageService.getUserId(),
      url: this.validateForm.value['url'],
      questionId: this.questionId
    }
    console.log(requestBody);
    this.answerService.postAnswer(requestBody).subscribe((res) => {
      console.log(res);
      this.validateForm.reset();
      window.location.reload();
    });
  }

}
