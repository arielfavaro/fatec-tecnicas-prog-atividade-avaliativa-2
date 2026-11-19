package siga;

/**
 * Estado concreto do padrão State: a reserva foi pedida e o equipamento ainda
 * não saiu do laboratório. É a fase em que toda reserva nasce.
 *
 * Permite retirar, indo para EM_USO, e cancelar, indo para ENCERRADA.
 * Recusa devolver.
 */
public class FaseSolicitada extends FaseReserva {

    @Override
    public String getNome() {
        return "SOLICITADA";
    }

    @Override
    public FaseReserva retirar() {
        return new FaseEmUso();
    }

    @Override
    public FaseReserva devolver() {
        throw operacaoProibida("devolver");
    }

    @Override
    public FaseReserva cancelar() {
        return new FaseEncerrada();
    }
}
