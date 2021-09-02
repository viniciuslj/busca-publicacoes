import { NgModule } from '@angular/core';
import {
  SbpSharedLibsModule,
  FindLanguageFromKeyPipe,
  JhiAlertComponent,
  JhiAlertErrorComponent,
  BuscaComponent,
  ProcessingComponent,
  GlobalMessageComponent,
  SafeHtmlPipe,
  LoginMaskPipe,
  RgMaskPipe,
  PdfViewDialogComponent
} from './';

@NgModule({
  imports: [SbpSharedLibsModule],
  declarations: [
    FindLanguageFromKeyPipe,
    JhiAlertComponent,
    JhiAlertErrorComponent,
    BuscaComponent,
    ProcessingComponent,
    GlobalMessageComponent,
    SafeHtmlPipe,
    LoginMaskPipe,
    RgMaskPipe,
    PdfViewDialogComponent
  ],
  exports: [
    SbpSharedLibsModule,
    FindLanguageFromKeyPipe,
    JhiAlertComponent,
    JhiAlertErrorComponent,
    BuscaComponent,
    ProcessingComponent,
    GlobalMessageComponent,
    SafeHtmlPipe,
    LoginMaskPipe,
    RgMaskPipe,
    PdfViewDialogComponent
  ]
})
export class SbpSharedCommonModule {}
