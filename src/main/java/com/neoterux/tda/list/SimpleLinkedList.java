package com.neoterux.tda.list;

import com.neoterux.tda.list.content.SimpleNode;

import java.util.Comparator;
import java.util.Iterator;
import java.util.Objects;

/**
 * Implementación de una LinkedList simple no circular.
 *
 * @param <E> tipo de dato que almacena la lista
 */
public class SimpleLinkedList<E> implements MutableList<E>{

    /**
     * Primer objeto en la lista.
     */
    private SimpleNode<E> header;
    /**
     * Último objeto en la lista.
     */
    private SimpleNode<E> last;
    /**
     * Se utiliza para no tener que recorrer la lista en caso de querer obtener el tamaño.
     */
    private int effectiveSize = 0;

    /**
     * Crea un nuevo LinkedList vacío.
     */
    public SimpleLinkedList() { }


    /**
     * Añade un nuevo elemento al inicio de la lista tiene una dificultad de O(1).
     * @param e nuevo elemento a añadir.
     * @return true si se añadió con éxito, caso contrario false.
     */
    @Override
    public boolean addFirst(E e) {
        if (e == null)
            return false;
        SimpleNode<E> nfirst = new SimpleNode<>(e);
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
        if (e == null)
            return false;
        var nNode = new SimpleNode<>(e);
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
        if(element == null)
            return;
        Objects.checkIndex(index, effectiveSize);
        SimpleNode<E> nNode = new SimpleNode<>(element);
        if(index == 0) {
            nNode.setNext(header);
            header = nNode;
        }else {
            SimpleNode<E> tmp = getNodeAt(index-1);
            tmp.genNext(element);
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
        if(index == 0) {
            return removeFirst();
        }else {
            SimpleNode<E> tmp = getNodeAt(index - 1);
            var nxt = tmp.getNext();
            removed = nxt.getContent();
            tmp.setNext(nxt.getNext());
            if(index == effectiveSize -1)
                last = tmp;
        }
        effectiveSize--;
        return removed;

    }

    @Override
    public E removeFirst() {
        if(isEmpty())
            return null;

        var old = header;
        if(header == last){
            this.header = null;
            this.last = null;
        }else {
            header = header.getNext();
        }
        effectiveSize--;

        return old.getContent();
    }

    @Override
    public E removeLast() {
        if(isEmpty())
            return null;
        return remove(effectiveSize-1);
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
        Objects.checkIndex(index, effectiveSize);
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
        if(element == null)
            return null;
        Objects.checkIndex(index, effectiveSize);
        var nNode = new SimpleNode<>(element);
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
        return effectiveSize == 0;
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

    /**
     * Busca en la lista elementos que sean iguales a {@literal target} mediante el método.
     * equals.
     *
     * @param target elemento a buscar.
     * @return lista con elementos encontrados.
     */
    @Override
    public List<E> findAll(E target) {
        List<E> tmp = new SimpleLinkedList<>();
        forEach((item) -> {
            if(item.equals(target))
                tmp.addLast(item);
        });
        return tmp;
    }

    /**
     * Busca elementos a traves de la lista de acuerdo al comparador, para que un objeto sea
     * identificado como 'igual', el comparador debe devolver 0.
     *
     * @param target objeto a comparar
     * @param cmp comparador con primer parametro el objeto {@literal target}.
     * @return lista con los elementos encontrados.
     */
    @Override
    public List<E> findAll(E target, Comparator<E> cmp) {
        List<E> tmp = new SimpleLinkedList<>();
        forEach((item)->{
            if(cmp.compare(target, item) == 0)
                tmp.addLast(item);
        });

        return tmp;
    }

    /**
     * Genera una nueva lista con elementos que contengan en común ambas listas.
     *
     * @param target lista a buscar
     * @return lista con los objetos en común.
     * @throws NullPointerException si el target es null.
     */
    @Override
    public List<E> intersectionWith(List<E> target) {
        if (target == null)
            throw new NullPointerException("Target must not be null");
        List<E> itr = new SimpleLinkedList<>();
        forEach((item)->{
            for (E e : target) {
                if (e.equals(item))
                    itr.addLast(item);
            }
        });
        return itr;
    }

    private SimpleNode<E> getNodeAt(int index) {
        Objects.checkIndex(index, effectiveSize);
        if(index == 0){
            return header;
        }else if (index == effectiveSize -1){
            return last;
        }
        SimpleNode<E> tmp = header;
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
        SimpleNode<E> tmp = header;
        for (int i = 0; i < effectiveSize; i++) {
            str.append(tmp.getContent());
            if (i < effectiveSize-1)
                str.append(", ");
            tmp = tmp.getNext();
        }
        str.append("]");
        return str.toString();
    }

    /**
     * Modifica la lista y elimina a los objetos que no se encuentren dentro del rango especificado.
     *
     * @param from indice desde donde mantener.
     * @param to indice hasta donde mantener.
     * @throws IllegalArgumentException si el rango ingresado no es válido.
     */
    @Override
    public void keepOnly(int from, int to) {
        if (isEmpty())
            return;
        checkRange(from, to);
        to = Math.min(to, effectiveSize-1);
        int oldSize = effectiveSize;
        SimpleNode<E> first = header, end = header, detch = header;
        for (int i = 0; i < oldSize; i++) {
            if(i < from)
                first = first.getNext();
            if(i < to)
                end = end.getNext();
            if(i < from || i > to){
                SimpleNode<E> tmp = detch.getNext();
                detch.clean();
                detch = tmp;
                effectiveSize--;
            }
        }
        header = first;
        last = end;
        end.setNext(null);
    }

    @Override
    public void detach(int from, int to) {
        if (isEmpty())
            return;
        checkRange(from, to);
        to = Math.min(to, effectiveSize-1);
        if(from == 0 && to == effectiveSize-1){
            clear();
            return;
        }
        SimpleNode<E> first = null, end = null, tmp = header;
        int osize = effectiveSize;
        int moves = (to - from) +1;
        for (int i = 0; i < osize; i++) {
            if(i < from)
                first = tmp;
            if ( i <= to)
                end = tmp;
            tmp = tmp.getNext();
        }
        if (first == null) // this is that the from index is 0
            header = end.getNext();
        else if (end == null || end.getNext() == null){ // that means that we reached the end.
            last = first;
            first.setNext(null);
        }else{
            first.setNext(end.getNext());
            if(to == effectiveSize -1)
                last = end.getNext();
        }

        effectiveSize -= moves;
    }

    private void checkRange(int from, int to) {
        if (from > to)
            throw new IllegalArgumentException("from must be lower than to");
        if (from < 0)
            throw new IndexOutOfBoundsException("from must be >= 0");
    }

    /**
     * @return un objeto Iterator.
     */
    @Override
    public Iterator<E> iterator() {
        return new Iterator<>() {
            private SimpleNode<E> pointer = header;
            @Override
            public boolean hasNext() {
                return pointer != null;
            }

            @Override
            public E next() {
                var old = pointer;
                pointer = pointer.getNext();
                return old.getContent();
            }
        };
    }
}
