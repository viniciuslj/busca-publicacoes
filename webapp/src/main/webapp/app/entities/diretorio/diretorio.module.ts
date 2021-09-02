import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { SbpSharedModule } from 'app/shared';
import {
  DiretorioComponent,
  DiretorioDetailComponent,
  DiretorioUpdateComponent,
  DiretorioDeletePopupComponent,
  DiretorioDeleteDialogComponent,
  diretorioRoute,
  diretorioPopupRoute
} from './';

const ENTITY_STATES = [...diretorioRoute, ...diretorioPopupRoute];

@NgModule({
  imports: [SbpSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    DiretorioComponent,
    DiretorioDetailComponent,
    DiretorioUpdateComponent,
    DiretorioDeleteDialogComponent,
    DiretorioDeletePopupComponent
  ],
  entryComponents: [DiretorioComponent, DiretorioUpdateComponent, DiretorioDeleteDialogComponent, DiretorioDeletePopupComponent],
  providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SbpDiretorioModule {
  constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
    this.languageHelper.language.subscribe((languageKey: string) => {
      if (languageKey !== undefined) {
        this.languageService.changeLanguage(languageKey);
      }
    });
  }
}
