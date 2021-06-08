package com.neoterux.tda.list.content;

public abstract class Node<E> {

    protected E content;
    protected Node<E> next;

    public Node<E> getNext() {
        return next;
    }

    public void setNext(Node<E> next) {
        this.next = next;
    }

    public E getContent() {
        return content;
    }

    public abstract Node<E> genNext(E element);

    public abstract Node<E> genPrevius(E element);
}
