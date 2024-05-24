import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { DashboardComponent } from './components/dashboard/dashboard.component';
import { userGuard } from '../auth-guard/user-guard/user.guard';
import { PostQuestionComponent } from './components/post-question/post-question.component';
import { ViewQuestionComponent } from './components/view-question/view-question.component';
import { EditQuestionComponent } from './components/edit-question/edit-question.component';
import { EditAnswerComponent } from './components/edit-answer/edit-answer.component';
import { FilteredByTextQuestionComponent } from './components/filtered-by-text-question/filtered-by-text-question.component';
import { FilteredByUserQuestionComponent } from './components/filtered-by-user-question/filtered-by-user-question.component';
import { FilteredByTagQuestionComponent } from './components/filtered-by-tag-question/filtered-by-tag-question.component';
import { ManageUsersComponent } from './components/manage-users/manage-users.component';

const routes: Routes = [
  {path:'dashboard', component: DashboardComponent, canActivate: [userGuard]},
  {path:'question', component: PostQuestionComponent, canActivate: [userGuard]},
  {path:'question/:questionId', component: ViewQuestionComponent, canActivate: [userGuard]},
  {path:'edit-question/:questionId', component: EditQuestionComponent, canActivate: [userGuard]},
  {path:'edit-answer/:answerId', component: EditAnswerComponent, canActivate: [userGuard]},
  {path:'question/filtered/:text', component: FilteredByTextQuestionComponent, canActivate: [userGuard]},
  {path:'question/filtered/username/:username', component: FilteredByUserQuestionComponent, canActivate: [userGuard]},
  {path:'question/filtered/tag/:tag', component: FilteredByTagQuestionComponent, canActivate: [userGuard]},
  {path:'manage', component: ManageUsersComponent, canActivate: [userGuard]}
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class UserRoutingModule { }
