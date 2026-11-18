package siga;

import java.util.ArrayList;
import java.util.List;

/**
 * Adaptador do padrão Adapter: implementa {@link FonteDeEquipamentos} usando o
 * componente externo {@link InventarioLegadoWS}, recebido por composição.
 *
 * E o unico lugar do sistema que conhece o formato da matriz do inventário
 * patrimonial: coluna 0 e o código, 1 e a descrição e 2 e a situação em letra.
 */
public class AdaptadorInventarioLegado implements FonteDeEquipamentos {

    private static final int COLUNA_CODIGO = 0;
    private static final int COLUNA_DESCRICAO = 1;
    private static final int COLUNA_SITUACAO = 2;
    private static final String SITUACAO_DISPONIVEL = "D";

    private final InventarioLegadoWS servicoLegado;

    public AdaptadorInventarioLegado(InventarioLegadoWS servicoLegado) {
        this.servicoLegado = servicoLegado;
    }

    @Override
    public List<Equipamento> listar() {
        List<Equipamento> equipamentos = new ArrayList<>();

        for (String[] linha : servicoLegado.consultarEquipamentos()) {
            equipamentos.add(converter(linha));
        }

        return equipamentos;
    }

    private Equipamento converter(String[] linha) {
        Equipamento item = new Equipamento(
            linha[COLUNA_CODIGO],
            linha[COLUNA_DESCRICAO]
        );

        item.setDisponivel(
            estaDisponivel(linha[COLUNA_SITUACAO])
        );

        return item;
    }

    /**
     * Regra da situacao, aplicada uma vez so: o legado as vezes manda a letra
     * em minuscula, entao "D" e "d" valem como disponivel.
     */
    private boolean estaDisponivel(String situacao) {
        return SITUACAO_DISPONIVEL.equalsIgnoreCase(situacao);
    }
}
