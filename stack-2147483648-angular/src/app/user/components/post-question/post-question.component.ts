import { Component, inject, OnInit} from '@angular/core';
import { QuestionService } from '../../user-services/question-service/question.service';
import { LiveAnnouncer } from '@angular/cdk/a11y';
import { MatChipEditedEvent, MatChipInputEvent } from '@angular/material/chips';
import { COMMA, ENTER } from '@angular/cdk/keycodes';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Observable, startWith, map, of } from 'rxjs';
import { TagService } from '../../user-services/question-service/tag.service';

@Component({
  selector: 'app-post-question',
  templateUrl: './post-question.component.html',
  styleUrls: ['./post-question.component.scss']
})
export class PostQuestionComponent  {

  tags: { name: string }[] = [];
  isSubmitting: boolean = false;
  addOnBlur = true;
  validateForm: FormGroup;
  allTags: string[] = [];
  filteredTags: Observable<string[]>;

  readonly separatorKeysCodes = [ENTER, COMMA] as const;
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

    this.filteredTags = this.validateForm.get('tags')!.valueChanges.pipe(
      startWith(''),
      map((tag: string | null) => tag ? this.filterTags(tag) : this.allTags.slice())
    );
  }

  ngOnInit(): void {}

  add(event: MatChipInputEvent): void {
    const value = (event.value || '').trim();
    if (value) {
      this.tags.push({ name: value });
    }
    event.chipInput!.clear();
  }

  remove(tag: any): void {
    const index = this.tags.indexOf(tag);
    if (index >= 0) {
      this.tags.splice(index, 1);
      this.announcer.announce(`Removed ${tag.name}`);
    }
  }

  edit(tag: any, event: MatChipEditedEvent): void {
    const value = event.value.trim();

    if (!value) {
      this.remove(tag);
      return;
    }
    const index = this.tags.indexOf(tag);
    if (index >= 0) {
      this.tags[index] = { name: value };
      this.announcer.announce(`Edited ${tag.name} to ${value}`);
    }
  }

  filterTags(value: string | null): string[] {
    const filterValue = (value || '').toLowerCase();
    return this.allTags.filter(tag => tag.toLowerCase().includes(filterValue));
  }

  postQuestion(): void {
    this.isSubmitting = true;
    this.service.postQuestion(this.validateForm.value).subscribe((res) => {
      console.log(res);
      this.isSubmitting = false;
      this.validateForm.reset();
      this.tags = [];
    });
  }
}
