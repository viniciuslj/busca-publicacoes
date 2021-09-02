import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { NgJhipsterModule } from 'ng-jhipster';
import { InfiniteScrollModule } from 'ngx-infinite-scroll';
import { CookieModule } from 'ngx-cookie';
import { FontAwesomeModule } from '@fortawesome/angular-fontawesome';

import { PdfViewerModule } from 'ng2-pdf-viewer';

// PrimeNG
import { DialogModule } from 'primeng/dialog';
import { CheckboxModule } from 'primeng/checkbox';
import { ListboxModule } from 'primeng/listbox';
import { ProgressBarModule } from 'primeng/progressbar';
import { TreeModule } from 'primeng/tree';
import { ConfirmDialogModule } from 'primeng/confirmdialog';
import { BlockUIModule } from 'primeng/blockui';
import { ToastModule } from 'primeng/toast';
import { SpinnerModule } from 'primeng/spinner';
import { SidebarModule } from 'primeng/sidebar';

@NgModule({
  imports: [
    NgbModule,
    InfiniteScrollModule,
    CookieModule.forRoot(),
    FontAwesomeModule,
    ReactiveFormsModule,
    PdfViewerModule,
    DialogModule,
    CheckboxModule,
    ListboxModule,
    ProgressBarModule,
    TreeModule,
    ConfirmDialogModule,
    BlockUIModule,
    ToastModule,
    SpinnerModule,
    SidebarModule
  ],
  exports: [
    FormsModule,
    CommonModule,
    NgbModule,
    NgJhipsterModule,
    InfiniteScrollModule,
    FontAwesomeModule,
    ReactiveFormsModule,
    PdfViewerModule,
    DialogModule,
    CheckboxModule,
    ListboxModule,
    ProgressBarModule,
    TreeModule,
    ConfirmDialogModule,
    BlockUIModule,
    ToastModule,
    SpinnerModule,
    SidebarModule
  ]
})
export class SbpSharedLibsModule {
  static forRoot() {
    return {
      ngModule: SbpSharedLibsModule
    };
  }
}
