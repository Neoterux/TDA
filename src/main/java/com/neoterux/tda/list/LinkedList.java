package com.neoterux.tda.list;

import com.neoterux.tda.list.content.DoubleNode;

import java.util.Comparator;
import java.util.Iterator;
import java.util.Objects;

public class LinkedList<E> implements MutableList<E>{

    private int effectiveSize;
    private DoubleNode<E> last;

    /**
     * Crea una nueva lista Doblemente enlazada circular vacía.
     */
    public LinkedList() { effectiveSize = 0; }

    /**
     * Añade un nuevo objeto en la primera posición.
     *
     * @param e elemento a añadir.
     * @return true si se añadió con éxito.
     */
    @Override
    public boolean addFirst(E e) {
        if (e == null)
            return false;
        if(last == null)
            return addLast(e);
        DoubleNode<E> header = last.getNext();// header
        header.genPrevius(e);
        effectiveSize++;
        return true;
    }

    /**
     * Añade un nuevo objeto en la última posición.
     *
     * @param e elemento a añadir.
     * @return true si se añadió con exito.
     */
    @Override
    public boolean addLast(E e) {
        if(e == null)
            return false;
        if(effectiveSize == 0) {
            last = new DoubleNode<>(e);
            last.setNext(last);
            last.setPrevious(last);
        }else {
         last = last.genNext(e);
        }
        effectiveSize++;
        return true;
    }

    /**
     * Añade un nuevo ítem en en índice especificado.
     *
     * @param index indice a añadir.
     * @param element elemento a añadir.
     */
    @Override
    public void add(int index, E element) {
        if(element == null)
            return;
        Objects.checkIndex(index, effectiveSize);
        getNodeAt(index).genNext(element);
        effectiveSize++;

    }

    /**
     * Remueve un elemento dentro del índice especificado.
     *
     * @param index indice del elemento a eliminar.
     * @return elemento del índice especificado.
     */
    @Override
    public E remove(int index) {
        if(effectiveSize == 1) {
            E e = last.getContent();
            clear();
            return e;
        }
        DoubleNode<E> node = getNodeAt(index);
        if(index == effectiveSize -1 || index == -1){
            last = node.getPrevious();
        }
        node.delete();
        effectiveSize--;
        return node.getContent();
    }

    /**
     * Elimina el primer elemento de la lista.
     *
     * @return Elemento que se encontraba en el primer indice.
     */
    @Override
    public E removeFirst() {
        return remove(0);
    }

    /**
     * Elimina el último elemento de la lista.
     *
     * @return el antiguo último item de la lista.
     */
    @Override
    public E removeLast() {
        return remove(-1);
    }

    /**
     * Devuelve el ítem que se encuentra en una posición específica. Soporta índices negativos.
     *
     * @param index índice o posición donde se encuentra el objeto deseado
     * @return El objeto deseado
     * @throws IndexOutOfBoundsException si el index está fuera de límites, ya sea positivo o negativo.
     */
    @Override
    public E get(int index) {
        return getNodeAt(index).getContent();
    }

    /**
     * Reemplaza un ítem de la lista en el indice especificado por otro objeto.
     *
     * @param index indice a reemplazar
     * @param element elemento con el que se va a reemplazar.
     * @return antiguo elemento que se encontraba en el índice especificado.
     * @throws NullPointerException si el índice especificado está fuera de límites
     */
    @Override
    public E set(int index, E element) {
        if(index < 0)
            index += effectiveSize;
        if(element == null)
            return null;
        Objects.checkIndex(index, effectiveSize);
        DoubleNode<E> old = getNodeAt(index);
        old.replaceWith(element);
        return old.getContent();
    }

    /**
     * @return El número de elementos que contiene la lista
     */
    @Override
    public int size() {
        return effectiveSize;
    }

    /**
     * @return true si la lista no contine ningun elemento, false caso contrario.
     */
    @Override
    public boolean isEmpty() {
        return last == null;
    }

