import { Component } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { AnswerServiceService } from '../../user-services/answer-service.service';
import { StorageService } from '../../../auth-services/storage/storage.service';

@Component({
  selector: 'app-edit-answer',
  templateUrl: './edit-answer.component.html',
  styleUrl: './edit-answer.component.scss'
})
export class EditAnswerComponent {

  validateForm!: FormGroup;
  questionId: number = this.route.snapshot.params['questionId'];
  answer: any;
  isSubmitting: boolean = false;

  constructor(
    private service: AnswerServiceService,
    private fb: FormBuilder,
    private route: ActivatedRoute,
  ) {
    this.service.getAnswerById(this.route.snapshot.params['answerId']).subscribe(
      (response) => {
        this.answer = response;
        console.log(response);
        this.initializeForm();
        this.questionId = this.answer.questionId;
      },
      (error) => {
        console.log(error);
      }
    );
  }

  initializeForm(): void {
    this.validateForm = this.fb.group({
      body: [this.answer.body, [Validators.required]],
      url: [this.answer.url, [Validators.required]],
    });
  }

  addAnswer() {
    let requestBody: any = {
      body: this.validateForm.value.body,
      userId: StorageService.getUserId(),
      url: this.validateForm.value['url'],
      questionId: this.questionId,
      id: this.answer.id
    }
    console.log(requestBody);
    this.service.updateAnswer(requestBody, StorageService.getUserId(), StorageService.getUserRole()).subscribe((res) => {
      console.log(res);
      this.validateForm.reset();
      window.location.reload();
    });
  }
}
