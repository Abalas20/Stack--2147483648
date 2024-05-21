import { ComponentFixture, TestBed } from '@angular/core/testing';

import { FilteredByTextQuestionComponent } from './filtered-by-text-question.component';

describe('FilteredByTextQuestionComponent', () => {
  let component: FilteredByTextQuestionComponent;
  let fixture: ComponentFixture<FilteredByTextQuestionComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [FilteredByTextQuestionComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(FilteredByTextQuestionComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
