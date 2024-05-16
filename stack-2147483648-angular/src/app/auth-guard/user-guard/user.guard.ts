import { Injectable } from "@angular/core";
import { ActivatedRouteSnapshot, CanActivate, GuardResult, MaybeAsync, Router, RouterStateSnapshot } from "@angular/router";
import { StorageService } from "../../auth-services/storage/storage.service";
import { MatSnackBar } from "@angular/material/snack-bar";

@Injectable({
  providedIn: 'root'
})
  
export class userGuard implements CanActivate {

  constructor(
    private router: Router,
    private snackBar: MatSnackBar,
  ) {}

  canActivate(next: ActivatedRouteSnapshot, state: RouterStateSnapshot): boolean {
    if (!StorageService.hasToken()) {
      StorageService.logout();
      this.router.navigateByUrl("/login");
      this.snackBar.open(
        "You are not logged-in, Login first.", "Close", {duration: 5000}
      );
      return false;
    }
    return true;
  }
}