package hipermercado;

public class Caja extends Thread {
    private Cola<Cliente> queue;
    private Contabilidad accounting;

    public Caja(Cola<Cliente> queue, Contabilidad accounting) {
        this.queue = queue;
        this.accounting = accounting;
    }

    public void run() {
        Cliente cliente = queue.attend();

        if (cliente == null) return;

        try {
            sleep(getAttentionMillis(cliente));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private long getAttentionMillis(Cliente cliente) {
        return (long)cliente.damePrecioCarro()*10;
    }
}