package br.com.diogao.TabelaFipe.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ConverteDados implements IConverteDados{

    private ObjectMapper mapper = new ObjectMapper(); // mapeando o objeto
    @Override
    public <T> T obterDados(String json, Class<T> classe) {
        try{
            return mapper.readValue(json, classe); // carregando os valores json e enviando pra alguma classe
        } catch (JsonProcessingException e ) {
            throw new RuntimeException(e);
        }
    }
}
