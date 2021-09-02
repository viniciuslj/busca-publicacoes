import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { LogBusca } from 'app/shared/model/log-busca.model';
import { LogBuscaService } from './log-busca.service';
import { LogBuscaComponent } from './log-busca.component';
import { ILogBusca } from 'app/shared/model/log-busca.model';

@Injectable({ providedIn: 'root' })
export class LogBuscaResolve implements Resolve<ILogBusca> {
  constructor(private service: LogBuscaService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<ILogBusca> {
    const id = route.params['id'] ? route.params['id'] : null;
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<LogBusca>) => response.ok),
        map((logBusca: HttpResponse<LogBusca>) => logBusca.body)
      );
    }
    return of(new LogBusca());
  }
}

export const logBuscaRoute: Routes = [
  {
    path: '',
    component: LogBuscaComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_ADMIN'],
      defaultSort: 'id,asc',
      pageTitle: 'sbpApp.logBusca.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
