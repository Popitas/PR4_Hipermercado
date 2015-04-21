package hipermercado;

import java.util.Calendar;
import java.util.LinkedList;

public class Cola<E> {

    private LinkedList<E> queue;
    private State state;
    private int maxSize;

    public Cola() {
        queue = new LinkedList<E>();
        state = State.OPENED;
    }

    private static String getTimeStamp() {
        Calendar instance = Calendar.getInstance();
        return instance.get(Calendar.HOUR_OF_DAY) + ":" +
                instance.get(Calendar.MINUTE) + ":" +
                instance.get(Calendar.SECOND);
    }

    public synchronized void add(E item) {
        if (state == State.CLOSED) return;

        queue.addLast(item);

        if (queue.size() > maxSize) maxSize++;
    }

    public synchronized void reQueue(E item) {
        queue.addFirst(item);

        if (queue.size() > maxSize) maxSize++;
    }

    public synchronized E attend() {
        try {
            if (state == State.CLOSED) return null;
            wait(10000);
        } catch (InterruptedException e) {
            return null;
        }

        E firstElement = queue.poll();

        if (firstElement != null) {
            System.out.println(getTimeStamp() + ": " + firstElement + " salio de la cola.");
        }

        notifyAll();
        return firstElement;
    }

    public synchronized void close() {
        state = State.CLOSED;
    }

    public int maxSize() {
        return maxSize;
    }

    private enum State {
        OPENED, CLOSED
    }
}
