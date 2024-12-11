import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {PessoaCadastrarComponent} from "./pessoa-cadastrar/pessoa-cadastrar.component";

const routes: Routes = [
  {path: '', component: PessoaCadastrarComponent}
];

@NgModule({
  imports: [RouterModule.forChild(routes),],
  exports: [RouterModule]
})
export class PessoaCadastrarRoutingModule {
}
