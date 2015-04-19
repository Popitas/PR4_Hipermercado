package hipermercado;

public class Contabilidad {

    private static double cash;

    public Contabilidad() {
        cash = 0;
    }

    public synchronized void addCash(double cash) {
        try {
            wait();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Contabilidad.cash += cash;
    }

    public double getCash() {
        try {
            wait();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return cash;
    }
}