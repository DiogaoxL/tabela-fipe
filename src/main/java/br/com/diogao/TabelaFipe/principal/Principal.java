package br.com.diogao.TabelaFipe.principal;

import br.com.diogao.TabelaFipe.model.Dados;
import br.com.diogao.TabelaFipe.model.Modelos;
import br.com.diogao.TabelaFipe.service.ConsumoApi;
import br.com.diogao.TabelaFipe.service.ConverteDados;

import java.util.Comparator;
import java.util.Scanner;

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
    }
}
