package br.com.diogao.TabelaFipe.principal;

import br.com.diogao.TabelaFipe.model.Dados;
import br.com.diogao.TabelaFipe.model.Modelos;
import br.com.diogao.TabelaFipe.model.Veiculos;
import br.com.diogao.TabelaFipe.service.ConsumoApi;
import br.com.diogao.TabelaFipe.service.ConverteDados;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Principal {
    private Scanner sc = new Scanner(System.in);

    private ConsumoApi consumoApi = new ConsumoApi();
    private ConverteDados conversor = new ConverteDados();
    private String URL_BASE = "https://parallelum.com.br/fipe/api/v1/";
    public  void exibeMenu() {
        var menu = """
                --------- Opções -----------
                Carro
                Moto
                Caminhão
                Digite uma das opções para consultar:                
                """;
        System.out.println(menu);
        var opcao = sc.nextLine();

        String endereco;

        if (opcao.toLowerCase().contains("carr")){
            endereco = URL_BASE + "carros/marcas" ;
        } else if (opcao.toLowerCase().contains("mot")){
            endereco = URL_BASE + "motos/marcas" ;
        } else {
            endereco = URL_BASE + "caminhoes/marcas" ;
        }

        var json = consumoApi.obterDados(endereco);


        var marcas = conversor.obterLista(json, Dados.class);
        marcas.stream()
                .sorted(Comparator.comparing(Dados::codigo))
                .forEach(System.out::println);

        System.out.println("Qual o codigo da marca para consulta?");
        var codigoMarca = sc.nextLine();

        endereco = endereco + "/" + codigoMarca + "/modelos";
        json = consumoApi.obterDados(endereco);
        var modeloLista = conversor.obterDados(json, Modelos.class);

        System.out.println("Modelos dessa marca \n");
        modeloLista.modelos().stream()
                .sorted(Comparator.comparing(Dados::codigo))
                .forEach(System.out::println);

        System.out.println("\nDigite um trecho do carro a ser buscado: ");
        var nomeVeiculo = sc.nextLine();

        List<Dados> modelosFiltrados = modeloLista.modelos().stream()
                .filter(m -> m.nome().toLowerCase().contains(nomeVeiculo.toLowerCase()))
                .collect(Collectors.toList());

        System.out.println("Modelos filtrados  ");
        modelosFiltrados.forEach(System.out::println);

        System.out.println("Digite por favor o código do modelo para buscar os valores de avaliação: ");
        var codigoModelo = sc.nextLine();
        endereco = endereco + "/" + codigoModelo + "/anos";

        json = consumoApi.obterDados(endereco);
        List<Dados> anos = conversor.obterLista(json, Dados.class);
        List<Veiculos> veiculos = new ArrayList<>();

        for (int i = 0; i < anos.size(); i++) {
            var enderecoAnos = endereco + "/" + anos.get(i).codigo();
            json = consumoApi.obterDados(enderecoAnos);
            Veiculos veiculo = conversor.obterDados(json, Veiculos.class);
            veiculos.add(veiculo);
        }

        System.out.println("Todos os veiculos filtrados com avaliação por ano: ");
        veiculos.forEach(System.out::println);


    }
}
