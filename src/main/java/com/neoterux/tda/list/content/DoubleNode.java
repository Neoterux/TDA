package com.neoterux.tda.list.content;

import com.neoterux.tda.containers.Node;

public class DoubleNode<E> extends Node<E, DoubleNode<E>> {
    private DoubleNode<E> previous;

    public DoubleNode(E element) { this(element, null); }

    public DoubleNode(E element, DoubleNode<E> previous) { this(element, previous, null); }

    public DoubleNode(E element, DoubleNode<E> previous, DoubleNode<E> next){
        this.content = element;
        this.next = next;
        this.previous = previous;
    }

    @Override
    public DoubleNode<E> genNext(E element) {
        var nNode = new DoubleNode<E>(element, this, this.next);

        if(this.next != null){
            this.next.setPrevious(nNode);
        }
        this.next = nNode;

        return nNode;
    }

    @Override
    public DoubleNode<E> genPrevius(E element) {
        var nNode = new DoubleNode<>(element, this.previous, this);
        if (this.previous != null)
            this.previous.setNext(nNode);
        setPrevious(nNode);
        return nNode;
    }

    public void setPrevious(DoubleNode<E> previous) {
        this.previous = previous;
    }

    public void replaceWith(E element){
        DoubleNode<E> nNode = new DoubleNode<>(element, this.previous, this.next);
        if(this.next != null)
            this.next.setPrevious(nNode);
        if(this.previous != null)
            this.previous.setNext(nNode);
        this.clean();
    }

    public void delete() {
        if(this.previous == this || this.next == this) {
            clean();
            return;
        }
        if(this.previous != null && this.next != null){
            this.previous.setNext(this.next);
            this.next.setPrevious(this.previous);
        }

        if(previous == null)
            this.next.setPrevious(null);
        else if(next == null)
            this.previous.setNext(null);
        // if any of the conditions are true, that means that this node is alone
    }

    @Override
    public void clean() {
        this.next = null;
        this.previous = null;
    }

    public void setNext(DoubleNode<E> next) {
        this.next = next;
    }

    public DoubleNode<E> getPrevious() {
        return previous;
    }
}
