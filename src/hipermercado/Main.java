package hipermercado;

import java.io.IOException;
import java.util.Calendar;
import java.util.Random;
import java.util.Scanner;

public class Main {
    private static Caja[] checkouts;
    private static Cliente[] clients;
    private static Cola<Cliente> queue = new Cola<Cliente>();
    private static Contabilidad accounting = new Contabilidad();

    public static void main(String[] args) throws IOException {
        askForValues();
        createClients();

        openCheckouts();
        queueClients();
    }

    private static void queueClients() {
        Random random = new Random();

        for (Cliente client : clients) {
            try {
                Thread.sleep(random.nextInt(5));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            queue.add(client);
            printArrivalData(client);
        }
    }

    private static void printArrivalData(Cliente client) {
        Calendar instance = Calendar.getInstance();
        System.out.println(client.dameNombre() + " llego a las " +
                instance.get(Calendar.HOUR_OF_DAY) + " con " +
                client.damePrecioCarro());
    }

    private static void openCheckouts() {
        for (int i = 0; i < checkouts.length; i++) {
            checkouts[i] = new Caja(queue, accounting);
            checkouts[i].start();
        }
    }

    private static void createClients() {
        for (int i = 0; i < clients.length; i++) {
            clients[i] = new Cliente();
        }
    }

    private static void askForValues() {
        Scanner in = new Scanner(System.in);
        System.out.print("Numero de cajas: ");
        checkouts = new Caja[in.nextInt()];

        System.out.print("Numero de clientes: ");
        clients = new Cliente[in.nextInt()];
    }
}
