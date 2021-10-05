
public class Registradora {

    public static void main(String[] args) {
//        primeiroBug();

        segundoBug();

//        terceiroBug();
//
//        quartoBug();
//
//        quintoBug();
//
//        sextoBug();
    }

    private static double registrarItem(String item, int quantidade) {
        double precoItem = RelacaoPesoPreco.retornaPrecoProduto(item, quantidade);

        if (QuantidadeMinimaItem.precisaReposicao(item)) {
            if (Constantes.PAO.equals(item) || Constantes.SANDUICHE_PRONTO.equals(item) || Constantes.TORTA.equals(item)) {
                if (!DataProjeto.cozinhaEmFuncionamento()) {
                    System.out.println("Cozinha fechada!");
                }
                ReposicaoCozinha.reporItem(item);
            }

            if (Constantes.LEITE.equals(item) || Constantes.CAFE.equals(item)) {
                ReposicaoFornecedor.reporItem(item);
            }
        }

        return precoItem;
    }

    private static void primeiroBug() {
        DataProjeto.criarDataComCozinhaFuncionando();
        String item = Constantes.SANDUICHE_PRONTO;
        int quantidade = 4;

        double precoTotal = registrarItem(item, quantidade);

        System.out.println(String.format("Valor total: %.2f", precoTotal));
    }

    private static void segundoBug() {
        DataProjeto.criarDataComCozinhaEncerradaMasComDiaUtil();
        String item = Constantes.TORTA;
        int quantidade = 10;

        double precoTotal = registrarItem(item, quantidade);

        System.out.println(String.format("Valor total: %.2f", precoTotal));
    }

    private static void terceiroBug() {
        DataProjeto.criarDataComCozinhaFuncionando();
        String item = Constantes.CAFE;
        int quantidade = 40;

        double precoTotal = registrarItem(item, quantidade);

        System.out.println(String.format("Valor total: %.2f", precoTotal));
    }

    private static void quartoBug() {
        DataProjeto.criarDataComCozinhaFuncionando();
        // Cliente 1
        String item = Constantes.SANDUICHE_PRONTO;
        int quantidade = 20;

        double precoTotal = registrarItem(item, quantidade);

        System.out.println(String.format("Valor total: %.2f", precoTotal));

        // Cliente 2
        String item2 = Constantes.SANDUICHE_PRONTO;
        int quantidade2 = 5;

        double precoTotal2 = registrarItem(item2, quantidade2);

        System.out.println(String.format("Valor total: %.2f", precoTotal2));
    }

    private static void quintoBug() {
        DataProjeto.criarDataComCozinhaFuncionando();
        String item = Constantes.PAO;
        int quantidade = 10;

        double precoTotal = registrarItem(item, quantidade);

        System.out.println(String.format("Valor total: %.2f", precoTotal));
    }

    private static void sextoBug() {
        DataProjeto.criarDataComCozinhaEncerradaSemDiaUtil();
        // Cliente 1
        String item = Constantes.SANDUICHE_PRONTO;
        int quantidade = 20;

        double precoTotal = registrarItem(item, quantidade);

        System.out.println(String.format("Valor total: %.2f", precoTotal));

        // Cliente 2
        String item2 = Constantes.SANDUICHE_PRONTO;
        int quantidade2 = 5;

        double precoTotal2 = registrarItem(item2, quantidade2);

        System.out.println(String.format("Valor total: %.2f", precoTotal2));
    }

}
