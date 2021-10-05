public class RelacaoPesoPreco {

    public static double retornaPrecoProduto(String item, int qtd) {
        double precoTotal = 0;

        if (Constantes.PAO.equals(item)) {
            precoTotal = 12.75 * (qtd * 60 / 1000);
        }

        if (Constantes.TORTA.equals(item)) {
            precoTotal = 96.00 * qtd / 16;
        }

        if (Constantes.LEITE.equals(item)) {
            precoTotal = 4.48 * qtd;
        }

        if (Constantes.CAFE.equals(item)) {
            precoTotal = 9.56 * qtd;
        }

        if (Constantes.SANDUICHE_PRONTO.equals(item)) {
            precoTotal = 4.5 * qtd;
        }

        return precoTotal;
    }
}