    /**
     * Elimina todos los elementos dentro de la lista.
     */
    @Override
    public void clear() {
        if(last == null)
            return;
        DoubleNode<E> pointer = last.getNext();
        for (int i = 0; i < effectiveSize; i++) {
            pointer.setPrevious(null); // clear all references.
        }
        last = null;
        effectiveSize = 0;
    }

    /**
     * Busca los elementos que sean iguales mediante la implementación del método {@code equals} dentro de los objetos
     * de tipo {@link E}.
     *
     * @param target Objeto a comparar todos los items dentro de la lista.
     * @return Lista con los ítems que cumplan con el método {@code equals}.
     */
    @Override
    public List<E> findAll(E target) {
        return findAll(target,(i,t) -> t.equals(i)? 0 : 1);
    }

    /**
     * Busca los elementos que sean iguales según el valor que retorne el comparador ingresado, se tomarán únicamente los
     * objetos de la lista que, según el comparador, retornen 0.
     *
     * @param target Objeto a comparar junto con el Objeto comparador especificado cada elemento de la lista.
     * @param cmp Comparador para determinar los objetos que sean iguales.
     * @return Lista con los items de la lista que cumplan la condicion del comparador.
     */
    @Override
    public List<E> findAll(E target, Comparator<E> cmp) {
        if(cmp == null)
            throw new IllegalArgumentException("Comparator cannot be null");
        List<E> container = new LinkedList<>();
        if(target == null)
            return container;

        for(E item : this){
            if(cmp.compare(item, target) == 0)
                container.addLast(item);
        }

        return container;
    }

    /**
     * Compara los elementos de la lista donde fue invocado, con los elementos de {@literal target} que sean iguales
     * de acuerdo a la implementación del método {@code equals} de los objetos de tipo {@link E}.
     *
     * @param target Lista con elementos a comparar.
     * @return Lista con items de donde fue invocado que hayan cumplido las condiciones del equals {@see E#equals}.
     */
    @Override
    public List<E> intersectionWith(List<E> target) {
        return intersectionWith(target, (i, t)-> i.equals(t)? 0 : 1 );
    }


    /**
     * Compara los elementos de la lista donde fue invocado, con los elementos de {@literal target} que sean iguales
     * de acuerdo a la implementacion del comparador. Se asume como iguales si el comparador devuelve 0.
     *
     * @param targetList Lista con elementos a comparar.
     * @param cmp Comparador que dicta que elementos son iguales (return 0).
     * @return Lista con items de donde fue invocado que hayan cumplido las condiciones del equals {@see E#equals}.
     */
    public List<E> intersectionWith(List<E> targetList, Comparator<E> cmp){
        if(cmp == null)
            throw new IllegalArgumentException("Comparator cannot be null");
        List<E> container = new LinkedList<>();
        if(targetList == null || targetList.isEmpty())
            return container;
        for(E item : this) for(E target : targetList) {
            if (cmp.compare(item, target) == 0) { // the intersection is 1->1, so we can break the loop.
                container.addLast(item);
                break;
            }
        }
        return container;
    }

    /**
     * Borra todos los elementos que no estén dentro del rango especificado.
     *
     * @param from index desde donde mantener elementos.
     * @param to index hasta donde mantener elementos. Se define como el min{to, last_index}.
     */
    @Override
    public void keepOnly(final int from, int to) {
        checkRange(from, to);
        to = Math.min(to, effectiveSize-1);
        if(from == 0 && to == effectiveSize -1)
            return;

        DoubleNode<E> sNode = getNodeAt(from);
        DoubleNode<E> tNode = sNode;
        int nSize = 1;
        for (int i = 0; i < (to - from); i++) {
            tNode = tNode.getNext();
            nSize++;
        }
        sNode.setPrevious(tNode);
        tNode.setNext(sNode);
        if(to == effectiveSize - 1)
            last = tNode;

        effectiveSize = nSize;
    }

