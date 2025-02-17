import { HttpClient, HttpHeaders, HttpResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { ToastrService } from 'ngx-toastr';
import { catchError, Observable, tap, throwError } from 'rxjs';
import { Tarefa } from '../../model/Tarefa';

@Injectable({
  providedIn: 'root'
})
export class TodoServiceService {
  public domain: string = 'http://localhost:8080/api/tarefas';

  constructor(
    private httpClient: HttpClient,
    private toastr: ToastrService
  ) {
  }

  create(url: string, tarefa: Tarefa): Observable<HttpResponse<any>> {
    return this.httpClient
      .post<any>(this.domain + url, tarefa, { observe: 'response' })
      .pipe(
        tap((response: HttpResponse<any>) => {
          this.toastr.success('Tarefa resgistrada com sucesso!');
        }),
        catchError((error) => {
          console.log(error);
          if (error.status >= 500)
            this.toastr.error('Resposta inesperada do servidor.');
          else
            this.toastr.error(error.error.menssagem);
          return throwError(() => new Error('Erro ao enviar requisição.'));
        })
      )
  }

  getAll() {
    return this.httpClient
      .get<any>(this.domain, { observe: 'response' })
      .pipe(
        catchError((error) => {
          if (error.status >= 500)
            this.toastr.error('Resposta inesperada do servidor.');
          return throwError(() => new Error('Erro ao enviar requisição.'));
        })
      )
  }

  getById(id: number) {
    return this.httpClient
      .get<any>(this.domain + "/" + id, { observe: 'response' })
      .pipe(
        catchError((error) => {
          if (error.status === 404)
            this.toastr.warning('Nenhuma tarefa localizada com esse ID');

          if (error.status >= 500)
            this.toastr.error('Resposta inesperada do servidor.');
          return throwError(() => new Error('Erro ao enviar requisição.'));
        })
      )
  }

  update(id: number, status: string) {
    const statusNew: StatusToJson = new StatusToJson();
    statusNew.status = status;
    return this.httpClient
      .put(this.domain + "/" + id, JSON.stringify(statusNew), {
        headers: new HttpHeaders({
          'Content-Type': 'application/json',
        }),
        observe: "response"
      })
      .pipe(
        tap((response) => {
          this.toastr.success('Tarefa editada com sucesso!');
        }),
        catchError((error) => {
          if (error.status === 404)
            this.toastr.warning('Nenhuma tarefa localizada com esse ID');

          if (error.status >= 500)
            this.toastr.error('Resposta inesperada do servidor.');
          return throwError(() => new Error('Erro ao enviar requisição.'));
        })
      )
  }

  delete(id: number) {
    return this.httpClient
      .delete(this.domain + "/" + id, { observe: "response" })
      .pipe(
        tap((response) => {
          this.toastr.success("Tarefa excluida");
        }),
        catchError((error) => {
          if (error.status === 404)
            this.toastr.warning(error.error.menssagem);

          if (error.status >= 500)
            this.toastr.error('Resposta inesperada do servidor.');
          return throwError(() => new Error('Erro ao enviar requisição.'));
        })
      )
  }


}

class StatusToJson {
  status?: string;
}
