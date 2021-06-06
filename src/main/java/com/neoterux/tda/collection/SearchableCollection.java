package com.neoterux.tda.collection;

/**
 * Define la estructura de una colección de datos que pueda buscar objetos que contiene,
 * @param <E> Tipo de objeto que contiene.
 */
public interface SearchableCollection<E> {

//    public E findObject(int index);

    int findIndex(E object);

}
