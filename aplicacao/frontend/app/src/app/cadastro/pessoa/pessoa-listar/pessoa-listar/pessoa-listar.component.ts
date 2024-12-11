import {Component, OnInit} from '@angular/core';
import {Observable} from 'rxjs';
import {Pessoa} from '../../pessoa.model';
import {PessoaService} from '../../../pessoa.service';

@Component({
  selector: 'app-pessoa-listar',
  templateUrl: './pessoa-listar.component.html',
  styleUrls: ['./pessoa-listar.component.scss']
})
export class PessoaListarComponent implements OnInit {

  pessoas$!: Observable<Pessoa[]>;

  constructor(private pessoaService: PessoaService) {
  }

  ngOnInit() {
    this.listarPessoas();
  }

  listarPessoas() {
    this.pessoas$ = this.pessoaService.listar();
  }

  deletar(id: number) {
    if (confirm('Deseja realmente deletar esta pessoa?')) {
      this.pessoaService.deletar(id).subscribe(() => {
        this.listarPessoas();
      });
    }
  }
}
