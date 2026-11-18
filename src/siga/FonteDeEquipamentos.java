package siga;

import java.util.List;

/**
 * Alvo do padrao Adapter: a interface que o sistema espera para obter os
 * equipamentos do acervo.
 *
 * Os clientes só dependem desta abstração e recebem objetos
 * {@link Equipamento} prontos sem conhecer de onde os dados vem nem em que
 * formato chegam.
 */
public interface FonteDeEquipamentos {

    List<Equipamento> listar();
}
