import { Routes } from '@angular/router';

import { loginRoute } from './';

const ACCOUNT_ROUTES = [loginRoute];

export const accountState: Routes = [
  {
    path: '',
    children: ACCOUNT_ROUTES
  }
];
