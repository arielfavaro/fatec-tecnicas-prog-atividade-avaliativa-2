package siga;

/**
 * Estado do padrão State: uma fase do ciclo de vida de {@link Reserva}.
 *
 * Cada fase concreta responde às três operações dizendo se ela é permitida
 * e para qual fase a reserva vai.
 *
 * A operação proibida lança IllegalStateException
 */
public abstract class FaseReserva {

    /**
     * Nome que Reserva.getSituacao() devolve, faz parte do contrato.
     * Quem usa Reserva compara com "SOLICITADA", "EM_USO" e "ENCERRADA".
     */
    public abstract String getNome();

    public abstract FaseReserva retirar();

    public abstract FaseReserva devolver();

    public abstract FaseReserva cancelar();

    /**
     * Monta o erro de uma operação que esta fase não permite.
     */
    protected IllegalStateException operacaoProibida(String operacao) {
        return new IllegalStateException(
                "Nao e possivel " + operacao + " em " + getNome() + ".");
    }
}
