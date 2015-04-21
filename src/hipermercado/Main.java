package hipermercado;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Calendar;
import java.util.Random;
import java.util.Scanner;

public class Main {
    private static Cliente[] clients;
    private static Caja[] checkouts;
    //DuendeAveria duendecillo;
    private static Cola<Cliente> queue = new Cola<Cliente>();
    private static final Timer supermarket = new Timer(60000, new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            queue.close();
            System.out.print("\n" + getTimeStamp() + ": ");
            System.out.println("Se ha cerrado la cola\n");
            supermarket.stop();
        }
    });
    private static Contabilidad accounting = new Contabilidad();

    public static void main(String[] args) {
        supermarket.start();
        askForValues();
        createClients();
        openCheckouts();
        queueClients();
    }

    private static void queueClients() {
        Random random = new Random();

        for (Cliente client : clients) {
            try {
                Thread.sleep(random.nextInt(5000));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            queue.add(client);
            printArrivalData(client);
        }
    }

    private static void printArrivalData(Cliente client) {
        System.out.println(getTimeStamp() + ": " + client.toString() + " llego a la cola.");
    }

    private static String getTimeStamp() {
        Calendar instance = Calendar.getInstance();
        return instance.get(Calendar.HOUR_OF_DAY) + ":" +
                instance.get(Calendar.MINUTE) + ":" +
                instance.get(Calendar.SECOND);
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
        //duendecillo = new DuendeAveria(checkouts);

        System.out.print("Numero de clientes: ");
        clients = new Cliente[in.nextInt()];
    }
}