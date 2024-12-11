import {Routes} from '@angular/router';
import {HomeComponent} from "./core/home/home.component";

export const routes: Routes = [
  {path: "", component: HomeComponent},
  {
    path: 'pessoa/listar',
    loadChildren: () => import('./cadastro/pessoa/pessoa-listar/pessoa-listar.module').then(modulo => modulo.PessoaListarModule)
  },
  {
    path: 'pessoa/cadastrar',
    loadChildren: () => import('./cadastro/pessoa/pessoa-cadastrar/pessoa-cadastrar.module').then(modulo => modulo.PessoaCadastrarModule)
  },
  {
    path: 'pessoa/editar/:id',
    loadChildren: () => import('./cadastro/pessoa/pessoa-editar/pessoa-editar.module').then(modulo => modulo.PessoaEditarModule)
  },
];
