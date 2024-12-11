import {Component, OnInit} from '@angular/core';
import {FormBuilder, FormGroup} from '@angular/forms';
import {PessoaService} from '../../../pessoa.service';
import {Router} from "@angular/router";

@Component({
  selector: 'app-pessoa-cadastrar',
  templateUrl: './pessoa-cadastrar.component.html',
  styleUrls: ['./pessoa-cadastrar.component.scss']
})
export class PessoaCadastrarComponent implements OnInit {

  formGroup: FormGroup;

  constructor(
    private formBuilder: FormBuilder,
    private pessoaService: PessoaService,
    private router: Router
  ) {
    this.formGroup = this.formBuilder.group({
      nome: '',
      contato: this.formBuilder.group({
        telefone: '',
      }),
      endereco: this.formBuilder.group({
        bairro: ''
      })
    });
  }

  ngOnInit() {
  }

  private formatarTelefone(value: string) {
    if (value?.length == 10) {
      return `(${value.substring(0, 2)}) ${value.substring(2, 6)}-${value.substring(6, 10)}`;
    } else {
      return `(${value.substring(0, 2)}) ${value.substring(2, 7)}-${value.substring(7, 11)}`;
    }
  }

  salvar() {
    const telefoneAtual = this.formGroup.getRawValue().contato.telefone;
    const telefoneFormatado = this.formatarTelefone(telefoneAtual);

    this.formGroup.patchValue({
      contato: {
        telefone: telefoneFormatado
      }
    });

    this.pessoaService.incluir(this.formGroup.value).subscribe(() => {
      this.formGroup.reset();
      this.router.navigate(['/pessoa/listar']);
    });
  }
}
