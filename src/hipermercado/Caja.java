package hipermercado;

import java.util.Calendar;

public class Caja extends Thread {
    private static Cola<Cliente> queue;
    private static Contabilidad accounting;
    private static int totalIDs = 0;
    private int id;
    private double cash;

    public Caja(Cola<Cliente> queue, Contabilidad accounting) {
        Caja.queue = queue;
        Caja.accounting = accounting;
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
        Calendar instance = Calendar.getInstance();
        System.out.println(client.dameNombre() + " terminó de ser atendido a las: " +
                instance.get(Calendar.HOUR_OF_DAY));
    }

    private void printAttentionTime(Cliente client) {
        Calendar instance = Calendar.getInstance();
        System.out.println(client.dameNombre() + " fue atendido a las: " +
                instance.get(Calendar.HOUR_OF_DAY));
    }

    private void close() {
        printCashAccountedTime();
        accounting.addCash(cash);
    }

    private void printCashAccountedTime() {
        Calendar instance = Calendar.getInstance();
        System.out.println("La caja " + id + " contabilizó " + cash + " a las " +
                instance.get(Calendar.HOUR_OF_DAY));
    }

    private long getAttentionMillis(Cliente cliente) {
        return (long)cliente.damePrecioCarro()*10;
    }
}