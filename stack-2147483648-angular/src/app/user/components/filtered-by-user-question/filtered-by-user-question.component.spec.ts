import { ComponentFixture, TestBed } from '@angular/core/testing';

import { FilteredByUserQuestionComponent } from './filtered-by-user-question.component';

describe('FilteredByUserQuestionComponent', () => {
  let component: FilteredByUserQuestionComponent;
  let fixture: ComponentFixture<FilteredByUserQuestionComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [FilteredByUserQuestionComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(FilteredByUserQuestionComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
