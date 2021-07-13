package com.neoterux.tda.stack;

import java.util.EmptyStackException;
import java.util.Iterator;

/**
 * <h2>Array Stack</h2>
 * Implementación de un Stack a base de arreglos fijos.
 *
 * @param <E> tipo de dato que almacena
 */
public class ArrayStack<E>  implements Stack<E>{

    private E[] elements;
    private int effectiveSize;

    /**
     * Crea un nuevo Stack con un tamaño fijo.
     * @param size cantidad máxima de elementos a almacenar.
     */
    public ArrayStack(int size) {
        this.elements = (E[]) new Object[size];
        effectiveSize = 0;
    }

    @Override
    public E pop() {
        if(isEmpty())
            throw new EmptyStackException();

        return elements[--effectiveSize];
    }

    @Override
    public void push(E element) {
        if (effectiveSize == capacity())
            throw new StackOverflowError();

        elements[effectiveSize++] = element;
    }

    @Override
    public boolean isEmpty() {
        return effectiveSize > 0;
    }

    @Override
    public int size() {
        return effectiveSize;
    }

    @Override
    public int capacity() {
        return elements.length;
    }

    @Override
    public E peek() {
        if (isEmpty())
            return null;
        return elements[effectiveSize-1];
    }

    @Override
    public Iterator<E> iterator() {
        return new Iterator<>() {
            private int pointer = 0;

            @Override
            public boolean hasNext() {
                return pointer < effectiveSize;
            }

            @Override
            public E next() {
                return elements[pointer++];
            }
        };
    }

}
