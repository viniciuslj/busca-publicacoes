import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Pagina } from 'app/shared/model/pagina.model';
import { PaginaService } from './pagina.service';
import { PaginaComponent } from './pagina.component';
import { PaginaDetailComponent } from './pagina-detail.component';
import { IPagina } from 'app/shared/model/pagina.model';

@Injectable({ providedIn: 'root' })
export class PaginaResolve implements Resolve<IPagina> {
  constructor(private service: PaginaService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IPagina> {
    const id = route.params['id'] ? route.params['id'] : null;
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<Pagina>) => response.ok),
        map((pagina: HttpResponse<Pagina>) => pagina.body)
      );
    }
    return of(new Pagina());
  }
}

export const paginaRoute: Routes = [
  {
    path: '',
    component: PaginaComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_USER'],
      defaultSort: 'id,asc',
      pageTitle: 'sbpApp.pagina.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'view',
    component: PaginaDetailComponent,
    resolve: {
      pagina: PaginaResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'sbpApp.pagina.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
