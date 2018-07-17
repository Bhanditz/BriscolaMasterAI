package com.gianlu.briscolamasterai;

import java.util.Collection;
import java.util.Objects;

/**
 * @author Gianlu
 */
public final class Utils {
    public static <E> boolean contains(E[] array, E match) {
        for (E element : array) {
            if (Objects.equals(element, match))
                return true;
        }

        return false;
    }

    public static <E> void remove(E[] array, E match) {
        for (int i = 0; i < array.length; i++) {
            if (Objects.equals(match, array[i]))
                array[i] = null;
        }
    }

    public static boolean isEmpty(Object[] array) {
        return countNotNull(array) == 0;
    }

    public static int countNotNull(Object[] array) {
        int count = 0;
        for (Object obj : array)
            if (obj != null) count++;
        return count;
    }

    public static <E> void push(E[] array, E element) {
        for (int i = 0; i < array.length; i++) {
            if (array[i] == null) {
                array[i] = element;
                return;
            }
        }
    }

    public static <E> int indexOf(E[] array, E element) {
        for (int i = 0; i < array.length; i++) {
            if (Objects.equals(element, array[i]))
                return i;
        }

        return -1;
    }

    public static <E> void dumpArrayIntoList(E[] array, Collection<E> list) {
        for (int i = 0; i < array.length; i++) {
            list.add(array[i]);
            array[i] = null;
        }
    }

    public static <E> void clear(E[] array) {
        for (int i = 0; i < array.length; i++)
            array[i] = null;
    }
}
