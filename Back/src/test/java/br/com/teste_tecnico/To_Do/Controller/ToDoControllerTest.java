package br.com.teste_tecnico.To_Do.Controller;


import br.com.teste_tecnico.To_Do.service.ToDoService;
import com.fasterxml.jackson.databind.ObjectMapper;
import br.com.teste_tecnico.To_Do.controller.ToDoController;
import br.com.teste_tecnico.To_Do.model.StatusDTO;
import br.com.teste_tecnico.To_Do.model.ToDo;
import br.com.teste_tecnico.To_Do.model.enums.Status;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.validation.BindingResult;
import br.com.teste_tecnico.To_Do.repository.ToDoRepository;

import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


public class ToDoControllerTest {

    private MockMvc mockMvc;

    @InjectMocks
    private ToDoController toDoController;

    @Mock
    ToDoService toDoService;
    @Mock
    ToDoRepository toDoRepository;

    @Mock
    private BindingResult bindingResult;

    private final ObjectMapper objectMapper = new ObjectMapper();

    private ToDo toDo;
    private final String URL = "/api/tarefas";

    @BeforeEach
    void configuracaoInicial() {
        MockitoAnnotations.openMocks(this);
        toDo = new ToDo();
        toDo.setId(1L);
        toDo.setTitulo("Teste");
        toDo.setDescricao("Descricao do teste");
        toDo.setStatus(Status.PENDENTE);

        mockMvc = MockMvcBuilders.standaloneSetup(toDoController).build();
    }


//    SALVAR TAREFA

//    ERROS: TITULO

