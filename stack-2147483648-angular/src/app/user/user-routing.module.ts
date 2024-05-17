import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { DashboardComponent } from './components/dashboard/dashboard.component';
import { userGuard } from '../auth-guard/user-guard/user.guard';
import { PostQuestionComponent } from './components/post-question/post-question.component';

const routes: Routes = [
  {path:'dashboard', component: DashboardComponent, canActivate: [userGuard]},
  {path:'question', component: PostQuestionComponent, canActivate: [userGuard]}
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class UserRoutingModule { }
