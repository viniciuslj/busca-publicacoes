import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { SbpSharedModule } from 'app/shared';
import {
  DocumentoComponent,
  DocumentoDetailComponent,
  DocumentoUpdateComponent,
  DocumentoBatchComponent,
  DocumentoDeletePopupComponent,
  DocumentoDeleteDialogComponent,
  documentoRoute,
  documentoPopupRoute
} from './';

const ENTITY_STATES = [...documentoRoute, ...documentoPopupRoute];

@NgModule({
  imports: [SbpSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    DocumentoComponent,
    DocumentoDetailComponent,
    DocumentoUpdateComponent,
    DocumentoDeleteDialogComponent,
    DocumentoDeletePopupComponent,
    DocumentoBatchComponent
  ],
  entryComponents: [
    DocumentoComponent,
    DocumentoUpdateComponent,
    DocumentoDeleteDialogComponent,
    DocumentoDeletePopupComponent,
    DocumentoBatchComponent
  ],
  providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SbpDocumentoModule {
  constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
    this.languageHelper.language.subscribe((languageKey: string) => {
      if (languageKey !== undefined) {
        this.languageService.changeLanguage(languageKey);
      }
    });
  }
}
