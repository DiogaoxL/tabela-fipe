package br.com.diogao.TabelaFipe.principal;

import br.com.diogao.TabelaFipe.service.ConsumoApi;

import java.util.Scanner;

public class Principal {
    private Scanner sc = new Scanner(System.in);

    private ConsumoApi consumoApi = new ConsumoApi();
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

        System.out.println(json);
    }
}
