import { Injectable } from "@angular/core";
import { ActivatedRouteSnapshot, CanActivate, GuardResult, MaybeAsync, Router, RouterStateSnapshot } from "@angular/router";
import { StorageService } from "../../auth-services/storage/storage.service";

@Injectable({
  providedIn: 'root'
})

export class NoAuthGuard implements CanActivate {

  constructor(
    private router: Router,
  ) {}

  canActivate(next: ActivatedRouteSnapshot, state: RouterStateSnapshot): boolean {
    if (StorageService.hasToken()) {
      this.router.navigateByUrl("/user/dashboard");
      return false;
    }
    return true;
  }

}