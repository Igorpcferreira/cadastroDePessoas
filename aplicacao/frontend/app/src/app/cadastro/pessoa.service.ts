import {Injectable} from '@angular/core';
import {Observable, throwError} from 'rxjs';
import {catchError} from 'rxjs/operators';
import {Pessoa} from './pessoa/pessoa.model';
import {HttpClient, HttpErrorResponse} from "@angular/common/http";
import {HttpClientModuleWrapperModule} from "./http-client.module";

@Injectable({
  providedIn: HttpClientModuleWrapperModule
})
export class PessoaService {

  private baseUrl = 'http://localhost:8080';
  private endpoint = '/cadastro/pessoa';

  constructor(private httpClient: HttpClient) {
  }

  private handleError(error: HttpErrorResponse): Observable<any> {
    let errorMessage = 'Unknown error occurred';
    if (error.error instanceof ErrorEvent) {
      errorMessage = `Error: ${error.error.message}`;
    } else {
      errorMessage = error.error.message || `${error.status} - ${error.statusText}`;
    }
    alert(errorMessage);
    return throwError(errorMessage);
  }

  listar(): Observable<Pessoa[]> {
    return this.httpClient.get<Pessoa[]>(`${this.baseUrl}${this.endpoint}/listar`)
      .pipe(
        catchError(this.handleError)
      );
  }

  consultar(id: number): Observable<Pessoa> {
    return this.httpClient.get<Pessoa>(`${this.baseUrl}${this.endpoint}/${id}`)
      .pipe(
        catchError(this.handleError)
      );
  }

  incluir(pessoa: Pessoa): Observable<Pessoa> {
    return this.httpClient.post<Pessoa>(`${this.baseUrl}${this.endpoint}`, pessoa)
      .pipe(
        catchError(this.handleError)
      );
  }

  alterar(id: number, pessoa: Pessoa): Observable<Pessoa> {
    return this.httpClient.put<Pessoa>(`${this.baseUrl}${this.endpoint}/${id}`, pessoa)
      .pipe(
        catchError(this.handleError)
      );
  }

  deletar(id: number): Observable<void> {
    return this.httpClient.delete<void>(`${this.baseUrl}${this.endpoint}/${id}`)
      .pipe(
        catchError(this.handleError)
      );
  }
}
