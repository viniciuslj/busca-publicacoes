import { Component, OnInit, Input, Output, EventEmitter, ViewChild } from '@angular/core';
import { Dialog } from 'primeng/dialog';

import * as FileSaver from 'file-saver';
import { PdfViewerComponent, PDFProgressData } from 'ng2-pdf-viewer';

@Component({
  selector: 'my-pdf-view-dialog',
  templateUrl: './pdf-view-dialog.component.html',
  styles: []
})
export class PdfViewDialogComponent implements OnInit {
  @Input() header = '';
  @Output() onClose = new EventEmitter();
  @Input() modal = false;
  data: Blob = null;
  base64data: string = null;
  nameFile: string = null;
  query: string = null;
  page: number;
  visible = false;

  @ViewChild('dialog') dialog: Dialog;
  @ViewChild('pdfViewer') pdfViewer: PdfViewerComponent;

  constructor() {}

  ngOnInit() {}

  show(data: Blob, nameFile: string, query: string, page?: number) {
    this.data = data;
    this.nameFile = nameFile;
    this.query = query;
    this.page = page ? page : 1;

    this.blobToBase64(data, (base64data: string) => {
      this.base64data = base64data;
      this.visible = true;
    });
  }

  onShow(event, dialog: Dialog) {}

  onHide() {
    this.onClose.emit();
    this.base64data = null;
  }

  hide() {
    this.visible = false;
  }

  afterLoadComplete(event) {
    // this.dialog.maximize();
    setTimeout(() => this.search(this.query), 100);
  }

  blobToBase64(data: Blob, callback: Function) {
    const reader = new FileReader();
    reader.readAsDataURL(data);
    reader.onloadend = () => {
      callback(reader.result);
    };
  }

  downloadPdf() {
    FileSaver.saveAs(this.data, this.nameFile);
  }

  search(stringToSearch: string) {
    this.pdfViewer.pdfFindController.executeCommand('find', {
      caseSensitive: false,
      findPrevious: undefined,
      highlightAll: true,
      phraseSearch: true,
      query: stringToSearch
    });
  }

  onProgress(progressData: PDFProgressData) {}
}
