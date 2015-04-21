package hipermercado;

import java.util.Calendar;

public class Caja extends Thread {
    private Cola<Cliente> queue;
    private Contabilidad accounting;
    private static int totalIDs = 0;
    private int id;
    private double cash;

    public Caja(Cola<Cliente> queue, Contabilidad accounting) {
        this.queue = queue;
        this.accounting = accounting;
        id = totalIDs++;
    }

    public void run() {
        Cliente client = queue.attend();
        while (client != null) {
            try {
                printAttentionTime(client);
                sleep(getAttentionMillis(client));
            } catch (InterruptedException e) {
                close();
                queue.reQueue(client);
            }
            cash += client.damePrecioCarro();
            printFinishedTime(client);
            client = queue.attend();
        }
        close();
    }

    private void printFinishedTime(Cliente client) {
        System.out.println(getTimeStamp() + ": " + client.dameNombre() +
                " termino de ser atendido en la caja " + id + ".");
    }

    private void printAttentionTime(Cliente client) {
        System.out.println(getTimeStamp() + ": " + client.dameNombre() +
                " comenzo a atenderse en la caja " + id + ".");
    }

    private void close() {
        printCashAccountedTime();
        accounting.addCash(cash);
    }

    private void printCashAccountedTime() {
        System.out.println(getTimeStamp() + ": " +
                "La caja " + id + " contabilizo " + cash + "€");
    }

    private long getAttentionMillis(Cliente cliente) {
        return (long)cliente.damePrecioCarro()*10;
    }

    private static String getTimeStamp() {
        Calendar instance = Calendar.getInstance();
        return instance.get(Calendar.HOUR_OF_DAY) + ":" +
                instance.get(Calendar.MINUTE) + ":" +
                instance.get(Calendar.SECOND);
    }
}