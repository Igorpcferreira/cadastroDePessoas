import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';

import {PessoaEditarRoutingModule} from './pessoa-editar-routing.module';
import {ReactiveFormsModule} from "@angular/forms";
import {HttpClientModuleWrapperModule} from "../../http-client.module";
import {NgxMaskModule} from "ngx-mask";
import {PessoaEditarComponent} from "./pessoa-editar/pessoa-editar.component";

@NgModule({
  declarations: [
    PessoaEditarComponent
  ],
  imports: [
    CommonModule,
    PessoaEditarRoutingModule,
    ReactiveFormsModule,
    HttpClientModuleWrapperModule,
    NgxMaskModule.forRoot()
  ]
})
export class PessoaEditarModule {
}
