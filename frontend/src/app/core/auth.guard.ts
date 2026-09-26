import { inject } from '@angular/core';
import { CanActivateFn, Router } from '@angular/router';
import { StorageTokenService } from './storage/storage-token.service';


export const authGuard: CanActivateFn = () => {
  const router = inject(Router);

  if (StorageTokenService.getToken()) {
    return true;
  }

  router.navigate(['/connexion']);
  return false;
};