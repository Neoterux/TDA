package com.neoterux.tda.list;

import com.neoterux.tda.list.content.SimpleNode;

import java.util.Comparator;
import java.util.Iterator;
import java.util.Objects;

/**
 * Simple Circular implementation of LinkedList.
 * @param <E> tipo de dato que almacena
 */
public class SCLinkedList<E> implements MutableList<E>{


    private SimpleNode<E> last;

    private int effectiveSize;

    public SCLinkedList() { effectiveSize = 0; }


    @Override
    public boolean addFirst(E e) {
        if (e == null)
            return false;
        if (last == null) {
            last = new SimpleNode<>(e);
            last.setNext(last);
        }else{
            var header = last.getNext();
            last.genNext(e);
            var current = last.getNext();
            current.setNext(header);
        }
        effectiveSize++;
        return true;
    }

    @Override
    public boolean addLast(E e) {
        if (e == null)
            return false;
        if (last == null)
            return addFirst(e);
        else {
            last = last.genNext(e);
        }
        effectiveSize++;
        return true;
    }

    @Override
    public void add(int index, E element) {
        if (element == null)
            return;
        Objects.checkIndex(index, effectiveSize);
        if (index == 0)
            addFirst(element);
        else if(index == effectiveSize -1)
            addLast(element);
        getNodeAt(index - 1).genNext(element);
        effectiveSize++;

    }

    @Override
    public E remove(int index) {
        if (index < 0)
            index = effectiveSize + index;
        Objects.checkIndex(index, effectiveSize);
        var node = getNodeAt(index - 1);
        System.out.println("index: " + index+ " node: " + node.getNext().getContent());
        E value = null;
        if(effectiveSize == 1)// this is to prevent that we can delete the last without keeping it
            last = null;
        else{
            var current = node.getNext();
            node.setNext(current.getNext());
            value = current.getContent();
            current.clean();
            if (index == effectiveSize - 1)
                last = node;
        }
        effectiveSize--;
        return value;
    }

    @Override
    public E removeFirst() {
        return remove(0);
    }

    @Override
    public E removeLast() {
        return remove(-1);
    }

    @Override
    public E get(int index) {
        if(index < 0)
            index = effectiveSize + index;
        Objects.checkIndex(index,effectiveSize);
        return getNodeAt(index).getContent();
    }

    @Override
    public E set(int index, E element) {
        if (index < 0)
            index += effectiveSize;
        Objects.checkIndex(index, effectiveSize);
        if (effectiveSize == 1) {
            var old = last;
            last = new SimpleNode<>(element);
            last.setNext(last); // keep cyclic
            return old.getContent();
        }

        SimpleNode<E> prev = getNodeAt(index - 1);
        var current = prev.getNext();
        var newNode = prev.genNext(element);
        newNode.setNext(current.getNext());
        return current.getContent();
    }

    /**
     * @return el tamaño de la lista
     */
    @Override
    public int size() {
        return effectiveSize;
    }

    /**
     * @return true si la lista no contiene elementos.
     */
    @Override
    public boolean isEmpty() {
        return last == null;
    }

    /**
     * Elimina los objetos de la lista.
     */
    @Override
    public void clear() {
        last = null;
    }

    @Override
    public Iterator<E> iterator() {
        return new Iterator<>(){
            private final SimpleNode<E> header = (last != null)? last.getNext() : null;

            private SimpleNode<E> pointer = header;
            private int idx = 0;

            @Override
            public boolean hasNext() {
                //FIXME: Handle to fix the idx if an item was removed from list before this get executed and index
                // is not the same for the current pointer.
                //if (idx >= effectiveSize + 1)// fix index if item removed and effective size decrease

                if (last == null) // this would delete all references created before clear method
                    pointer = null;
                return pointer != null && idx < effectiveSize;
            }

            @Override
            public E next() {
                if (idx >= effectiveSize)
                    // get null when the iterator finish, and doesnt return the last node item.
                    return null;

                var r = pointer;
                pointer = pointer.getNext();
                idx++;
                return r.getContent();
            }
        };
    }


    public List<E> findAll(E anotherElement) {
        List<E> result = new SCLinkedList<>();
        for(E element : this) {
            if (element.equals(anotherElement))
                result.addLast(element);
        }
        return result;
    }

    @Override
    public List<E> findAll(E target, Comparator<E> cmp) {
        if (cmp == null)
            throw new IllegalArgumentException("Comparator cannot be null");
        List<E> container = new SCLinkedList<>();
        for (E item : this) {
            if(cmp.compare(item, target) == 0)
                container.addLast(item);
        }
        return container;
    }

