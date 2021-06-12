package com.neoterux.tda.containers;

public abstract class Node<E, N extends Node> {

    protected E content;
    protected N next;

    public N getNext() { return  next; }

    public void setNext(N next) {
        this.next = next;
    }

    public E getContent() {
        return content;
    }

    public abstract N genNext(E element);

    protected abstract N genPrevius(E element);

    public abstract void clean();
}
