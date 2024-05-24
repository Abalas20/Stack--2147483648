import { Component } from '@angular/core';
import { QuestionService } from '../../user-services/question-service/question.service';

@Component({
  selector: 'app-manage-users',
  templateUrl: './manage-users.component.html',
  styleUrl: './manage-users.component.scss'
})
export class ManageUsersComponent {

  constructor(  private service: QuestionService,) {
    this.getAllUsers();
   }

  users: any;

  toggleBan(userId: number) {
    console.log(userId);
   this.service.banUser(userId).subscribe((response) => {
      console.log(response);
      this.getAllUsers();
    });
  }

  total: number = 0;
  pageNum: number = 0;

  getAllUsers() {
    this.service.getAllUsers(this.pageNum).subscribe((response) => {
      console.log(response);
      this.users = response.users;
      this.total = response.totalPages * 5;
    });
  }

  pageIndexChange(event: any) {
    this.pageNum = event.pageIndex;
    this.getAllUsers();
  }
}
