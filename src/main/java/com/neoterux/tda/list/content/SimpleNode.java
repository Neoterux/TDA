package com.neoterux.tda.list.content;

import com.neoterux.tda.containers.Node;

public class SimpleNode<E> extends Node<E, SimpleNode<E>> {

    public SimpleNode(E element) {
        this(element, null);
    }
    public SimpleNode (E element, SimpleNode<E> next) {
        this.content = element;
        this.next = next;
    }

    @Override
    public SimpleNode<E> genNext(E element) {
        var nNode = new SimpleNode<>(element);
        if(this.next != null)
            nNode.next = this.next;
        this.next = nNode;
        return nNode;
    }

    @Override
    public void clean() {
        this.next = null;
    }

    @Override
    public SimpleNode<E> genPrevius(E element) {
        return new SimpleNode<>(element, this);
    }
}
