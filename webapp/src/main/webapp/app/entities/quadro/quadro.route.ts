import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Quadro } from 'app/shared/model/quadro.model';
import { QuadroService } from './quadro.service';
import { IQuadro } from 'app/shared/model/quadro.model';

@Injectable({ providedIn: 'root' })
export class QuadroResolve implements Resolve<IQuadro> {
  constructor(private service: QuadroService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IQuadro> {
    const id = route.params['id'] ? route.params['id'] : null;
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<Quadro>) => response.ok),
        map((quadro: HttpResponse<Quadro>) => quadro.body)
      );
    }
    return of(new Quadro());
  }
}

export const quadroRoute: Routes = [];

export const quadroPopupRoute: Routes = [];