    @Test
    @DisplayName("Falha ao salvar nova tarefa sem título")
    void falhaAoSalvarNovaTarefaSemTitulo() throws Exception {

        toDo.setTitulo(null);

        when(bindingResult.hasErrors()).thenReturn(true);

        mockMvc.perform(post(URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(toDo)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.menssagem").value("Título é obrigatório"));
    }

    @Test
    @DisplayName("Título - minimo de caracteres não atingido")
    void falhaAoSalvarNovaTarefaSemTituloMinimo() throws Exception {

        toDo.setTitulo("Oi");


        mockMvc.perform(post(URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(toDo)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.menssagem").value("Título mínimo de 3 caracteres"));
    }

    @Test
    @DisplayName("Título - máximo de caracteres ultrapassado")
    void falhaAoSalvarNovaTarefaTituloMaximoUltrapassado() throws Exception {

        toDo.setTitulo("Este título com certeza tem mais de 10 caracteres");


        mockMvc.perform(post(URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(toDo)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.menssagem").value("Título máximo de 10 caracteres"));
    }


//    ERROS: DESCRICAO

    @Test
    @DisplayName("Falha ao salvar nova tarefa sem Descrição")
    void falhaAoSalvarNovaTarefaSemDescricao() throws Exception {

        toDo.setDescricao(null);

        when(bindingResult.hasErrors()).thenReturn(true);

        mockMvc.perform(post(URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(toDo)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.menssagem").value("Descrição é obrigatória"));
    }

    @Test
    @DisplayName("Descricao - minimo de caracteres não atingido")
    void falhaAoSalvarNovaTarefaSemDescricaoMinima() throws Exception {

        toDo.setDescricao("hi");


        mockMvc.perform(post(URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(toDo)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.menssagem").value("Descrição mínima de 3 caracteres"));
    }

    @Test
    @DisplayName("Descricao - máximo de caracteres ultrapassado")
    void falhaAoSalvarNovaTarefaDescricaoMaximaUltrapassada() throws Exception {

        toDo.setDescricao("Esta Descrição com certeza tem mais de 20 caracteres");

        mockMvc.perform(post(URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(toDo)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.menssagem").value("Descrição máxima de 20 caracteres"));
    }

    //    ERRO: STATUS INVALIDO
    @Test
    @DisplayName("Erro ao salvar, status inválido")
    void erroAoSalvarUmaTarefaStatusInvalido() throws Exception {
        String json = "{\"titulo\": \"Comprar leite\", \"descricao\": \"Comprar leite no supermercado\", \"status\": \"INEXISTENTE\"}";

        when(toDoService.save(eq(toDo))).thenReturn(new ResponseEntity<>(HttpStatus.BAD_REQUEST));

        mockMvc.perform(post(URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isBadRequest());
    }

//    SUCESSO

    @Test
    @DisplayName("Sucesso ao salvar nova tarefa")
    void sucessoAoSalvarTarefa() throws Exception {
        when(toDoService.save(any(ToDo.class))).thenReturn(new ResponseEntity<>(HttpStatus.CREATED));

        mockMvc.perform(post(URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(toDo)))
                .andExpect(status().isCreated());
    }

    //      EDITAR TAREFA
//    ERRO: STATUS INVALIDO
    @Test
    @DisplayName("Erro ao editar, status inválido")
    void erroAoEditarStatusDeUmaTarefaStatusInvalido() throws Exception {
        StatusDTO statusDTO = new StatusDTO("INEXISTENTE");

        mockMvc.perform(put(URL + "/{id}", toDo.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(statusDTO)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.menssagem").value("Status inválido"));
    }


    //    ERRO: TAREFA INEXISTENTE
    @Test
    @DisplayName("Erro ao editar, tarefa inexistente")
    void erroAoEditarStatusDeUmaTarefaInexistente() throws Exception {
        StatusDTO statusDTO = new StatusDTO("EM_ANDAMENTO");
        when(toDoService.update(eq(toDo.getId()), eq(statusDTO.status()))).thenReturn(new ResponseEntity<>(HttpStatus.NOT_FOUND));

        mockMvc.perform(put(URL + "/{id}", toDo.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(statusDTO)))
                .andExpect(status().isNotFound());
    }

    //    SUCESSO
    @Test
    @DisplayName("Sucesso ao editar status de uma tarefa")
    void sucessoAoEditarStatusDeUmaTarefa() throws Exception {
        StatusDTO statusDTO = new StatusDTO("EM_ANDAMENTO");
        toDo.setStatus(Status.EM_ANDAMENTO);
        when(toDoService.update(eq(toDo.getId()), eq(statusDTO.status()))).thenReturn(new ResponseEntity<>(toDo, HttpStatus.OK));

        mockMvc.perform(put(URL + "/{id}", toDo.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(statusDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value(statusDTO.status()));
    }


//    EXIBIR TAREFAS

    //    LISTA VAZIA
    @Test
    @DisplayName("Exibir todas as tarefas - Lista Vazia")
    void exibirTodasAsTarefasListaVazia() throws Exception {

        List<ToDo> tarefasTest = List.of();

        when(toDoService.getAll()).thenReturn(ResponseEntity.ok(tarefasTest));

        mockMvc.perform(get(URL).contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk()).andExpect(jsonPath("$.length()").value(0));
    }


    //    SUCESSO
    @Test
    @DisplayName("Exibir todas as tarefas")
    void exibirTodasAsTarefas() throws Exception {

        List<ToDo> tarefasTest = List.of(new ToDo(1L, "Tarefa 1", "Fazer compras", Status.PENDENTE), new ToDo(2L, "Tarefa 2", "Buscar pet no petshop", Status.CONCLUIDA));

        when(toDoService.getAll()).thenReturn(ResponseEntity.ok(tarefasTest));

        mockMvc.perform(get(URL).contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk()).andExpect(jsonPath("$.length()").value(tarefasTest.size())).andExpect(jsonPath("$[0].id").value(tarefasTest.get(0).getId())).andExpect(jsonPath("$[0].titulo").value(tarefasTest.get(0).getTitulo())).andExpect(jsonPath("$[0].descricao").value(tarefasTest.get(0).getDescricao())).andExpect(jsonPath("$[0].status").value(tarefasTest.get(0).getStatus().toString()));
    }


//    EXIBIR TAREFA POR ID

//    ERRO TAREFA INEXISTENTE

    @Test
    @DisplayName("Erro ao Exibir tarefa por id - Tarefa inexistente")
    void erroAoExibirTarefaPorIdInexistente() throws Exception {

        Long id = 4L;
        when(toDoService.getOneById(eq(id))).thenReturn(new ResponseEntity<>(HttpStatus.NOT_FOUND));

        mockMvc.perform(get(URL + "/{id}", id)).andExpect(status().isNotFound());
    }

    //    SUCESSO
    @Test
    @DisplayName("Exibir tarefa por id")
    void exibirTarefaPorId() throws Exception {

        ToDo toDoById = new ToDo(4L, "Tarefa 4", "Cozinhar Feijão", Status.PENDENTE);

        when(toDoService.getOneById(eq(4L))).thenReturn(ResponseEntity.ok(toDoById));

        mockMvc.perform(get(URL + "/{id}", toDoById.getId())).andExpect(status().isOk()).andExpect(jsonPath("$.id").value(toDoById.getId())).andExpect(jsonPath("$.titulo").value(toDoById.getTitulo())).andExpect(jsonPath("$.descricao").value(toDoById.getDescricao())).andExpect(jsonPath("$.status").value(toDoById.getStatus().toString()));
    }


    //    EXCLUIR TAREFA

    //    ERRO: TAREFA INEXISTENTE
    @Test
    @DisplayName("Erro ao excluir tarefa - Inexistente")
    void erroAoExcluirTarefaInexistente() throws Exception {

        Long id = 5L;

        when(toDoService.delete(eq(5L))).thenReturn(new ResponseEntity<>("Tarefa não localizada", HttpStatus.NOT_FOUND));

        mockMvc.perform(delete(URL + "/{id}", id)).andExpect(status().isNotFound()).andExpect(content().string("Tarefa não localizada"));
    }

    //    SUCESSO
    @Test
    @DisplayName("Excluir tarefa")
    void excluirTarefa() throws Exception {

        Long id = 5L;

        when(toDoService.delete(eq(id))).thenReturn(ResponseEntity.ok("Tarefa excluida"));

        mockMvc.perform(delete(URL + "/{id}", id)).andExpect(status().isOk()).andExpect(content().string("Tarefa excluida"));
    }
}
