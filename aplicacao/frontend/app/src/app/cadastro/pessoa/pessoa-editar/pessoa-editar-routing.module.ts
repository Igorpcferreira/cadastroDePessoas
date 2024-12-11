import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {PessoaEditarComponent} from "./pessoa-editar/pessoa-editar.component";

const routes: Routes = [
  {path: '', component: PessoaEditarComponent}
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class PessoaEditarRoutingModule {
}
