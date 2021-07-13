package com.neoterux.tda.stack;

/**
 * <h2>Stack</h2>
 * Define una estructura base para el TDA Stack
 *
 * @param <E> tipo de elemento que va a almacenar
 */
public interface Stack<E> extends Iterable<E> {

    /**
     * Remueve el elemento que se encuentra en el tope del stack.
     * @return el elemento del tope
     * @throws java.util.EmptyStackException si no hay elementos en el Array
     */
    E pop();

    /**
     * Añade un nuevo elemento al stack
     *
     * @throws StackOverflowError si el stack no puede añadir más elementos.
     */
    void push(E element);

    /**
     * @return true si el stack no tiene ningún elemento.
     */
    boolean isEmpty();

    /**
     * @return la cantidad de elementos que contiene el stack
     */
    int size();

    /**
     * @return la capacidad total que puede almacenar el stack
     */
    int capacity();

    /**
     * @return el elemento que se encuentre en el tope del stack, en caso de estar vacío devuelve null.
     */
    E peek();

}
