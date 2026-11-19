package siga;

/**
 * Estado concreto do padrão State: o equipamento foi retirado e está com o
 * solicitante.
 *
 * Permite devolver, indo para ENCERRADA. Recusa retirar de novo e cancelar.
 */
public class FaseEmUso extends FaseReserva {

    @Override
    public String getNome() {
        return "EM_USO";
    }

    @Override
    public FaseReserva retirar() {
        throw operacaoProibida("retirar");
    }

    @Override
    public FaseReserva devolver() {
        return new FaseEncerrada();
    }

    @Override
    public FaseReserva cancelar() {
        throw operacaoProibida("cancelar");
    }
}
