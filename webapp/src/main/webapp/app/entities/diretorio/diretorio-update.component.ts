import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { IDiretorio, Diretorio } from 'app/shared/model/diretorio.model';
import { DiretorioService } from './diretorio.service';

@Component({
  selector: 'jhi-diretorio-update',
  templateUrl: './diretorio-update.component.html'
})
export class DiretorioUpdateComponent implements OnInit {
  diretorio: IDiretorio;
  isSaving: boolean;

  editForm = this.fb.group({
    id: [],
    nome: [null, [Validators.required, Validators.maxLength(50)]],
    descricao: [],
    restrito: [null, [Validators.required]],
    filtroData: [null, [Validators.required]]
  });

  constructor(protected diretorioService: DiretorioService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ diretorio }) => {
      this.updateForm(diretorio);
      this.diretorio = diretorio;
    });
  }

  updateForm(diretorio: IDiretorio) {
    this.editForm.patchValue({
      id: diretorio.id,
      nome: diretorio.nome,
      descricao: diretorio.descricao,
      restrito: diretorio.restrito,
      filtroData: diretorio.filtroData
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const diretorio = this.createFromForm();
    if (diretorio.id !== undefined) {
      this.subscribeToSaveResponse(this.diretorioService.update(diretorio));
    } else {
      this.subscribeToSaveResponse(this.diretorioService.create(diretorio));
    }
  }

  private createFromForm(): IDiretorio {
    const entity = {
      ...new Diretorio(),
      id: this.editForm.get(['id']).value,
      nome: this.editForm.get(['nome']).value,
      descricao: this.editForm.get(['descricao']).value,
      restrito: this.editForm.get(['restrito']).value,
      filtroData: this.editForm.get(['filtroData']).value
    };
    return entity;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IDiretorio>>) {
    result.subscribe((res: HttpResponse<IDiretorio>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
  }

  protected onSaveSuccess() {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError() {
    this.isSaving = false;
  }
}
