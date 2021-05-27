package com.neoterux.tda.list;

public interface List<E> {

    public boolean addFirst(E e);

    public boolean addLast(E e);

    public void add(int index, E element);

    public E remove(int index);

    public E get(int index);

    public E set(int index, E element);

    public int size();

    public boolean isEmpty();

    public void clear();

    @Override
    public String toString();

}
