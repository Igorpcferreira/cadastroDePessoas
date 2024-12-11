import {Component, OnInit} from '@angular/core';
import {FormBuilder, FormGroup} from "@angular/forms";
import {PessoaService} from "../../../pessoa.service";
import {ActivatedRoute, Router} from "@angular/router";

@Component({
  selector: 'app-pessoa-editar',
  templateUrl: './pessoa-editar.component.html',
  styleUrl: './pessoa-editar.component.scss'
})
export class PessoaEditarComponent implements OnInit {

  formGroup: FormGroup;

  constructor(
    private formBuilder: FormBuilder,
    private pessoaService: PessoaService,
    private route: ActivatedRoute,
    private router: Router
  ) {
    this.formGroup = this.formBuilder.group({
      id: '',
      nome: '',
      contato: this.formBuilder.group({
        id: '',
        telefone: '',
      }),
      endereco: this.formBuilder.group({
        id: '',
        bairro: ''
      })
    });
  }

  ngOnInit() {
    this.consultar();
  }

  private formatarTelefone(value: string) {
    if (value?.length == 10) {
      return `(${value.substring(0, 2)}) ${value.substring(2, 6)}-${value.substring(6, 10)}`;
    } else if (value?.length == 11) {
      return `(${value.substring(0, 2)}) ${value.substring(2, 7)}-${value.substring(7, 11)}`;
    } else {
      return value;
    }
  }

  consultar() {
    const id = this.route.snapshot.paramMap.get('id');
    if (id) {
      this.pessoaService.consultar(Number(id)).subscribe(response => {
        this.formGroup.patchValue(response);
      });
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

    const formValue = this.formGroup.getRawValue();
    this.pessoaService.alterar(formValue.id, formValue).subscribe(response => {
      this.router.navigate(['/pessoa/listar']);
    });
  }
}
