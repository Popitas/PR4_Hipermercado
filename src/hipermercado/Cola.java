package hipermercado;

import java.util.LinkedList;

public class Cola<E> {

    private LinkedList<E> queue;
    private QueueState state;
    private int maxSize;

    public Cola() {
        queue = new LinkedList<E>();
        state = QueueState.OPENED;
    }

    public synchronized void add(E item) {
        if (state == QueueState.CLOSED) {
            return;
        } else {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        queue.addLast(item);
        if (queue.size() > maxSize) maxSize++;

        notifyAll();
    }

    public synchronized void reQueue(E item) {
        try {
            wait();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        queue.addFirst(item);
        if (queue.size() > maxSize) maxSize++;

        notifyAll();
    }

    public synchronized E attend() {
        try {
            if (state == QueueState.CLOSED) return null;
            wait(10000);
        } catch (InterruptedException e) {
            return null;
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

    public int maxSize() {
        return maxSize;
    }
}