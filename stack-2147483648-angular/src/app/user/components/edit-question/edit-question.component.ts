import { Component, ElementRef, ViewChild, inject } from '@angular/core';
import { StorageService } from '../../../auth-services/storage/storage.service';
import { MatAutocompleteSelectedEvent } from '@angular/material/autocomplete';
import { MatChipInputEvent } from '@angular/material/chips';
import { Observable, map, startWith } from 'rxjs';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { LiveAnnouncer } from '@angular/cdk/a11y';
import { QuestionService } from '../../user-services/question-service/question.service';
import { TagService } from '../../user-services/question-service/tag.service';
import { COMMA, ENTER } from '@angular/cdk/keycodes';
import { ActivatedRoute } from '@angular/router';

@Component({
  selector: 'app-edit-question',
  templateUrl: './edit-question.component.html',
  styleUrl: './edit-question.component.scss'
})
export class EditQuestionComponent {
  
  readonly separatorKeysCodes = [ENTER, COMMA] as const;

  tags: string[] = [];
  filteredTags: Observable<string[]>;
  allTags: string[] = [];
  tagCtrl = new FormControl('');

  @ViewChild('tagInput')
  tagInput!: ElementRef<HTMLInputElement>;

  isSubmitting: boolean = false;
  addOnBlur = true;
  validateForm!: FormGroup;

  announcer = inject(LiveAnnouncer);

  questionId: number = this.route.snapshot.params['questionId'];
  question: any;

  constructor(
    private service: QuestionService,
    private tagService: TagService,
    private fb: FormBuilder,
    private route: ActivatedRoute,
  ) {
    this.getQuestionById(this.questionId);

    this.tagService.getTags().subscribe(tags => {
      this.tags = tags;
    });

    this.filteredTags = this.tagCtrl.valueChanges.pipe(
      startWith(null),
      map((tag: string | null) => (tag ? this._filter(tag) : this.allTags.slice())),
    );
  }

  getQuestionById(id: number): void {
    this.service.getQuestionById(id).subscribe(
      (response) => {
        this.question = response.question;
        console.log(response);

        this.initializeForm();
      },
      (error) => {
        console.log(error);
      }
    );
  }

  initializeForm(): void {
    this.validateForm = this.fb.group({
      title: [this.question.title, [Validators.required]],
      body: [this.question.body, [Validators.required]],
      url: [this.question.url, [Validators.required]],
      tags: [this.question.tags, [Validators.required]],
    });
  }

  add(event: MatChipInputEvent): void {
    const value = (event.value || '').trim();

    if (value && !this.tags.includes(value)) {
      this.tags.push(value);
      this.validateForm.controls['tags'].setValue(this.tags);
    }
    event.chipInput!.clear();

    this.tagCtrl.setValue(null);
  }

  remove(tag: any): void {
    const index = this.tags.indexOf(tag);

    if (index >= 0) {
      this.tags.splice(index, 1);

      this.announcer.announce(`Removed ${tag.name}`);
    }
  }

  selected(event: MatAutocompleteSelectedEvent): void {
    const value = event.option.viewValue;

    if (!this.tags.includes(value)) {
      this.tags.push(value);
      this.validateForm.controls['tags'].setValue(this.tags);
    }
    this.tagInput.nativeElement.value = '';
    this.tagCtrl.setValue(null);
  }

  private _filter(value: string): string[] {
    const filterValue = value.toLowerCase();

    return this.allTags.filter(tag => 
      tag.toLowerCase().includes(filterValue) && !this.tags.includes(tag)
    );
  }

  postQuestion(): void {
    let requestBody: any = {
      title: this.validateForm.value['title'],
      body: this.validateForm.value['body'],
      userId: StorageService.getUserId(),
      url: this.validateForm.value['url'],
      tags: this.tags.map(tag => ({ tagName: tag })),
      id: this.questionId,
    };
    console.log(requestBody);
    this.isSubmitting = true;
    this.service.updateQuestion(requestBody, StorageService.getUserId()).subscribe((res) => {
      console.log(res);
      this.isSubmitting = false;
      this.validateForm.reset();
      this.tags = [];
    });
  }
}
