package com.neoterux.tda.list;

import java.util.Comparator;
import java.util.Iterator;

/**
 * Generalización de la estructura de datos List.
 *
 * @param <E> tipo de dato que contiene la lista
 */
public interface List<E> extends Iterable<E> {

    /**
     * Añade un elemento al inicio de la lista
     * @param e elemento a añadir
     * @return true si se pudo añadir, false si no se pudo añadir
     */
    boolean addFirst(E e);

    /**
     * Añade un elemento al final de la lista.
     *
     * @param e elemento a añadir
     * @return true si se añadió con éxito, false si no se pudo añadir.
     */
    boolean addLast(E e);

    /**
     * Añade un elemento luego del índice especificado, desplazando a los items siguientes
     * en caso de ser necesario.
     *
     * @param index index a añadir elemento.
     * @param element elemento a añadir.
     * @throws IndexOutOfBoundsException si se intenta añadir un elemento fuera de rango.
     */
    void add(int index, E element);

    /**
     * Remueve el elemento que se encuentre en el indice especificado.
     *
     * @param index index a eliminar
     * @return el objeto que se removió, null si el indice esta fuera de rango.
     */
    E remove(int index);

    /**
     * Remueve el primer elemento de la lista.
     *
     * @return elemento que se encuentra al inicio, null si la lista está vacía.
     */
    E removeFirst();

    /**
     * Remueve el último elemento de la lista.
     *
     * @return elemento que se encuentra al final de la lista, null si la lista está vacía.
     */
    E removeLast();

    /**
     * Obtiene el elemento del indice especificado.
     *
     * @param index index del elemento a obtener.
     * @return el elemento que se encuentra en el index especificado.
     * @throws IndexOutOfBoundsException si el índice ingresado está fuera de rango
     */
    E get(int index);

    /**
     * Reemplaza el objeto que se encuentra en el index especificado, por el nuevo elemento
     * introducido.
     *
     * @param index index del elemento a añadir.
     * @param element elemento para reemplazar.
     * @return elemento que se encontraba en el índice especificado.
     * @throws IndexOutOfBoundsException si el índice está fuera de rango.
     * @throws IllegalArgumentException si el nuevo elemento es null.
     */
    E set(int index, E element);

    /**
     * @return la cantidad de elementos que contiene la lista.
     */
    int size();

    /**
     * @return true si esta vacía, false si contiene al menos 1 elemento.
     */
    boolean isEmpty();

    /**
     * Borra todos los elementos de la lista.
     */
    void clear();

    /**
     * Obtiene una nueva lista con los elementos que coincidan con el método {@link E#equals(Object)}.
     *
     * @param target elemento a comparar la lista.
     * @return lista con los elementos, lista vacía si no hay elementos.
     */
    List<E> findAll(E target);

    /**
     * Obtiene una nueva lista con los elementos que, de a cuerdo con el comparador, devuelvan 0.
     *
     * @param target elemento a comparar con el comparator.
     * @param cmp comparador para evaluar objetos.
     * @return lista con los elementos que hayan devuelto 0 en el comparador.
     */
    List<E> findAll(E target, Comparator<E> cmp);

    List<E> intersectionWith(List<E> target);

    /**
     * Obtiene el índice de un elemento en específico.
     * @param e elemento a buscar
     * @return el índice del elemento si se encuentra en la lista, -1 si no se encuentra.
     */
    default int indexOf(E e) {
        Iterator<E> it = iterator();
        int idx = -1;
        boolean found = false;
        while (it.hasNext()){
            idx++;
            found = it.next().equals(e);
            if(found){
                break;
            }
        }
        return (found) ? idx : -1;
    }

}
