package com.im.sky.datastructure.queue;

import com.sun.istack.internal.NotNull;

import javax.annotation.Nonnull;
import java.util.*;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

/**
 * @author jiangchangwei
 * @date 2020-5-12 下午 4:55
 **/
public class MultiPriorityQueue<T extends IndexSelect> implements BlockingQueue<T> {

    private int count;

    private List<BlockingQueue<T>> queues;

    private Random random;

    public MultiPriorityQueue(int capacity) {
        if(capacity <= 0) {
            throw new IllegalArgumentException("capacity must be larger than 0, capacity:" + capacity);
        }
        this.count = 1;
        queues = new ArrayList<>(count);
        queues.add(new ArrayBlockingQueue<>(capacity));
        random = new Random();
    }

    public MultiPriorityQueue(int count, int capacity) {
        if(count <= 0) {
            throw new RuntimeException("illegal parameter, count:" + count);
        }
        if(capacity <= 0) {
            throw new IllegalArgumentException("capacity must be larger than 0, capacity:" + capacity);
        }
        this.count = count;
        queues = new ArrayList<>(count);
        for(int i = 0; i < count; i++) {
            queues.add(new ArrayBlockingQueue<T>(capacity));
        }
        random = new Random();
    }

    @Override
    public boolean add(@Nonnull T t) {
        return queues.get(buildIndex(t)).add(t);
    }

    @Override
    public boolean offer(@Nonnull T t) {
        return queues.get(buildIndex(t)).offer(t);
    }

    @Override
    public void put(@Nonnull T t) throws InterruptedException {
        queues.get(buildIndex(t)).put(t);
    }

    @Override
    public boolean offer(T t, long timeout, @Nonnull TimeUnit unit) throws InterruptedException {
        return queues.get(buildIndex(t)).offer(t, timeout, unit);
    }

    @Override
    public @Nonnull T take() throws InterruptedException {
        int index = random.nextInt(count);
        return queues.get(index).take();
    }

    @Override
    public T poll(long timeout, TimeUnit unit) throws InterruptedException {
        int index = random.nextInt(count);
        return queues.get(index).poll(timeout, unit);
    }

    // not accurate
    @Override
    public int remainingCapacity() {
        return queues.stream().map(BlockingQueue::remainingCapacity).mapToInt(t -> t).sum();
    }

    @Override
    public boolean remove(Object o) {
        boolean ok = false;
        for(int i = 0; i < count; i++) {
            if(!ok) {
                ok = queues.get(i).remove(o);
            }
        }
        return ok;
    }

    @Override
    public boolean contains(Object o) {
        boolean ok = false;
        for(int i = 0; i < count; i++) {
            if(!ok) {
                ok = queues.get(i).contains(o);
            }
        }
        return ok;
    }

    @Override
    public int drainTo(@NotNull Collection<? super T> c) {
        int count = 0;
        for(int i = 0; i < this.count; i++) {
            count = queues.get(i).drainTo(c);
        }
        return count;
    }

    @Override
    public int drainTo(Collection<? super T> c, int maxElements) {
        int count = 0;
        for(int i = 0; i < this.count; i++) {
            count += queues.get(i).drainTo(c, maxElements - count);
        }
        return count;
    }

    @Override
    public T remove() {
        int index = random.nextInt(count);
        return queues.get(index).remove();
    }

    @Override
    public T poll() {
        int index = random.nextInt(count);
        return queues.get(index).poll();
    }

    @Override
    public T element() {
        int index = random.nextInt(count);
        return queues.get(index).element();
    }

    @Override
    public T peek() {
        int index = random.nextInt(count);
        return queues.get(index).peek();
    }

    @Override
    public int size() {
        int size = 0;
        for(BlockingQueue queue : queues) {
            size += queue.size();
        }
        return size;
    }

    @Override
    public boolean isEmpty() {
        for(BlockingQueue queue : queues) {
            if(!queue.isEmpty()) {
                return false;
            }
        }
        return true;
    }

    @Override
    public Iterator<T> iterator() {
        return new MultiIterator();
    }

    @Override
    public Object[] toArray() {
        List<Object> list = new LinkedList<>();
        for(int i = 0; i < count; i++) {
            list.addAll(queues.get(i));
        }
        return list.toArray();
    }

    @Override
    public <T1> T1[] toArray(T1[] a) {
        List<Object> list = new LinkedList<>();
        for(int i = 0; i < count; i++) {
            list.addAll(queues.get(i));
        }
        return list.toArray(a);
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        List<Object> list = new LinkedList<>();
        for(int i = 0; i < count; i++) {
            list.addAll(queues.get(i));
        }
        return list.containsAll(c);
    }

    @Override
    public boolean addAll(Collection<? extends T> c) {
        for(T e : c) {
            queues.get(buildIndex(e)).add(e);
        }
        return true;
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        for(BlockingQueue queue : queues) {
            queue.removeAll(c);
        }
        return true;
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        for(BlockingQueue queue : queues) {
            queue.retainAll(c);
        }
        return true;
    }

    @Override
    public void clear() {
        for(BlockingQueue queue : queues) {
            queue.clear();
        }
    }

    private int buildIndex(T e) {
        if(e == null) {
            return 0;
        }
        int index = e.index();
        if(index < 0) {
            return 0;
        }
        if(index >= count) {
            return count - 1;
        }
        return index;
    }

    private class MultiIterator implements Iterator<T> {

        List<Iterator<T>> list;

        private int index;

        MultiIterator() {
            list = new ArrayList<>(count);
            queues.forEach(t -> {
                list.add(t.iterator());
            });
        }

        @Override
        public boolean hasNext() {
           do {
               if(index >= count) {
                   return false;
               }
               if(list.get(index).hasNext()) {
                   return true;
               }
               index++;
           }while (index < count);
           return false;
        }

        @Override
        public T next() {
            if(index >= count) {
                return null;
            }
            return list.get(index).next();
        }
    }
}
