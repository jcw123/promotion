package com.im.sky.functional;

import java.util.Spliterator;

/**
 * @author jiangchangwei
 * @date 2020-1-8 上午 10:54
 **/
public class TaggedArray<T> {

    public static void main(String[] args) {
        TaggedArray<Integer> taggedArray = new TaggedArray<>(new Integer[]{1,2,3,4}, new String[]{"1","2","3","4"});
        Spliterator<Integer> spliterator = taggedArray.spliterator();
        spliterator.tryAdvance(System.out::println);
    }

    private final Object[] data;

    public TaggedArray(T[] data, Object[] target) {
        int size = data.length;
        if(target.length != size) throw new IllegalArgumentException();
        this.data = new Object[2 * size];
        for(int i =0, j=0; i < size; i++) {
            this.data[j++] = data[i];
            this.data[j++] = target[i];
        }
    }

    public Spliterator<T> spliterator() {
        return new TaggedArraySpliterator<>(data, 0, data.length);
    }
}
