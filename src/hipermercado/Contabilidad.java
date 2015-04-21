package hipermercado;

public class Contabilidad {

    private static double cash;

    public Contabilidad() {
        cash = 0;
    }

    public synchronized void addCash(double cash) {
        Contabilidad.cash += cash;
    }

    public synchronized double getCash() {
        return cash;
    }
}