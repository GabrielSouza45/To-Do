package br.com.teste_tecnico.To_Do.model;

import br.com.teste_tecnico.To_Do.model.enums.Status;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Entity(name = "tarefas")
@Table(name = "tarefas")
public class ToDo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Título é obrigatório")
    @Size(min = 3, message = "Título mínimo de 3 caracteres")
    @Size(max = 10, message = "Título máximo de 10 caracteres")
    private String titulo;

    @NotBlank(message = "Descrição é obrigatória")
    @Size(min = 3, message = "Descrição mínima de 3 caracteres")
    @Size(max = 20, message = "Descrição máxima de 20 caracteres")
    private String descricao;

    @Enumerated(EnumType.STRING)
    private Status status;



    public ToDo() {
    }

    public ToDo(Long id, String titulo, String descricao, Status status) {
        this.id = id;
        this.titulo = titulo;
        this.descricao = descricao;
        this.status = status;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
}
