import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {PessoaListarComponent} from "./pessoa-listar/pessoa-listar.component";

const routes: Routes = [
  {path: "", component: PessoaListarComponent},
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class PessoaListarRoutingModule {
}
