import { DocumentoService } from 'app/entities/documento';
import { IDocumento } from './../../shared/model/documento.model';
import { Component, OnInit, OnDestroy, ViewChild } from '@angular/core';
import { HttpErrorResponse, HttpHeaders, HttpResponse } from '@angular/common/http';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiParseLinks, JhiAlertService, JhiDataUtils } from 'ng-jhipster';

import { IPagina } from 'app/shared/model/pagina.model';
import { AccountService } from 'app/core';
import * as FileSaver from 'file-saver';

import { ITEMS_PER_PAGE, PdfViewDialogComponent, ProcessingService } from 'app/shared';
import { PaginaService } from './pagina.service';

@Component({
  selector: 'jhi-pagina',
  templateUrl: './pagina.component.html'
})
export class PaginaComponent implements OnInit, OnDestroy {
  currentAccount: any;
  paginas: IPagina[];
  error: any;
  success: any;
  eventSubscriber: Subscription;
  currentSearch: string;
  currentSearchClean: string;
  routeData: any;
  links: any;
  totalItems: any;
  itemsPerPage: any;
  page: any;
  predicate: any;
  previousPage: any;
  reverse: any;

  @ViewChild('pdfViewDialog') pdfViewDialog: PdfViewDialogComponent;

  constructor(
    protected paginaService: PaginaService,
    protected documentoService: DocumentoService,
    protected parseLinks: JhiParseLinks,
    protected jhiAlertService: JhiAlertService,
    protected accountService: AccountService,
    protected activatedRoute: ActivatedRoute,
    protected dataUtils: JhiDataUtils,
    protected router: Router,
    protected eventManager: JhiEventManager,
    protected processingService: ProcessingService
  ) {
    this.itemsPerPage = ITEMS_PER_PAGE;
    this.routeData = this.activatedRoute.data.subscribe(data => {
      this.page = data.pagingParams.page;
      this.previousPage = data.pagingParams.page;
      this.reverse = data.pagingParams.ascending;
      this.predicate = data.pagingParams.predicate;
    });
    this.currentSearch =
      this.activatedRoute.snapshot && this.activatedRoute.snapshot.params['search'] ? this.activatedRoute.snapshot.params['search'] : '';
  }

  loadAll() {
    this.currentSearch = this.currentSearch.replace(/"/g, '');

    if (this.currentSearch) {
      this.processingService.show();
      this.paginaService
        .search({
          page: this.page - 1,
          query: this.currentSearch.indexOf('*') >= 0 ? this.currentSearch : `"${this.currentSearch}"`,
          size: this.itemsPerPage,
          sort: this.sort()
        })
        .subscribe(
          (res: HttpResponse<IPagina[]>) => this.paginatePaginas(res.body, res.headers),
          (res: HttpErrorResponse) => this.onError(res.message)
        );
      return;
    }
  }

  loadPage(page: number) {
    if (page !== this.previousPage) {
      this.previousPage = page;
      this.transition();
    }
  }

  transition() {
    this.router.navigate(['/pagina'], {
      queryParams: {
        page: this.page,
        size: this.itemsPerPage,
        search: this.currentSearch,
        sort: this.predicate + ',' + (this.reverse ? 'asc' : 'desc')
      }
    });
    this.loadAll();
  }

  clear() {
    this.page = 0;
    this.currentSearch = '';
    this.router.navigate([
      '/pagina',
      {
        page: this.page,
        sort: this.predicate + ',' + (this.reverse ? 'asc' : 'desc')
      }
    ]);
    this.loadAll();
  }

  search(query) {
    if (!query) {
      return this.clear();
    }
    this.page = 0;
    this.currentSearch = query;
    this.router.navigate([
      '/pagina',
      {
        search: this.currentSearch,
        page: this.page,
        sort: this.predicate + ',' + (this.reverse ? 'asc' : 'desc')
      }
    ]);
    this.loadAll();
  }

  ngOnInit() {
    this.loadAll();
    this.accountService.identity().then(account => {
      this.currentAccount = account;
    });
    this.registerChangeInPaginas();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IPagina) {
    return item.id;
  }

  byteSize(field) {
    return this.dataUtils.byteSize(field);
  }

  openFile(contentType, field) {
    return this.dataUtils.openFile(contentType, field);
  }

  registerChangeInPaginas() {
    this.eventSubscriber = this.eventManager.subscribe('paginaListModification', response => this.loadAll());
  }

  sort() {
    const result = [this.predicate + ',' + (this.reverse ? 'asc' : 'desc')];
    if (this.predicate !== 'id') {
      result.push('id');
    }
    return result;
  }

  protected paginatePaginas(data: IPagina[], headers: HttpHeaders) {
    this.links = this.parseLinks.parse(headers.get('link'));
    this.totalItems = parseInt(headers.get('X-Total-Count'), 10);

    this.currentSearchClean = this.currentSearch.replace(/\*/g, '');

    const regex = new RegExp(`(${this.currentSearchClean})`, 'gim');
    this.paginas = data.map((pagina: IPagina) => {
      pagina.conteudo = pagina.conteudo.replace(regex, '<mark>$1</mark>');
      return pagina;
    });

    this.processingService.hide();
  }

  protected onError(errorMessage: string) {
    this.processingService.hide();
    this.jhiAlertService.error(errorMessage, null, null);
  }

  downloadDocumento(documento: IDocumento) {
    this.processingService.show();
    this.documentoService.findWithConteudo(documento.id, 1, documento.quantidadePaginas).subscribe(file => {
      FileSaver.saveAs(file, `${documento.nome}.pdf`);
      this.processingService.hide();
    });
  }
}
