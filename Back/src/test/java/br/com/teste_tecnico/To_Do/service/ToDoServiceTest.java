package br.com.teste_tecnico.To_Do.service;

import br.com.teste_tecnico.To_Do.model.ToDo;
import br.com.teste_tecnico.To_Do.model.enums.Status;
import br.com.teste_tecnico.To_Do.repository.ToDoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ToDoServiceTest {

    @Mock
    private ToDoRepository toDoRepository;

    @InjectMocks
    private ToDoService toDoService;

    private ToDo toDo;

    @BeforeEach
    void configuracaoInicial() {
        MockitoAnnotations.openMocks(this);
        toDo = new ToDo();
        toDo.setTitulo("Titulo valido");
        toDo.setDescricao("Descricao valida");
        toDo.setStatus(Status.PENDENTE);
    }


//    SALVAR TAREFA
//    SUCESSO

    @Test
    @DisplayName("Sucesso ao salvar nova tarefa")
    void sucessoAoSalvarTarefa() throws Exception {
        when(toDoRepository.save(toDo)).thenReturn(toDo);

        ResponseEntity<?> response = toDoService.save(toDo);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());

        verify(toDoRepository, times(1)).save(toDo);

    }

    //      EDITAR TAREFA
    //    SUCESSO
    @Test
    @DisplayName("Sucesso ao editar status de uma tarefa")
    void sucessoAoEditarStatusDeUmaTarefa() throws Exception {
        String status = "CONCLUIDA";
        when(toDoRepository.findById(toDo.getId())).thenReturn(Optional.ofNullable(toDo));
        when(toDoRepository.save(toDo)).thenReturn(toDo);

        ResponseEntity<?> response = toDoService.update(toDo.getId(), status);
        ToDo toDoResponse = (ToDo) response.getBody();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(toDoResponse.getStatus().toString(), status);

        verify(toDoRepository, times(1)).findById(toDo.getId());
        verify(toDoRepository, times(1)).save(toDo);
    }


    //    EXIBIR TAREFAS
    //    SUCESSO
    @Test
    @DisplayName("Exibir todas as tarefas")
    void exibirTodasAsTarefas() throws Exception {

        List<ToDo> tarefasTest = List.of(new ToDo(1L, "Tarefa 1", "Fazer compras", Status.PENDENTE), new ToDo(2L, "Tarefa 2", "Buscar pet no petshop", Status.CONCLUIDA));

        when(toDoRepository.findAll()).thenReturn(tarefasTest);

        ResponseEntity<?> response = toDoService.getAll();
        List<ToDo> tarefas = (List<ToDo>) response.getBody();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(tarefas);
        assertEquals(tarefasTest.size(), tarefas.size());
        assertTrue(tarefas.containsAll(tarefasTest));

        verify(toDoRepository, times(1)).findAll();
    }


//    EXIBIR TAREFA POR ID

    //    SUCESSO
    @Test
    @DisplayName("Exibir tarefa por id")
    void exibirTarefaPorId() throws Exception {
        when(toDoRepository.findById(eq(toDo.getId()))).thenReturn(Optional.ofNullable(toDo));

        ResponseEntity<?> response = toDoService.getOneById(toDo.getId());
        ToDo tarefa = (ToDo) response.getBody();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(tarefa);
        assertEquals(tarefa, toDo);
    }


    //    EXCLUIR TAREFA


    //    SUCESSO
    @Test
    @DisplayName("Excluir tarefa")
    void excluirTarefa() throws Exception {
        when(toDoRepository.findById(toDo.getId())).thenReturn(Optional.ofNullable(toDo));
        doNothing().when(toDoRepository).delete(toDo);

        ResponseEntity<?> response = toDoService.delete(toDo.getId());

        assertEquals(response.getStatusCode(), HttpStatus.OK);

        verify(toDoRepository, times(1)).delete(toDo);
    }


}
