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
    }
];
