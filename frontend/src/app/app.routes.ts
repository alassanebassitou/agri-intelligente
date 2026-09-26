import { Routes } from '@angular/router';
import { roleGuard } from './core/role.guard';
import { authGuard } from './core/auth.guard';

export const routes: Routes = [
    {
        path: '',
        redirectTo: 'connexion',
        pathMatch: 'full'
    },
    {
        path:'',
        loadChildren: () => import('./features/auth/auth.routes').then(m => m.AUTH_ROUTES)
    },
    {
        path: 'accueil',
        canActivate: [authGuard, roleGuard(['AGRICULTEUR'])],
        loadChildren: () => import('./features/agriculteur/agriculteur.route').then(m => m.AGRICULTEUR_ROUTES)
    },
    {
    path: 'admin',
    canActivate: [authGuard, roleGuard(['ADMIN'])],
    loadChildren: () => import('./features/administrateur/admin.routes').then(m => m.ADMIN_ROUTES)
}
];
