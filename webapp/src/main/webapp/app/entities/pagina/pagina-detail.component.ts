import { MainService } from './../../layouts/main/main.service';
import { GlobalMessageService } from './../../shared/global-message/global-message.service';
import { IDocumento } from './../../shared/model/documento.model';
import { DocumentoService } from './../documento/documento.service';
import { PaginaService } from './pagina.service';
import { Component, OnInit, ViewChild } from '@angular/core';
import { ActivatedRoute, ActivatedRouteSnapshot, Router, NavigationExtras } from '@angular/router';
import { JhiDataUtils } from 'ng-jhipster';

import { IPagina } from 'app/shared/model/pagina.model';
import * as FileSaver from 'file-saver';
import { PdfViewerComponent, PDFProgressData } from 'ng2-pdf-viewer';
import { ProcessingService } from 'app/shared';
import { Location } from '@angular/common';
import { TreeNode } from 'primeng/api';

@Component({
  selector: 'jhi-pagina-detail',
  templateUrl: './pagina-detail.component.html',
  styleUrls: ['pagina.scss']
})
export class PaginaDetailComponent implements OnInit {
  documento: IDocumento;

  paginaInicial = 0;
  paginaFinal = 0;

  data: Blob = null;
  base64data: string = null;
  nameFile: string = null;
  query = '';

  displaySidebar = false;

  @ViewChild('pdfViewer') pdfViewer: PdfViewerComponent;

  documentos: IDocumento[] = null;
  filesTree: TreeNode[] = [];

  constructor(
    protected mainService: MainService,
    protected activatedRoute: ActivatedRoute,
    protected paginaService: PaginaService,
    protected documentoService: DocumentoService,
    protected processingService: ProcessingService,
    protected globalMessageService: GlobalMessageService,
    protected router: Router,
    protected location: Location
  ) {}

  ngOnInit() {
    this.mainService.hideNavbar();
    this.activatedRoute.queryParams.subscribe(params => {
      if (params !== null && params.id !== undefined) {
        this.paginaInicial = params.paginaInicial;
        this.paginaFinal = params.paginaFinal;

        this.documentoService.find(params.id).subscribe(response => {
          this.documento = response.body;

          if (this.validarInput()) {
            this.openPaginas(this.documento, this.paginaInicial, this.paginaFinal, this.query);
          }
        });
      } else {
        this.exibirExplorerDocumentos();
      }
    });

    this.documentoService.findAll().subscribe(response => {
      this.processarDocumentosFilesTree(response.body);
    });
  }

  processarDocumentosFilesTree(documentos) {
    this.documentos = documentos;
    const tree = {};

    for (let i = 0; i < documentos.length; i++) {
      const doc = documentos[i];

      const path = doc.path.split(/[\/,\\]+/);
      if (tree[path[0]] === undefined) {
        tree[path[0]] = {};
      }

      if (tree[path[0]][path[1]] === undefined) {
        tree[path[0]][path[1]] = [];
      }

      const t = tree[path[0]][path[1]].length;
      tree[path[0]][path[1]][t] = doc;
    }

    Object.keys(tree).forEach(dir => {
      const treeNode: TreeNode = {};
      treeNode.label = dir;
      treeNode.expandedIcon = 'fa fa-folder-open';
      treeNode.collapsedIcon = 'fa fa-folder';
      treeNode.children = [];

      Object.keys(tree[dir]).forEach(subDir => {
        const treeNode2: TreeNode = {};
        treeNode2.label = subDir;
        treeNode2.expandedIcon = 'fa fa-folder-open';
        treeNode2.collapsedIcon = 'fa fa-folder';
        treeNode2.children = [];

        tree[dir][subDir].forEach(doc => {
          const treeNode3: TreeNode = {};
          treeNode3.label = doc.nome;
          treeNode3.icon = 'fa fa-file-pdf';
          treeNode3.data = doc;
          treeNode2.children.push(treeNode3);
        });

        treeNode.children.push(treeNode2);
      });

      this.filesTree.push(treeNode);
    });
  }

  documentoSelect(event) {
    if (event.node.data !== undefined) {
      this.documento = event.node.data;
      this.paginaInicial = 1;
      this.paginaFinal = 10;
      this.openPaginas(this.documento, this.paginaInicial, this.paginaFinal, this.query);
      this.modificarUrl(this.documento, this.paginaInicial, this.paginaFinal, this.query);
    }
  }

  exibirExplorerDocumentos() {
    this.displaySidebar = true;
  }

  previousState() {
    window.history.back();
  }

  show(data: Blob, nameFile: string) {
    this.data = data;
    this.nameFile = nameFile;

    this.blobToBase64(data, (base64data: string) => {
      this.base64data = base64data;
    });
  }

  afterLoadComplete(event) {
    this.activatedRoute.queryParams.subscribe(params => {
      setTimeout(() => this.searchQueryChanged(params.query), 100);
    });
  }

  blobToBase64(data: Blob, callback: Function) {
    const reader = new FileReader();
    reader.readAsDataURL(data);
    reader.onloadend = () => {
      callback(reader.result);
    };
  }

  onProgress(progressData: PDFProgressData) {}

  openPaginas(documento: IDocumento, paginaInicial: number, paginaFinal: number, query: string) {
    this.processingService.show();
    this.documentoService.findWithConteudo(documento.id, paginaInicial, paginaFinal).subscribe(file => {
      this.show(file, `${documento.nome}-p${paginaInicial}-${paginaFinal}.pdf`);
      this.processingService.hide();
    });
  }

  validarInput() {
    if (
      this.paginaInicial > this.paginaFinal ||
      this.paginaFinal > this.documento.quantidadePaginas ||
      this.paginaInicial < 1 ||
      this.paginaFinal < 1
    ) {
      this.globalMessageService.error('Páginas inválidas');
      return false;
    }

    return true;
  }

  exibirPaginas() {
    if (this.validarInput()) {
      this.openPaginas(this.documento, this.paginaInicial, this.paginaFinal, this.query);
      this.modificarUrl(this.documento, this.paginaInicial, this.paginaFinal, this.query);
    }
  }

  modificarUrl(documento: IDocumento, paginaInicial: number, paginaFinal: number, query: string) {
    const navigationExtras: NavigationExtras = {
      queryParams: {
        id: documento.id,
        paginaInicial: paginaInicial,
        paginaFinal: paginaFinal,
        query: query
      }
    };

    const url = this.router.createUrlTree(['/pagina/view'], navigationExtras).toString();
    this.location.replaceState(url);
  }

  arquivoCompleto() {
    this.paginaInicial = 1;
    this.paginaFinal = this.documento.quantidadePaginas;
    this.exibirPaginas();
  }

  downloadPdf() {
    FileSaver.saveAs(this.data, this.nameFile);
  }

  searchQueryChanged(newQuery: string) {
    if (newQuery !== this.query) {
      this.query = newQuery;
      this.pdfViewer.pdfFindController.executeCommand('find', {
        query: this.query,
        highlightAll: true
      });
      this.modificarUrl(this.documento, this.paginaInicial, this.paginaFinal, this.query);
    } else {
      this.pdfViewer.pdfFindController.executeCommand('findagain', {
        query: this.query,
        highlightAll: true
      });
    }
  }
}
