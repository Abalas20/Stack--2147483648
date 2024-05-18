import { Component, ElementRef, inject, OnInit, ViewChild} from '@angular/core';
import { QuestionService } from '../../user-services/question-service/question.service';
import { LiveAnnouncer } from '@angular/cdk/a11y';
import { MatChipInputEvent } from '@angular/material/chips';
import { COMMA, ENTER } from '@angular/cdk/keycodes';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { Observable, startWith, map } from 'rxjs';
import { TagService } from '../../user-services/question-service/tag.service';
import { MatAutocompleteSelectedEvent } from '@angular/material/autocomplete';
import { StorageService } from '../../../auth-services/storage/storage.service';

@Component({
  selector: 'app-post-question',
  templateUrl: './post-question.component.html',
  styleUrls: ['./post-question.component.scss']
})
export class PostQuestionComponent  {
  
  readonly separatorKeysCodes = [ENTER, COMMA] as const;

  tags: string[] = [];
  filteredTags: Observable<string[]>;
  allTags: string[] = [];
  tagCtrl = new FormControl('');

  @ViewChild('tagInput')
  tagInput!: ElementRef<HTMLInputElement>;

  isSubmitting: boolean = false;
  addOnBlur = true;
  validateForm: FormGroup;

  announcer = inject(LiveAnnouncer);

  constructor(
    private service: QuestionService,
    private tagService: TagService,
    private fb: FormBuilder,
  ) {
    this.validateForm = this.fb.group({
      title: ['', [Validators.required]],
      body: ['', [Validators.required]],
      tags: [[], [Validators.required]],
    });

    this.tagService.getTags().subscribe(tags => {
      this.allTags = tags;
    });

    this.filteredTags = this.tagCtrl.valueChanges.pipe(
      startWith(null),
      map((tag: string | null) => (tag ? this._filter(tag) : this.allTags.slice())),
    );
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
      tags: this.tags.map(tag => ({ tagName: tag }))
    };
    console.log(requestBody);
    this.isSubmitting = true;
    this.service.postQuestion(requestBody).subscribe((res) => {
      console.log(res);
      this.isSubmitting = false;
      this.validateForm.reset();
      this.tags = [];
    });
  }
}
