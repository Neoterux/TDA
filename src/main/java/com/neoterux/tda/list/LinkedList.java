package com.neoterux.tda.list;

public class LinkedList<E> implements List<E>{

    private Node<E> first;

    private Node<E> last;

    private int effectiveSize;

    public LinkedList() { effectiveSize = 0; }

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


    private static class Node<E> {
        Node<E> previus;
        E content;
        Node<E> next;

        Node(E element) {
            this(element, null, null);
        }
        Node(E element, Node<E> previus) {
            this(element, previus, null);
        }
        Node(E element, Node<E> previus, Node<E> next){
            this.content = element;
            this.previus = previus;
            this.next = next;
        }

        public void setNext(Node<E> next) {
            this.next = next;
        }

        public void setPrevius(Node<E> previus) {
            this.previus = previus;
        }

        public Node<E> getNext() {
            return next;
        }

        public E getContent() {
            return content;
        }

        public Node<E> getPrevius() {
            return previus;
        }

        public Node<E> genNext(final E element) {
            return new Node<>(element, this);
        }

        public Node<E> genPrevius(final E element) {
            Node<E> nNode = new Node<>(element, this.previus, this);
            var prev = nNode.previus;
            if(prev != null){
                // clear previus node reference to this, and replace with new node
                prev.next = nNode;
            }
            // now the previus node of this node is the new node
            this.previus = nNode;
            return nNode;
        }
        public void clearReferences(){
            this.next = null;
            this.previus = null;
        }
    }
}
