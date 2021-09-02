import { IDiretorio } from 'app/shared/model/diretorio.model';
import { DocumentoService } from 'app/entities/documento';
import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { TreeNode } from 'primeng/components/common/treenode';
import { SelectItem } from 'primeng/components/common/selectitem';
import { ProcessingService } from 'app/shared';

@Component({
  selector: 'my-documento-batch',
  templateUrl: './documento-batch.component.html'
})
export class DocumentoBatchComponent implements OnInit {
  selecionados: string[];
  options: any[];
  diretorios: any;
  isProcessing = false;
  valueProgressBar = 0;
  total = 0;
  processados = 0;
  erros = 0;
  documentoAtual = '';
  log = '';

  constructor(
    protected activatedRoute: ActivatedRoute,
    protected documentoService: DocumentoService,
    protected processingService: ProcessingService
  ) {}

  ngOnInit() {
    this.processingService.show();
    this.documentoService.listBatch().subscribe(
      response => {
        this.diretorios = response.body;
        this.options = Object.keys(this.diretorios).map((k: any) => this.diretorios[k]);
        this.processingService.hide();
      },
      error => {
        this.processingService.hide();
      }
    );
  }

  previousState() {
    window.history.back();
  }

  async processar() {
    let sleep = 0;
    this.log = '';
    this.isProcessing = true;
    this.total = 0;
    this.processados = 0;
    this.erros = 0;
    this.selecionados.forEach(d => {
      this.total += this.diretorios[d].documentos.length;
    });

    for (let i = 0; i < this.selecionados.length; i++) {
      const diretorio = this.diretorios[this.selecionados[i]];
      let j = 0;
      while (j < diretorio.documentos.length) {
        const doc = diretorio.documentos[j];
        this.documentoAtual = `Processando ${doc.path} (${doc.nome})`;
        await this.documentoService
          .createBatch(doc)
          .toPromise()
          .then(
            respose => {
              this.processados++;
              this.valueProgressBar = Math.round((this.processados / this.total) * 100);
              sleep = 2000; // 2s
              j++;
            },
            error => {
              sleep += 60000; // +1min
              this.log += `Erro: ${doc.path}\\${doc.nome}\n`;
              if (sleep >= 600000) {
                // 10min
                // Desiste do arquivo
                this.erros++;
                j++;
              }
            }
          );

        this.documentoAtual = `Pausa de ${sleep / 1000} segundos`;
        await this.delay(sleep);
      }

      this.isProcessing = false;
      this.documentoAtual = '';
    }
  }

  delay(ms) {
    return new Promise(resolve => setTimeout(resolve, ms));
  }
}
