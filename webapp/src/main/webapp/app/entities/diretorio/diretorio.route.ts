import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Diretorio } from 'app/shared/model/diretorio.model';
import { DiretorioService } from './diretorio.service';
import { DiretorioComponent } from './diretorio.component';
import { DiretorioDetailComponent } from './diretorio-detail.component';
import { DiretorioUpdateComponent } from './diretorio-update.component';
import { DiretorioDeletePopupComponent } from './diretorio-delete-dialog.component';
import { IDiretorio } from 'app/shared/model/diretorio.model';

@Injectable({ providedIn: 'root' })
export class DiretorioResolve implements Resolve<IDiretorio> {
  constructor(private service: DiretorioService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IDiretorio> {
    const id = route.params['id'] ? route.params['id'] : null;
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<Diretorio>) => response.ok),
        map((diretorio: HttpResponse<Diretorio>) => diretorio.body)
      );
    }
    return of(new Diretorio());
  }
}

export const diretorioRoute: Routes = [
  {
    path: '',
    component: DiretorioComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_ADMIN'],
      defaultSort: 'id,asc',
      pageTitle: 'sbpApp.diretorio.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: DiretorioDetailComponent,
    resolve: {
      diretorio: DiretorioResolve
    },
    data: {
      authorities: ['ROLE_ADMIN'],
      pageTitle: 'sbpApp.diretorio.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: DiretorioUpdateComponent,
    resolve: {
      diretorio: DiretorioResolve
    },
    data: {
      authorities: ['ROLE_ADMIN'],
      pageTitle: 'sbpApp.diretorio.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: DiretorioUpdateComponent,
    resolve: {
      diretorio: DiretorioResolve
    },
    data: {
      authorities: ['ROLE_ADMIN'],
      pageTitle: 'sbpApp.diretorio.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const diretorioPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: DiretorioDeletePopupComponent,
    resolve: {
      diretorio: DiretorioResolve
    },
    data: {
      authorities: ['ROLE_ADMIN'],
      pageTitle: 'sbpApp.diretorio.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
