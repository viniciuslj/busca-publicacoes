import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { SbpSharedModule } from 'app/shared';
import { LogBuscaComponent, logBuscaRoute } from './';

const ENTITY_STATES = [...logBuscaRoute];

@NgModule({
  imports: [SbpSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [LogBuscaComponent],
  entryComponents: [LogBuscaComponent],
  providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SbpLogBuscaModule {
  constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
    this.languageHelper.language.subscribe((languageKey: string) => {
      if (languageKey !== undefined) {
        this.languageService.changeLanguage(languageKey);
      }
    });
  }
}
