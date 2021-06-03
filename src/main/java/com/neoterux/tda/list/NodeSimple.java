package com.neoterux.tda.list;

class NodeSimple<E> {
    private E content;
    private NodeSimple<E> next;

    public NodeSimple(E element) {
        this(element, null);
    }


    public NodeSimple(E element, NodeSimple<E> next) {
        this.content = element;
        this.next = next;
    }

    public E getContent() {
        return content;
    }

    public NodeSimple<E> getNext() {
        return next;
    }

    public void setNext(NodeSimple<E> e){
        this.next = e;
    }

}
