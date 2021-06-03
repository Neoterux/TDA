package com.neoterux.tda.list;

import java.util.Objects;

/**
 * Implementación de una LinkedList simple no circular.
 *
 * @param <E> tipo de dato que almacena la lista
 */
public class LinkedList<E> implements List<E>{

    /**
     * Primer objeto en la lista.
     */
    private NodeSimple<E> header;
    /**
     * Último objeto en la lista.
     */
    private NodeSimple<E> last;
    /**
     * Se utiliza para no tener que recorrer la lista en caso de querer obtener el tamaño.
     */
    private int effectiveSize = 0;

    /**
     * Crea un nuevo LinkedList vacío.
     */
    public LinkedList() { }


    /**
     * Añade un nuevo elemento al inicio de la lista tiene una dificultad de O(1).
     * @param e nuevo elemento a añadir.
     * @return true si se añadió con éxito, caso contrario false.
     */
    @Override
    public boolean addFirst(E e) {
        NodeSimple<E> nfirst = new NodeSimple<>(e);
        if(header != null)
            nfirst.setNext(header);
        else
            last = nfirst;

        header = nfirst;
        effectiveSize++;
        return true;
    }

    /**
     * Añade un nuevo elemento al  final de la lista, tiene una dificultad de O(1).
     *
     * @param e elemento a añadir.
     * @return true si se añadió con éxito.
     */
    @Override
    public boolean addLast(E e) {
        var nNode = new NodeSimple<>(e);
        if (header == null) {
            header = nNode;
        }else {
            last.setNext(nNode);
        }
        last = nNode;
        effectiveSize++;
    return true;
    }

    /**
     * Añade un elemento a un índice específico. En el peor caso tiene dificultad O(n)
     *
     * @param index indice a añadir
     * @param element elemento a añadir
     */
    @Override
    public void add(int index, E element) {
        Objects.checkIndex(index, effectiveSize);
        NodeSimple<E> nNode = new NodeSimple<>(element);
        if(index == 0) {
            nNode.setNext(header);
            header = nNode;
        }else {
            NodeSimple<E> tmp = getNodeAt(index-1);
            nNode.setNext(tmp.getNext());
            tmp.setNext(nNode);
        }
        effectiveSize++;
    }

    /**
     * Borra un nodo en la lista. En el peor caso su dificultad es de O(n).
     * @param index indice del elemento a añadir
     * @return Elemento a borrar de la lista.
     */
    @Override
    public E remove(int index) {
        Objects.checkIndex(index, effectiveSize);
        E removed;
        if(index == 0 && header != null) {
            removed = header.getContent();
            header = header.getNext();
        }else {
            NodeSimple<E> tmp = getNodeAt(index - 1);
            removed = tmp.getNext().getContent();
            tmp.setNext(null);
            if(index == effectiveSize -1)
                last = tmp;
        }
        effectiveSize--;
        return removed;
    }

    /**
     * Obtiene un objeto en el indice especificado. Tiene una dificultad en el peor de
     * los casos de O(n).
     *
     * @param index indice del objeto a tener.
     * @return El objeto en el índice especificado
     */
    @Override
    public E get(int index) {
        if (header == null)
            return null;
        if (index == 0){
            return header.getContent();
        }else if (index == effectiveSize -1){
            return last.getContent();
        }else {
            return getNodeAt(index).getContent();
        }
    }

    /**
     * Reemplaza y coloca un nuevo objeto en el indice especificado.
     *
     * @param index indice a reemplazar
     * @param element objeto a colocar
     * @return el objeto que se encontraba en el indice especificado.
     */
    @Override
    public E set(int index, E element) {
        Objects.checkIndex(index, effectiveSize);
        var nNode = new NodeSimple<>(element);
        E detached = null;
        if(index == 0){
            nNode.setNext(header.getNext());
            detached = header.getContent();
            header = nNode;
        }else if (index == effectiveSize -1){
            last.setNext(nNode);
            last = nNode;
        }else {
            var node = getNodeAt(index-1);
            detached = node.getNext().getContent();
            nNode.setNext(node.getNext().getNext());
            node.setNext(nNode);
        }
        return detached;
    }

    /**
     * @return El tamaño de la Lista.
     */
    @Override
    public int size() {
        return effectiveSize;
    }

    /**
     * @return true si la lista esta vacía.
     */
    @Override
    public boolean isEmpty() {
        return header == null && last == null;
    }

    /**
     * Borra todos los elementos de la lista
     */
    @Override
    public void clear() {
        // Si borramos la referencia al Header y al last
        // pasan a ser candidatos para el GC, debido a que los objetos de
        // tipo nodo son package-private. y nadie más tiene referencia a estos.
        header = null;
        last = null;
        // System.gc();
    }

    private NodeSimple<E> getNodeAt(int index) {
        Objects.checkIndex(index, effectiveSize);
        if(index == 0){
            return header;
        }else if (index == effectiveSize -1){
            return last;
        }
        NodeSimple<E> tmp = header;
        for (int i = 0; i < index; i++) {
            tmp = tmp.getNext();
        }
        return tmp;
    }

    /**
     * @return La representacion de la lista en formato [e1, e2, ..., en].
     */
    @Override
    public String toString() {
        StringBuilder str = new StringBuilder("[");
        var tmp = header;
        for (int i = 0; i < effectiveSize; i++) {
            str.append(tmp.getContent());
            if (i != effectiveSize -1)
                str.append(", ");
            tmp = tmp.getNext();
        }
        str.append("]");
        return str.toString();
    }
}
