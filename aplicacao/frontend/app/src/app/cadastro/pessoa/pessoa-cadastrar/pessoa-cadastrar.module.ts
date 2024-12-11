import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';

import {PessoaCadastrarRoutingModule} from './pessoa-cadastrar-routing.module';
import {PessoaCadastrarComponent} from "./pessoa-cadastrar/pessoa-cadastrar.component";
import {ReactiveFormsModule} from "@angular/forms";
import {HttpClientModuleWrapperModule} from "../../http-client.module";
import {NgxMaskModule} from "ngx-mask";

@NgModule({
  declarations: [
    PessoaCadastrarComponent
  ],
  imports: [
    CommonModule,
    PessoaCadastrarRoutingModule,
    ReactiveFormsModule,
    HttpClientModuleWrapperModule,
    NgxMaskModule.forRoot()
  ]
})

export class PessoaCadastrarModule {
}
