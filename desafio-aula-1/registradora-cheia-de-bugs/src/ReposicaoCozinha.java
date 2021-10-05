public class ReposicaoCozinha {

    static void reporItem(String item) {
        if (Constantes.PAO.equals(item)) {
            ItensPorQuantidade.pao = ItensPorQuantidade.pao + Constantes.QTD_PADRAO_PAO;
        }
        if (Constantes.TORTA.equals(item)) {
            ItensPorQuantidade.torta = ItensPorQuantidade.torta + Constantes.QTD_PADRAO_TORTA;
        }
        if (Constantes.SANDUICHE_PRONTO.equals(item)) {
            ItensPorQuantidade.sanduiche = ItensPorQuantidade.sanduiche + Constantes.QTD_PADRAO_SANDUICHE_PRONTO;
        }
    }
}
