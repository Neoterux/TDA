package com.neoterux.tda.list;

import java.util.Comparator;


public interface List<E> extends Iterable<E> {

    boolean addFirst(E e);

    boolean addLast(E e);

    void add(int index, E element);

    E remove(int index);

    E removeFirst();

    E removeLast();

    E get(int index);

    E set(int index, E element);

    int size();

    boolean isEmpty();

    void clear();

    List<E> findAll(E target);

    List<E> findAll(E target, Comparator<E> cmp);

    List<E> intersectionWith(List<E> target);

}
