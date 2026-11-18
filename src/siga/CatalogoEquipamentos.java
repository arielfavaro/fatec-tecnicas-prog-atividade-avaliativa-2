package siga;

import java.util.ArrayList;
import java.util.List;

/**
 * Cliente do padrao Adapter: lista os equipamentos disponiveis para reserva.
 *
 * Depende somente da abstração {FonteDeEquipamentos} e recebe os equipamentos
 * ja convertidos. Não conhece o formato do inventario patrimonial.
 */
public class CatalogoEquipamentos {

    private final FonteDeEquipamentos fonte;

    public CatalogoEquipamentos(FonteDeEquipamentos fonte) {
        this.fonte = fonte;
    }

    public List<Equipamento> listarDisponiveis() {
        List<Equipamento> disponiveis = new ArrayList<>();
        for (Equipamento item : fonte.listar()) {
            if (item.isDisponivel()) {
                disponiveis.add(item);
            }
        }
        return disponiveis;
    }
}
