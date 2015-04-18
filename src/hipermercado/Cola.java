package hipermercado;

import java.util.LinkedList;

public class Cola<E> {

    private LinkedList<E> queue;

    public Cola() {
        queue = new LinkedList<E>();
    }

    public void add(E item) {
        queue.addLast(item);
    }

    public void addInFront(E item) {
        queue.addFirst(item);
    }

    public E advance() {
        return queue.poll();
    }

    public int size() {
        return queue.size();
    }
}