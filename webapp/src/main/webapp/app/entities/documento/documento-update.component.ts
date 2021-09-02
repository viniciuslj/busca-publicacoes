import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService, JhiDataUtils } from 'ng-jhipster';
import { IDocumento, Documento } from 'app/shared/model/documento.model';
import { DocumentoService } from './documento.service';
import { IDiretorio } from 'app/shared/model/diretorio.model';
import { DiretorioService } from 'app/entities/diretorio';
import { ProcessingService } from 'app/shared';

@Component({
  selector: 'jhi-documento-update',
  templateUrl: './documento-update.component.html'
})
export class DocumentoUpdateComponent implements OnInit {
  documento: IDocumento;

  diretorios: IDiretorio[];
  dataPublicacaoDp: any;

  file: File;

  editForm = this.fb.group({
    id: [],
    nome: [null, [Validators.required, Validators.maxLength(50)]],
    descricao: [],
    path: [null, Validators.required],
    dataPublicacao: [],
    diretorio: [null, Validators.required]
  });

  constructor(
    protected dataUtils: JhiDataUtils,
    protected jhiAlertService: JhiAlertService,
    protected documentoService: DocumentoService,
    protected diretorioService: DiretorioService,
    protected activatedRoute: ActivatedRoute,
    protected processingService: ProcessingService,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ documento }) => {
      this.updateForm(documento);
      this.documento = documento;
    });
    this.diretorioService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IDiretorio[]>) => mayBeOk.ok),
        map((response: HttpResponse<IDiretorio[]>) => response.body)
      )
      .subscribe((res: IDiretorio[]) => (this.diretorios = res), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(documento: IDocumento) {
    this.editForm.patchValue({
      id: documento.id,
      nome: documento.nome,
      descricao: documento.descricao,
      path: documento.path,
      dataPublicacao: documento.dataPublicacao,
      diretorio: documento.diretorio
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.processingService.show();
    const documento = this.createFromForm();
    if (documento.id !== undefined) {
      // this.subscribeToSaveResponse(this.documentoService.update(documento));
    } else {
      this.subscribeToSaveResponse(this.documentoService.create(documento, this.file));
    }
  }

  private createFromForm(): IDocumento {
    const entity = {
      ...new Documento(),
      id: this.editForm.get(['id']).value,
      nome: this.editForm.get(['nome']).value,
      descricao: this.editForm.get(['descricao']).value,
      path: this.editForm.get(['path']).value,
      dataPublicacao: this.editForm.get(['dataPublicacao']).value,
      diretorio: this.editForm.get(['diretorio']).value
    };
    return entity;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IDocumento>>) {
    result.subscribe((res: HttpResponse<IDocumento>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
  }

  protected onSaveSuccess() {
    this.processingService.hide();
    this.previousState();
  }

  protected onSaveError() {
    this.processingService.hide();
  }
  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }

  trackDiretorioById(index: number, item: IDiretorio) {
    return item.id;
  }

  handleFileInput(files: FileList) {
    this.file = files.item(0);
    this.editForm.patchValue({
      nome: this.file.name.split('.')[0]
    });
  }
}
