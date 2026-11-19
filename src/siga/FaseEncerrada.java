package siga;

/**
 * Estado concreto do padrão State: a reserva terminou, por devolução ou por
 * cancelamento.
 *
 * É a fase final: recusa as três operações e não leva a nenhuma outra fase.
 */
public class FaseEncerrada extends FaseReserva {

    @Override
    public String getNome() {
        return "ENCERRADA";
    }

    @Override
    public FaseReserva retirar() {
        throw operacaoProibida("retirar");
    }

    @Override
    public FaseReserva devolver() {
        throw operacaoProibida("devolver");
    }

    @Override
    public FaseReserva cancelar() {
        throw operacaoProibida("cancelar");
    }
}
