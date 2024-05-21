import { Component } from '@angular/core';
import { MatDialogRef } from '@angular/material/dialog';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';

@Component({
  selector: 'app-dialog-box',
  templateUrl: './dialog-box.component.html',
  styleUrls: ['./dialog-box.component.scss']
})
export class DialogBoxComponent {
  validateForm: FormGroup;

  constructor(
    public dialogRef: MatDialogRef<DialogBoxComponent>,
    private fb: FormBuilder,
  ) {
    this.validateForm = this.fb.group({
      text: ['', Validators.required],
    });
  }

  onNoClick(): void {
    this.dialogRef.close();
  }

  onConfirmClick(): void {
    if (this.validateForm.valid) {
      this.dialogRef.close(this.validateForm.value['text']);
    }
  }
}
