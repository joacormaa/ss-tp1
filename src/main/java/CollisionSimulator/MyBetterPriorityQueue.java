package CollisionSimulator;

import java.util.PriorityQueue;
import java.util.Collection;
import java.util.Iterator;
import java.util.Queue;

public class MyBetterPriorityQueue<T> implements Queue<T> {
    private PriorityQueue<T> pq;

    public MyBetterPriorityQueue(){
        this.pq = new PriorityQueue<>();
    }

    public void heapify(){
        PriorityQueue aux = new PriorityQueue();
        for(T elem: this.pq){
            aux.offer(elem);
        }
        this.pq = aux;
    }

    @Override
    public int size() {
        return pq.size();
    }

    @Override
    public boolean isEmpty() {
        return pq.isEmpty();
    }

    @Override
    public boolean contains(Object o) {
        return pq.contains(o);
    }

    @Override
    public Iterator<T> iterator() {
        return pq.iterator();
    }

    @Override
    public Object[] toArray() {
        return pq.toArray();
    }

    @Override
    public <T1> T1[] toArray(T1[] a) {
        return pq.toArray(a);
    }

    @Override
    public boolean add(T t) {
        return pq.add(t);
    }

    @Override
    public boolean remove(Object o) {
        return pq.remove(o);
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        return pq.containsAll(c);
    }

    @Override
    public boolean addAll(Collection<? extends T> c) {
        return pq.addAll(c);
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        return pq.removeAll(c);
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        return pq.retainAll(c);
    }

    @Override
    public void clear() {
        pq.clear();
    }

    @Override
    public boolean offer(T t) {
        return pq.offer(t);
    }

    @Override
    public T remove() {
        return pq.remove();
    }

    @Override
    public T poll() {
        return pq.poll();
    }

    @Override
    public T element() {
        return pq.element();
    }

    @Override
    public T peek() {
        return pq.peek();
    }
}
