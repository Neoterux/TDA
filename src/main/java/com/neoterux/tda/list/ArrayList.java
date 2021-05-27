package com.neoterux.tda.list;

import java.util.Objects;

/**
 * Implementación estática del TDA List
 *
 * @param <E> tipo de dato que va a almacenar el ArrayList
 */
public class ArrayList<E> implements List<E> {

    /**
     * Array que contiene los elementos del ArrayList.
     */
    private E[] elements;

    /**
     * Capacidad del arreglo de elementos
     */
    private int capacity = 10;

    /**
     * Cantidad real de elementos contenidos en el ArrayList.
     */
    private int effectiveSize = 0;

    /**
     * Crea un nuevo ArrayList con un tamaño inicial de 10
     */
    public ArrayList() {
        this(10);
    }

    /**
     * Crea un ArrayList con un tamaño incial determinado.
     * @param size capacidad inicial del ArrayList, debe ser mayor a 0.
     */
    public ArrayList(int size) {
        if (size <= 0)
            throw new IllegalArgumentException("Illegal size for an array");
        capacity = size;
        elements = (E[]) (new Object[size]);
    }

    @Override
    public boolean addFirst(E e) {

        if (effectiveSize == capacity) {
            addCapacity();
        }
        return false;
    }

    @Override
    public boolean addLast(E e) {
        return false;
    }

    /**
     * Añade un elemento de tipo E, en un índice específico,
     * desplaza una posición a los elementos contiguos.
     *
     * @param index indice a insertar
     * @param element Elemento a añádir
     */
    @Override
    public void add(int index, E element) {
        if (index < 0 || index >= effectiveSize) {
            throw new IndexOutOfBoundsException("Index not valid");
        }
        if (effectiveSize == capacity) {
            addCapacity();
        }

        for (int i = effectiveSize - 1; i >= index; i--) {
            elements[i + 1] = elements[i];
        }
        elements[index] = element;
        effectiveSize++;
    }

    @Override
    public E remove(int index) {
        return null;
    }

    /**
     * Obtiene un elemento en un índice específico
     *
     * @param index indice del elemento a buscar
     * @return elemento
     */
    @Override
    public E get(int index) {
        if (index < 0 || index >= effectiveSize) {
            throw new IndexOutOfBoundsException("invalid index");
        }
        return elements[index];
    }

    @Override
    public E set(int index, E element) {
        Objects.checkIndex(index, effectiveSize);
        E detachObject = elements[index];
        elements[index] = element;
        return detachObject;
    }

    @Override
    public int size() {
        return effectiveSize;
    }

    @Override
    public boolean isEmpty() {
        return effectiveSize == 0;
    }

    @Override
    public void clear() {

    }

    @Override
    public String toString() {
        StringBuilder representation = new StringBuilder("[");
        for(E element : elements) {
            representation.append(element.toString()).append(", ");
        }
        representation.append("]");
        return representation.toString();
    }

    private void addCapacity() {

    }
}
