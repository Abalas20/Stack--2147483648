import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { DashboardComponent } from './components/dashboard/dashboard.component';
import { userGuard } from '../auth-guard/user-guard/user.guard';
import { PostQuestionComponent } from './components/post-question/post-question.component';
import { ViewQuestionComponent } from './components/view-question/view-question.component';
import { EditQuestionComponent } from './components/edit-question/edit-question.component';
import { EditAnswerComponent } from './components/edit-answer/edit-answer.component';

const routes: Routes = [
  {path:'dashboard', component: DashboardComponent, canActivate: [userGuard]},
  {path:'question', component: PostQuestionComponent, canActivate: [userGuard]},
  {path:'question/:questionId', component: ViewQuestionComponent, canActivate: [userGuard]},
  {path:'edit-question/:questionId', component: EditQuestionComponent, canActivate: [userGuard]},
  {path:'edit-answer/:answerId', component: EditAnswerComponent, canActivate: [userGuard]},
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class UserRoutingModule { }
