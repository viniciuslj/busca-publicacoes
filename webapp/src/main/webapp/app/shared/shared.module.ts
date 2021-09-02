import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { SbpSharedLibsModule, SbpSharedCommonModule, JhiLoginModalComponent, HasAnyAuthorityDirective } from './';

@NgModule({
  imports: [SbpSharedLibsModule, SbpSharedCommonModule],
  declarations: [JhiLoginModalComponent, HasAnyAuthorityDirective],
  entryComponents: [JhiLoginModalComponent],
  exports: [SbpSharedCommonModule, JhiLoginModalComponent, HasAnyAuthorityDirective],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SbpSharedModule {
  static forRoot() {
    return {
      ngModule: SbpSharedModule
    };
  }
}
