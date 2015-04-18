package hipermercado;

import java.util.LinkedList;

public class Cola<E> {

    private LinkedList<E> queue;
    private QueueState state;

    public Cola() {
        queue = new LinkedList<E>();
        state = QueueState.OPENED;
    }

    public synchronized void add(E item) {
        if (state == QueueState.CLOSED) return;

        while (state == QueueState.OPENED) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        queue.addLast(item);
        notifyAll();
    }

    public synchronized void reQueue(E item) {
        try {
            wait();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        queue.addFirst(item);
        notifyAll();
    }

    public synchronized E attend() {
        try {
            wait();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        E firstElement = queue.poll();
        notifyAll();
        return firstElement;
    }

    public synchronized void close() {
        try {
            wait();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        state = QueueState.CLOSED;
    }

    public int size() {
        return queue.size();
    }
}