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
        DoubleNode<E> nNode = new DoubleNode<>(element, this, this.next);

        if(this.next != null){
            this.next.setPrevious(nNode);
        }
        this.next = nNode;

        return nNode;
    }

    /**
     * Genera un nuevo nodo en la posición siguiente a este, en caso de existir algun nodo consiguiente a este, el nuevo
     * nodo desplaza a ese nodo.
     *
     * @param element elemento que va a poseer el nuevo nodo.
     * @return nuevo nodo creado.
     */
    @Override
    public DoubleNode<E> genPrevius(E element) {
        var nNode = new DoubleNode<>(element, this.previous, this);
        if (this.previous != null)
            this.previous.setNext(nNode);
        setPrevious(nNode);
        return nNode;
    }

    /**
     * Registra / reemplaza un nodo como nodo anterior a este.
     *
     * @param previous nodo a colocar como previo.
     */
    public void setPrevious(DoubleNode<E> previous) {
        this.previous = previous;
    }

    /**
     * Reemplaza este nodo con uno nuevo, en caso de estar enlazado con algún otro nodo.
     *
     * @param element elemento con el que se va a reemplazar
     */
    public void replaceWith(E element){
        DoubleNode<E> nNode = new DoubleNode<>(element, this.previous, this.next);
        if(this.next != null)
            this.next.setPrevious(nNode);
        if(this.previous != null)
            this.previous.setNext(nNode);
        this.clean();
    }

    /**
     * Borra el nodo si se encuentra enlazado con algún otro.
     */
    public void delete() {
        if(this.previous == this || this.next == this) {
            clean();
            return;
        }

        if(previous != null)
            this.previous.setNext(this.next);
        if (next != null)
            this.next.setPrevious(this.previous);
        clean();
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
