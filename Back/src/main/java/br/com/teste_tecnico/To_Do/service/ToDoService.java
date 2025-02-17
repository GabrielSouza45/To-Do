package br.com.teste_tecnico.To_Do.service;

import br.com.teste_tecnico.To_Do.infra.MessageToJson;
import br.com.teste_tecnico.To_Do.model.enums.Status;
import br.com.teste_tecnico.To_Do.repository.ToDoRepository;
import br.com.teste_tecnico.To_Do.model.ToDo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ToDoService {

    @Autowired
    private ToDoRepository repository;

    @Transactional()
    public ResponseEntity<?> save( ToDo toDo) {
        repository.save(toDo);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @Transactional(readOnly = true)
    public ResponseEntity<List<ToDo>> getAll() {
        List<ToDo> listaTarefas = repository.findAll();
        return new ResponseEntity<>(listaTarefas, HttpStatus.OK);
    }

    @Transactional(readOnly = true)
    public ResponseEntity<ToDo> getOneById(Long id) {
        ToDo toDo = getByID(id);
        if (toDo == null) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(toDo, HttpStatus.OK);
    }

    @Transactional()
    public ResponseEntity<ToDo> update(Long id, String status) {
        ToDo toDo = getByID(id);
        if (toDo == null) return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        toDo.setStatus(Status.valueOf(status));
        ToDo toDoSave = repository.save(toDo);

        return new ResponseEntity<>(toDoSave, HttpStatus.OK);
    }

    @Transactional()
    public ResponseEntity<String> delete(Long id) {
        ToDo toDo = getByID(id);
        if (toDo == null) return new ResponseEntity<>(MessageToJson.build("Tarefa não localizada"), HttpStatus.NOT_FOUND);

        repository.delete(toDo);
        return new ResponseEntity<>(MessageToJson.build("Tarefa excluida"), HttpStatus.OK);
    }

    @Transactional(readOnly = true)
    private ToDo getByID(Long id) {
        return repository.findById(id).orElse(null);
    }
}
