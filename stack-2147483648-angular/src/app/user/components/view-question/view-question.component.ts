import { Component } from '@angular/core';
import { QuestionService } from '../../user-services/question-service/question.service';
import { ActivatedRoute } from '@angular/router';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { StorageService } from '../../../auth-services/storage/storage.service';
import { AnswerServiceService } from '../../user-services/answer-service.service';
import { VoteService } from '../../user-services/vote-service/vote.service';
import { T } from '@angular/cdk/keycodes';

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
  voteCount!: number;

  constructor(
    private questionService: QuestionService,
    private route: ActivatedRoute,
    private fb: FormBuilder,
    private answerService: AnswerServiceService,
    private voteService: VoteService,
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
    this.voteService.getVoteCount(this.questionId).subscribe(
      (response) => {
        console.log(response);
        this.voteCount = response;
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

  downVote() {
    let requestBody: any = {
      userId: this.userId,
      questionId: this.questionId,
      value: -1,
    }
    console.log(requestBody);
    this.voteService.postVote(requestBody).subscribe((res) => {
      console.log(res);
      window.location.reload();
    });
  }
    
  upVote() {
    let requestBody: any = {
      userId: this.userId,
      questionId: this.questionId,
      value: 1,
    }
    console.log(requestBody);
    this.voteService.postVote(requestBody).subscribe((res) => {
      console.log(res);
      window.location.reload();
    });
  }

  upVoteA(answerId: number) {
    let requestBody: any = {
      userId: this.userId,
      answerId: answerId,
      value: 1,
    }
    console.log(requestBody);
    this.voteService.postVoteA(requestBody).subscribe((res) => {
      console.log(res);
      window.location.reload();
    });
  }

  downVoteA(answerId: number) {
    let requestBody: any = {
      userId: this.userId,
      answerId: answerId,
      value: -1,
    }
    console.log(requestBody);
    this.voteService.postVoteA(requestBody).subscribe((res) => {
      console.log(res);
      window.location.reload();
    });
  }

}