    /**
     * Encuentra los elementos que sean iguales entre ambas listas. Tiene dificultad de O(n^2). Se evalua como igual
     * a ambos objetos con el método {@code equals()}.
     *
     * @param targetList Lista con la que se va a comparar los elementos.
     * @return Lista con los elementos de la lista de donde fue invocado el método, y hayan cumplido el criterio del
     * método equals.
     */
    @Override
    public List<E> intersectionWith(List<E> targetList) {
        return intersectionWith(targetList, (it, t) -> it.equals(t)?0:1 );
    }

    /**
     * Busca los elementos que sean iguales entre ambas lista. Tiene una dificultad de O(n^2).
     * Para que un elemento sea considerado igual el comparador debe devolver 0.
     *
     * @param targetList lista con la que se van a comparar los elementos.
     * @param cmp comparador para evaluar los objetos de ambas listas.
     * @return Lista con los elementos de la lista de donde fue invocado, que hayan devuelto 0 mediante
     * el comparador.
     */
    public List<E> intersectionWith(List<E> targetList, Comparator<E> cmp){
        List<E> container = new SCLinkedList<>();
        for(E item : this) inner_loop: for(E target : targetList){
            if( cmp.compare(item, target) == 0) {
                container.addLast(item);
                // we dont need to continue the loop
                break inner_loop; // we can save cpu cycles with break;
            }
        }
        return container;
    }

    /**
     * Modifica los elementos de la lista y mantiene a los elementos que se encuentren dentro del rango [from, to]
     * inclusivo.
     *
     * @param from valor de partida del rango.
     * @param to valor de finalización del rango, si es más grande que el tamaño de la lista se toma como tope el último
     *           elemento dentro de esta.
     */
    @Override
    public void keepOnly(final int from, int to) {
        //FIXME
        checkRange(from, to);
        if (from == 0 && to == effectiveSize - 1)// the list keep inmutable
            return;

        SimpleNode<E> head = null, trail = null, tmp = last;
        to = Math.min(to, effectiveSize-1);
        int original_size = effectiveSize;
        for (int i = 0; i < original_size; i++) {
            tmp = tmp.getNext();
            if (i == from)
                head = tmp;
            else if (i == to)
                trail = tmp;
            if (i < from || i > to) {
                effectiveSize--;
            }
        }
        trail.setNext(head);
        last = trail;
    }

    /**
     * Modifica los elementos de la lista y elimina a los elementos que se encuentren dentro del rango [from, to]
     * inclusivo.
     *
     * @param from valor de partida del rango.
     * @param to valor de finalización del rango, si es más grande que el tamaño de la lista se toma como tope el último
     *           elemento dentro de esta.
     */
    @Override
    public void detach(final int from, int to) {
        checkRange(from, to);
        if (from == 0 && to >= effectiveSize) { // clear all list if this happen
            // this could handle error
            clear();
            return;
        }
        SimpleNode<E> head = null, trail = null, tmp = last, original_head = last.getNext();
        to = Math.min(to, effectiveSize-1);
        int original_size = effectiveSize;
        for (int i = 0; i < original_size; i++) {
            tmp = tmp.getNext();
            if (i == from - 1)
                head = tmp;
            else if (i == to + 1)
                trail = tmp;
            if (i >= from && i <= to) {
                effectiveSize--;
            }
        }
        // the next validations are needed because this is a circular implementation of linkedList
        if (to == original_size -1) {
            trail = original_head;
            last = head;

        } else if(from == 0)
            head = last;

        head.setNext(trail);
    }

    /**
     * Get a node of a specific index. Accepts negative index.
     *
     * @param index index of target node.
     * @return the node at the specified index
     */
    private SimpleNode<E> getNodeAt(int index){
        if (last == null)
            return null;
        if(index < 0)
            index += effectiveSize;

        SimpleNode<E> node= last;
        for (int i = 0; i <= index; i++) {
            node = node.getNext();
        }
        return node;
    }

    /**
     * Throw an exception if the range is invalid.
     *
     * @param from start index of the range, its inclusive.
     * @param to end index of the range, its inclusive.
     */
    private void checkRange(int from, int to){
        if (from < 0 || to < 0)
            throw new IllegalArgumentException("Only positive values are accepted");
        if (from > to)
            throw new IllegalArgumentException("From value most be lower than to");
        if (from > effectiveSize)
            throw new IllegalArgumentException("From value is out of bounds");
    }

    /**
     * @return Representación de la lista con un formato [a_1, a_2, ..., a_n]
     */
    @Override
    public String toString() {
        StringBuilder str = new StringBuilder("[");
        SimpleNode<E> tmp = last;
        for (int i = 0; i < effectiveSize; i++) {
            tmp = tmp.getNext();
            str.append(tmp.getContent());
            if(i < effectiveSize -1)
                str.append(", ");
        }
        str.append("]");
        return str.toString();
    }
}
