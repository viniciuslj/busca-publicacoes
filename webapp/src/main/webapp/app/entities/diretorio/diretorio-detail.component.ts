import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IDiretorio } from 'app/shared/model/diretorio.model';

@Component({
  selector: 'jhi-diretorio-detail',
  templateUrl: './diretorio-detail.component.html'
})
export class DiretorioDetailComponent implements OnInit {
  diretorio: IDiretorio;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ diretorio }) => {
      this.diretorio = diretorio;
    });
  }

  previousState() {
    window.history.back();
  }
}
