package siga;

import java.util.List;

/**
 * Cliente do padrao Adapter: mostra a situação de todos
 * os equipamentos do acervo.
 *
 * Depende somente da abstração {@link FonteDeEquipamentos}, a mesma usada pelo
 * {@link CatalogoEquipamentos}.
 */
public class RelatorioAcervo {

    private final FonteDeEquipamentos fonte;

    public RelatorioAcervo(FonteDeEquipamentos fonte) {
        this.fonte = fonte;
    }

    public List<Equipamento> listarTodos() {
        return fonte.listar();
    }

    public void imprimir() {
        System.out.println("   Situacao segundo o relatorio:");
        for (Equipamento item : listarTodos()) {
            System.out.println("     " + item + " - "
                    + (item.isDisponivel() ? "disponivel" : "baixado"));
        }
    }
}
