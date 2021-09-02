import { DocumentoService } from 'app/entities/documento';
import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { JhiDataUtils } from 'ng-jhipster';

import { IDocumento } from 'app/shared/model/documento.model';

import * as FileSaver from 'file-saver';

@Component({
  selector: 'jhi-documento-detail',
  templateUrl: './documento-detail.component.html'
})
export class DocumentoDetailComponent implements OnInit {
  documento: IDocumento;
  arquivoPdf: any = null;

  constructor(protected dataUtils: JhiDataUtils, protected activatedRoute: ActivatedRoute, protected documentoService: DocumentoService) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ documento }) => {
      this.documento = documento;
    });
  }

  byteSize(field) {
    return this.dataUtils.byteSize(field);
  }

  openFile(contentType, field) {
    return this.dataUtils.openFile(contentType, field);
  }
  previousState() {
    window.history.back();
  }

  downloadDocumento(documento: IDocumento) {
    this.documentoService.findWithConteudo(documento.id, 1, documento.quantidadePaginas).subscribe(file => {
      FileSaver.saveAs(file, documento.nome);
      // const reader = new FileReader();
      // reader.readAsDataURL(file);
      // reader.onloadend = () => {
      //   this.arquivoPdf = reader.result;
      // };
    });
  }
}
