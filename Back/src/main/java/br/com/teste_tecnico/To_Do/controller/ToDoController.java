package br.com.teste_tecnico.To_Do.controller;

import br.com.teste_tecnico.To_Do.infra.MessageToJson;
import jakarta.validation.Valid;
import br.com.teste_tecnico.To_Do.model.StatusDTO;
import br.com.teste_tecnico.To_Do.model.ToDo;
import br.com.teste_tecnico.To_Do.model.enums.Status;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import br.com.teste_tecnico.To_Do.service.ToDoService;

import java.util.Objects;


@RestController
@RequestMapping("api/tarefas")
@CrossOrigin(allowedHeaders = "*", origins = "*")
public class ToDoController {

    @Autowired
    private ToDoService toDoService;


    @GetMapping()
    @CrossOrigin(allowedHeaders = "*", origins = "*")
    public ResponseEntity<?> getAllToDo() {
        return toDoService.getAll();
    }

    @GetMapping("/{id}")
    @CrossOrigin(allowedHeaders = "*", origins = "*")
    public ResponseEntity<?> getOneToDo(@PathVariable("id") Long id) {
        return toDoService.getOneById(id);
    }

    @PostMapping()
    @CrossOrigin(allowedHeaders = "*", origins = "*")
    public ResponseEntity<?> createToDo(@Valid @RequestBody ToDo toDo, BindingResult result) {
        if (toDo.getStatus() == null)
            return new ResponseEntity<>(MessageToJson.build("Status inválido"), HttpStatus.BAD_REQUEST);

        if (result.hasErrors()) {
            String errorMessage = Objects.requireNonNull(result.getFieldError()).getDefaultMessage();
            System.out.println(errorMessage);
            return new ResponseEntity<>(MessageToJson.build(errorMessage), HttpStatus.BAD_REQUEST);
        }
        return toDoService.save(toDo);
    }

    @PutMapping("/{id}")
    @CrossOrigin(allowedHeaders = "*", origins = "*")
    public ResponseEntity<?> updateToDo(@PathVariable("id") Long id, @RequestBody StatusDTO status) {
        if (!validarStatus(status.status()))
            return new ResponseEntity<>(MessageToJson.build("Status inválido"), HttpStatus.BAD_REQUEST);
        return toDoService.update(id, status.status());
    }

    @DeleteMapping("/{id}")
    @CrossOrigin(allowedHeaders = "*", origins = "*")
    public ResponseEntity<?> deleteToDo(@PathVariable("id") Long id) {
        return toDoService.delete(id);
    }

    private boolean validarStatus(String statusRecebido) {
        try {
            Status status = Status.valueOf(statusRecebido.toUpperCase());
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }

}
