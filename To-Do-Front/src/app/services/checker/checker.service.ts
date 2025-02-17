import { Injectable } from '@angular/core';
import { FormGroup } from '@angular/forms';
import { ToastrService } from 'ngx-toastr';

@Injectable({
  providedIn: 'root'
})
export class CheckerService {

  constructor(private toastrService: ToastrService) { }

  checkerFormToDo(formGroup: FormGroup): boolean {
    let valido: boolean = true;
    const controls = formGroup.controls;

    if (controls['titulo']?.errors) {
      if (controls['titulo'].errors['required']) {
        this.toastrService.warning("Título é obrigatório");
      }
      if (controls['titulo'].errors['minlength']) {
        this.toastrService.warning("Título deve ter no mínimo 3 caracteres");
      }
      if (controls['titulo'].errors['maxlength']) {
        this.toastrService.warning("Título deve ter no máximo 10 caracteres");
      }

      valido = false;
    }

    if (controls['descricao']?.errors) {
      if (controls['descricao'].errors['required']) {
        this.toastrService.warning("Descrição é obrigatória");
      }
      if (controls['descricao'].errors['minlength']) {
        this.toastrService.warning("Descrição deve ter no mínimo 3 caracteres");
      }
      if (controls['descricao'].errors['maxlength']) {
        this.toastrService.warning("Descrição deve ter no máximo 20 caracteres");
      }

      valido = false;
    }

    if (controls['status']?.errors) {
      if (controls['status'].errors['required']) {
        this.toastrService.warning("Status é obrigatório");
      }
      valido = false;
    }

    return valido;
  }
}
