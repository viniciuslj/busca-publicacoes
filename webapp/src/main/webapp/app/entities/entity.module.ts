import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'diretorio',
        loadChildren: './diretorio/diretorio.module#SbpDiretorioModule'
      },
      {
        path: 'documento',
        loadChildren: './documento/documento.module#SbpDocumentoModule'
      },
      {
        path: 'pagina',
        loadChildren: './pagina/pagina.module#SbpPaginaModule'
      },
      {
        path: 'cargo',
        loadChildren: './cargo/cargo.module#SbpCargoModule'
      },
      {
        path: 'quadro',
        loadChildren: './quadro/quadro.module#SbpQuadroModule'
      },
      {
        path: 'log-busca',
        loadChildren: './log-busca/log-busca.module#SbpLogBuscaModule'
      }
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ])
  ],
  declarations: [],
  entryComponents: [],
  providers: [],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SbpEntityModule {}
