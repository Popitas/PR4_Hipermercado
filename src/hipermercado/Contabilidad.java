package hipermercado;

public class Contabilidad {

    private int cash;

    public Contabilidad() {
        cash = 0;
    }

    public synchronized void addCash(int cash) {
        try {
            wait();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        this.cash += cash;
    }

    public int getCash() {
        try {
            wait();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return cash;
    }
}