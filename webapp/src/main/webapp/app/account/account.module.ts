import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SbpSharedModule } from 'app/shared';

import { LoginComponent, accountState } from './';

@NgModule({
  imports: [SbpSharedModule, RouterModule.forChild(accountState)],
  declarations: [LoginComponent],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SbpAccountModule {}