    /**
     * Elimina los ítems que se encuentren en el rango ingresado.
     *
     * @param from index desde donde se va a eliminar.
     * @param to index hasta donde se va a eliminar, si es más grande que el último index se asume que to = last_index.
     */
    @Override
    public void detach(final int from, int to) {
        checkRange(from, to);
        if(from == 0 && to == effectiveSize -1) {
            clear();
            return;
        }
        to = Math.min(effectiveSize-1, to);
        int original = effectiveSize;
        DoubleNode<E> head = getNodeAt(from -1);
        DoubleNode<E> trail = head.getNext();
        int n_moves = to - from +1;
        for (int i = 0; i < n_moves ; i++) {
            trail = trail.getNext();
            trail.getPrevious().delete();
        }

        effectiveSize -= n_moves;
        System.out.println("size: " + effectiveSize + " moves: " + n_moves);
        head.setNext(trail);
        trail.setPrevious(head);
        if (to == original - 1)
            last = head;

    }

    /**
     * @return un Objeto iterador.
     */
    @Override
    public Iterator<E> iterator() {

        return new Iterator<>() {
            private DoubleNode<E> head = (last == null)? null : last.getNext();
            private DoubleNode<E> pointer = head;
            private int idx = 0;
            @Override
            public boolean hasNext() {
                if(last == null)
                    pointer = null;
                return pointer != null && idx < effectiveSize;
            }

            @Override
            public E next() {
                if (idx >= effectiveSize || pointer == null)
                    return null;
                E out = pointer.getContent();
                pointer = pointer.getNext();
                idx++;
                return out;
            }
        };
    }

    /**
     * Obtiene un nodo en un punto específico, tiene dificultad O(n/2).
     *
     * @param idx index del nodo objetivo
     * @return Nodo objetivo.
     */
    private DoubleNode<E> getNodeAt(int idx){
        if (idx >= effectiveSize || (idx + effectiveSize) < 0)
            throw new IndexOutOfBoundsException("index not valid");
        DoubleNode<E> target = last;
        int mid = Math.floorDiv(effectiveSize, 2);
        boolean reverse = (idx > mid) || (idx + effectiveSize > mid && idx < 0);
        int n_moves = Math.abs(idx + 1);
        if(idx > 0 && reverse)
            n_moves = n_moves - mid;
        for (int i = 0; i < n_moves; i++) {
            if(reverse)
                target = target.getPrevious();
            else
                target = target.getNext();
        }
        return target;
    }

    private void checkRange(int from, int to){
        if(from < 0 || to < 0)
            throw new IndexOutOfBoundsException("Values cannot be negative");
        if (from > to)
            throw new IllegalArgumentException("From cannot be greater than to");
        if (from >= effectiveSize)
            throw new IllegalArgumentException("From value is out of bounds");
    }

    /**
     * @return una representación de la lista en el formato [a_1, a_2, ..., a_n]
     */
    @Override
    public String toString() {
        StringBuilder str = new StringBuilder("[");
        DoubleNode<E> tmp = last;
        for (int i = 0; i < effectiveSize; i++) {
            tmp = tmp.getNext();
            str.append(tmp.getContent());
            if(i < effectiveSize - 1)
                str.append(", ");
        }
        str.append("]");
        return str.toString();
    }

    /**
     * Re posiciona el puntero del último elemento n-cantidad de posiciones.
     * Al final de la operación la lista coloca como último elemento aquel que
     * se encuentre en el puntero.
     *
     * @param times n-cantidad de rotaciones, si es negativo se rota desde el último elemento
     *              hacia atrás.
     */
    public void rotate(int times) {
        if(times == 0 || size() == 0){
            return; // do nothing when times is 0 or the list is empty
        }
        boolean reverse = times < 0;
        // this will reduce the numbers of iterations to the size of the list
        times = Math.abs(times) % size();

        while (times > 0){
            if (reverse){
                this.last = this.last.getPrevious();
            }else
                this.last = this.last.getNext();
            times--;
        }
    }
}
