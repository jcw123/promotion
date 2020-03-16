package com.im.sky.functional;

import java.util.Spliterator;
import java.util.function.Consumer;

public class TaggedArraySpliterator<T> implements Spliterator<T> {

    private final Object[] array;
      private int origin; // current index, advanced on split or traversal
      private final int fence; // one past the greatest index
 
    TaggedArraySpliterator(Object[] array, int origin, int fence) {
        this.array = array; this.origin = origin; this.fence = fence;
      }

      public void forEachRemaining(Consumer<? super T> action) {
        for (; origin < fence; origin += 2)
          action.accept((T) array[origin]);
      }
 
      public boolean tryAdvance(Consumer<? super T> action) {
        if (origin < fence) {
          action.accept((T) array[origin]);
          origin += 2;
          return true;
        }
        else // cannot advance
          return false;
      }
 
      public Spliterator<T> trySplit() {
        int lo = origin; // divide range in half
        int mid = ((lo + fence) >>> 1) & ~1; // force midpoint to be even
        if (lo < mid) { // split out left half
          origin = mid; // reset this Spliterator's origin
          return new TaggedArraySpliterator<>(array, lo, mid);
        }
        else       // too small to split
          return null;
      }

      public long estimateSize() {
        return (long)((fence - origin) / 2);
      }
 
      public int characteristics() {
        return ORDERED | SIZED | IMMUTABLE | SUBSIZED;
      }
}
