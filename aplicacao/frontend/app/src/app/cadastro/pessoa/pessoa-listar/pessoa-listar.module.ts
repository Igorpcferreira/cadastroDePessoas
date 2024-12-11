import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';

import {PessoaListarRoutingModule} from './pessoa-listar-routing.module';
import {HttpClientModuleWrapperModule} from "../../http-client.module";
import {PessoaListarComponent} from "./pessoa-listar/pessoa-listar.component";
import {MatIcon} from "@angular/material/icon";

@NgModule({
  declarations: [
    PessoaListarComponent
  ],
  imports: [
    CommonModule,
    PessoaListarRoutingModule,
    HttpClientModuleWrapperModule,
    MatIcon
  ]
})
export class PessoaListarModule {
}
