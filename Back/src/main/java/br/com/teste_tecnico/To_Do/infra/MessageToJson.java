package br.com.teste_tecnico.To_Do.infra;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class MessageToJson {
    public static String build(String menssagem) {
        try {
            Retorno retorno = new Retorno(menssagem);
            return new ObjectMapper().writeValueAsString(retorno);
        } catch (JsonProcessingException e) {
            System.out.println(e);
            return null;
        }
    }
}

class Retorno {
    private String menssagem;

    public Retorno(String menssagem) {
        this.menssagem = menssagem;
    }

    public String getMenssagem() {
        return menssagem;
    }
}