package com.neoterux.tda.list;

/**
 * Representa una lista que permita reducirla a como desee el usuario,
 * mediante métodos como {@code keepOnly(int from, int to)}, permitiendo
 * alterar la lista con indices mayores a 0 y menores a el indice final.
 *
 * @param <E> Tipo de dato que almacena
 */
public interface MutableList<E> extends List<E> {

    void keepOnly(int from, int to);

    void detach(int from, int to);
}
