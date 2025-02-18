import { CommonModule } from '@angular/common';
import { HttpClient } from '@angular/common/http';
import { Component } from '@angular/core';
import { FormControl, FormGroup, FormsModule, ReactiveFormsModule, Validators } from '@angular/forms';
import { ToastrService } from 'ngx-toastr';
import { Tarefa } from '../../model/Tarefa';
import { CheckerService } from '../../services/checker/checker.service';
import { TodoServiceService } from '../../services/todo-service/todo-service.service';
interface ToDo {
  id: number;
  titulo: string;
  descricao: string;
  status: 'PENDENTE' | 'EM_ANDAMENTO' | 'CONCLUIDA';
}

@Component({
  selector: 'app-home-page',
  standalone: true,
  imports: [FormsModule, CommonModule, ReactiveFormsModule],
  templateUrl: './home-page.component.html',
  styleUrl: './home-page.component.css'
})
export class HomePageComponent extends TodoServiceService {

  formToDo: FormGroup;
  tarefas: ToDo[] = [];

  constructor(private checker: CheckerService, private ToastrService: ToastrService, private http: HttpClient) {
    super(http, ToastrService);
    this.formToDo = this.loadForm();
    this.loadTarefas();
  }

  adicionarTarefa() {
    if (!this.checker.checkerFormToDo(this.formToDo)) return;

    this.create("", this.getTarefa())
      .subscribe({
        next: (response) => {
          this.cleanForm();
          this.loadTarefas();
        }
      })
  }

  pesquisarTarefaId() {
    const id = this.formToDo.value.id;
    console.log(id);

    if (!id) {
      this.loadTarefas();
      return;
    }


    this.getById(id)
      .subscribe({
        next: (response) => {
          this.cleanForm();
          this.tarefas = [];
          this.tarefas.push(response.body);
        },
        error: () => {
          this.loadTarefas();
          this.cleanForm();
        }
      })
  }


  editarStatus(tarefa: ToDo) {
    const statusSelected = this.formToDo.value.status;
    this.update(tarefa.id, statusSelected).subscribe({
      next: () => {
        this.loadTarefas();
      }
    })

  }

  removerTarefa(id: number) {
    this.delete(id).subscribe({
      next: () => {
        this.loadTarefas();
      }
    });
  }

  private cleanForm() {
    this.formToDo.reset();
  }

  private getTarefa() {
    const form = this.formToDo.value;
    return new Tarefa(
      form.titulo,
      form.descricao,
      form.status
    );
  }

  private loadForm(): FormGroup {
    return new FormGroup({
      id: new FormControl(''),
      titulo: new FormControl('', [Validators.required, Validators.minLength(3), Validators.maxLength(10)]),
      descricao: new FormControl('', [Validators.required, Validators.minLength(3), Validators.maxLength(20)]),
      status: new FormControl('', [Validators.required])
    })
  }

  private loadTarefas(): void {
    this.getAll()
      .subscribe({
        next: (response): void => {
          this.tarefas = [];
          this.tarefas = response.body;
        }
      })
  }

  getStatusClass(status: string): string {
    switch (status) {
      case 'CONCLUIDA':
        return 'bg-success'; // Bootstrap para verde (status concluída)
      case 'EM_ANDAMENTO':
        return 'bg-warning'; // Bootstrap para amarelo (status em andamento)
      case 'PENDENTE':
        return 'bg-danger'; // Bootstrap para vermelho (status pendente)
      default:
        return '';
    }
  }
}
