import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IDiretorio } from 'app/shared/model/diretorio.model';
import { DiretorioService } from './diretorio.service';

@Component({
  selector: 'jhi-diretorio-delete-dialog',
  templateUrl: './diretorio-delete-dialog.component.html'
})
export class DiretorioDeleteDialogComponent {
  diretorio: IDiretorio;

  constructor(protected diretorioService: DiretorioService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.diretorioService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'diretorioListModification',
        content: 'Deleted an diretorio'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-diretorio-delete-popup',
  template: ''
})
export class DiretorioDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ diretorio }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(DiretorioDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.diretorio = diretorio;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/diretorio', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/diretorio', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          }
        );
      }, 0);
    });
  }

  ngOnDestroy() {
    this.ngbModalRef = null;
  }
}
