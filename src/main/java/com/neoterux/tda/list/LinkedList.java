package com.neoterux.tda.list;

import java.util.Comparator;
import java.util.Iterator;

public class LinkedList<E> implements MutableList<E>{
    @Override
    public boolean addFirst(E e) {
        return false;
    }

    @Override
    public boolean addLast(E e) {
        return false;
    }

    @Override
    public void add(int index, E element) {

    }

    @Override
    public E remove(int index) {
        return null;
    }

    @Override
    public E removeFirst() {
        return null;
    }

    @Override
    public E removeLast() {
        return null;
    }

    @Override
    public E get(int index) {
        return null;
    }

    @Override
    public E set(int index, E element) {
        return null;
    }

    @Override
    public int size() {
        return 0;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public void clear() {

    }

    @Override
    public List<E> findAll(E target) {
        return null;
    }

    @Override
    public List<E> findAll(E target, Comparator<E> cmp) {
        return null;
    }

    @Override
    public List<E> intersectionWith(List<E> target) {
        return null;
    }

    @Override
    public void keepOnly(int from, int to) {

    }

    @Override
    public void detach(int from, int to) {

    }

    @Override
    public Iterator<E> iterator() {
        return null;
    }
}
