package com.neoterux.tda.collection;

import java.util.function.BiConsumer;
import java.util.function.Consumer;

/**
 * Define métodos para poder recorrer una colleción de manera secuencial.
 *
 * @param <E> Tipo de dato que almacena la Colleción.
 */
public interface IterableCollection<E> {

    void forEach(Consumer<E> action);

    void indexForEach(BiConsumer<Integer, E> action);
}
