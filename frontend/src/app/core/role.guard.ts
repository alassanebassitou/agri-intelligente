import { CanActivateFn } from "@angular/router";
import { inject } from "@angular/core";
import { Router } from "@angular/router";
import { StorageTokenService } from "./storage/storage-token.service";


export function roleGuard(rolesAutorises: string[]): CanActivateFn {
  return () => {
    const router = inject(Router);
    const role = StorageTokenService.getUserRole();

    if (rolesAutorises.includes(role)) {
      return true;
    }

    router.navigate(['/connexion']);
    return false;
  };
}