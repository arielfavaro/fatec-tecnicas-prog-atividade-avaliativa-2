package siga;

/**
 * Contexto do padrão State: guarda a fase atual do ciclo de vida e delega a
 * ela cada operação.
 *
 * Os métodos públicos só trocam a fase atual pela que ela devolve. As regras
 * de transição ficam nas classes de fase: {@link FaseSolicitada},
 * {@link FaseEmUso} e {@link FaseEncerrada}.
 */
public class Reserva {

    private final Usuario solicitante;
    private final Equipamento item;
    private FaseReserva fase;

    public Reserva(Usuario solicitante, Equipamento item) {
        this.solicitante = solicitante;
        this.item = item;
        this.fase = new FaseSolicitada();
    }

    public String getSituacao() {
        return fase.getNome();
    }

    public void retirar() {
        fase = fase.retirar();
    }

    public void devolver() {
        fase = fase.devolver();
    }

    public void cancelar() {
        fase = fase.cancelar();
    }

    public Usuario getSolicitante() {
        return solicitante;
    }

    public Equipamento getEquipamento() {
        return item;
    }
}
