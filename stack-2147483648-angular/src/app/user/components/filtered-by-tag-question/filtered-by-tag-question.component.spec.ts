import { ComponentFixture, TestBed } from '@angular/core/testing';

import { FilteredByTagQuestionComponent } from './filtered-by-tag-question.component';

describe('FilteredByTagQuestionComponent', () => {
  let component: FilteredByTagQuestionComponent;
  let fixture: ComponentFixture<FilteredByTagQuestionComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [FilteredByTagQuestionComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(FilteredByTagQuestionComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
