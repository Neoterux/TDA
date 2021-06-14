package com.neoterux.tda.list;

import java.util.Comparator;
import java.util.Iterator;
import java.util.Objects;

/**
 * Implementación estática del TDA List
 *
 * @param <E> tipo de dato que va a almacenar el ArrayList
 */
public class ArrayList<E> implements MutableList<E> {

    /**
     * Array que contiene los elementos del ArrayList.
     */
    private E[] elements;

    /**
     * Capacidad del arreglo de elementos
     */
    private int capacity;

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
     *
     * @param size capacidad inicial del ArrayList, debe ser mayor a 0.
     */
    public ArrayList(int size) {
        if (size <= 0)
            throw new IllegalArgumentException("Illegal size to initialization");
        capacity = size;
        elements = (E[]) (new Object[size]);
    }

    /**
     * Añade un objeto a la primera posición de la lista.
     *
     * @param e objeto a añadir
     * @return true si se añadió con éxito.
     */
    @Override
    public boolean addFirst(E e) {
        if(e == null)
            return false;
        if (effectiveSize == capacity) {
            addCapacity();
        }
        for (int i = effectiveSize - 1; i >0; i--) {
            elements[i+1] = elements[i];
        }
        elements[0] = e;
        effectiveSize++;
        return true;
    }

    /**
     * Añade un objeto al final de la lista.
     *
     * @param e elemento a añadir.
     * @return true si se añadió con éxito.
     */
    @Override
    public boolean addLast(E e) {
        if (e == null)
            return false;
        if (effectiveSize == capacity)
            addCapacity();
        if(isEmpty()) {
            elements[0] = e;
        }else {
            elements[effectiveSize] = e;
        }
        effectiveSize++;
        return true;
    }

    /**
     * Añade un elemento de tipo E, en un índice específico,
     * desplaza una posición a los elementos contiguos.
     *
     * @param index   indice a insertar
     * @param element Elemento a añádir
     */
    @Override
    public void add(int index, E element) {
        if(element == null)
            return;

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

    /**
     * Remueve un objeto en la posición especificada.
     * En la peor situación tiene una dificultad de O(n^2).
     *
     * @param index índice del objeto a eliminar de la lista.
     * @return elemento descartado.
     */
    @Override
    public E remove(int index) {
        if (index < 0 || index >= effectiveSize){
            return null;
        }
        E obj = elements[index];
        elements[index] = null;
        // We can't fix array elements when is the last object
        if(index != effectiveSize - 1)
            fixArraySpace(elements);
        effectiveSize--;
        return obj;
    }

    /**
     * Remueve el primer elemento de la lista.
     *
     * @return el elemento removido
     */
    @Override
    public E removeFirst() {
        if (effectiveSize == 0)
            return null;

        E old = elements[0];
        elements[0] = null;
        fixArraySpace(elements);
        effectiveSize--;
        return old;
    }

    /**
     * Remueve el último elemento que se encuentra en la lista.
     *
     * @return el elemento removido de la lista.
     */
    @Override
    public E removeLast() {
        if (effectiveSize == 0)
            return null;

        E old = elements[--effectiveSize];
        elements[effectiveSize] = null;
        return old;
    }

    /**
     * Obtiene un elemento en un índice específico
     *
     * @param index indice del elemento a buscar
     * @return elemento
     */
    @Override
    public E get(int index) {
        if (isEmpty())
            return null;
        if (index < 0 || index >= effectiveSize) {
            throw new IndexOutOfBoundsException("invalid index");
        }
        return elements[index];
    }

    /**
     * Reemplaza un objeto en una posición específica por otro objeto.
     *
     * @param index posición a intercambiar
     * @param element nuevo objeto a colocar
     * @return el objeto descartado en la posición {@literal index}
     */
    @Override
    public E set(int index, E element) {
        Objects.checkIndex(index, effectiveSize);
        E detachObject = elements[index];
        elements[index] = element;
        return detachObject;
    }

    /**
     * @return la cantidad de objetos almacenados en el array
     */
    @Override
    public int size() {
        return effectiveSize;
    }

    /**
     * @return true si el {@link #effectiveSize} es 0.
     */
    @Override
    public boolean isEmpty() {
        return effectiveSize == 0;
    }

    /**
     * Borra los elementos de la lista.
     */
    @Override
    public void clear() {
        for (int i = 0; i < effectiveSize; i++) {
            elements[i] = null;
        }
        effectiveSize = 0;
    }

    /**
     * Crea un nuevo ArrayList a partir de un array,
     * los elementos del list son un shallow copy del array original.
     * tamaño del array.
     * @param array from
     * @param <E> tipo de dato para el ArrayList
     * @return ArrayList con elementos del array original
     */
    public static <E> ArrayList<E> fromArray(E[] array) {
        ArrayList<E> na = new ArrayList<>(array.length);
        na.elements = fixArraySpace(array.clone());
        int esize = 0;
        for (E e : na.elements) {
            if (e == null) {
                break;
            }
            esize++;
        }
        na.effectiveSize = esize;

        return na;
    }

    /**
     * Ordena los espacios (null) del Array posicionando consecutivamente cada objeto que contenga el array.
     * En el peor escenario tiene una complejidad de O(n^2).
     *
     * @param array arreglo a reposicionar.
     * @param <E> tipo de dato del array.
     * @return el {@literal array} con los objetos contenidos ordenado.
     */
    private static <E> E[] fixArraySpace(E[] array) {
        for (int i = 1; i < array.length; i++) {
            if(array[i] == null)
                continue;
            if(array[i-1] == null) {
                int nidx = -1;
                for (int j = i; j > 0; j--) {
                    if (array[j - 1] == null) {
                        nidx = j - 1;
                    }
                }
                array[nidx] = array[i];
                array[i] = null;
            }
        }
        return array;
    }

    /**
     * @return Una representación de la lista en forma legible.
     */
    @Override
    public String toString() {
        StringBuilder representation = new StringBuilder("[");
        for(int i = 0; i < effectiveSize; i++){
            representation.append(elements[i]);
            if (i != effectiveSize - 1) {
                representation.append(", ");
            }
        }
        representation.append("]");
        return representation.toString();
    }

    /**
     * Añade capacidad al {@link ArrayList#elements}, cada vez que se ejecuta la capacidad
     * del array se ve aumentada x2.
     */
    private void addCapacity() {
        E[] backup = elements;
        /* other option, more efficient is:
        elements = Arrays.copyOf(elements, capacity*2);
        capacity = elements.length;
         */
        E[] newArr = (E[]) new Object[capacity<<1];
        for (int i = 0; i < backup.length; i++) {
            newArr[i] = backup[i];
        }
        capacity = newArr.length;
        elements = newArr;

    }

    /**
     * Modifica los elementos de la lista y mantiene los items que se encuentren entre el rango
     * [from, to] inclusivos. si 'to'es mayor al index maximo, se toma como límite el índice máximo.
     *
     * @param from index desde donde mantener
     * @param to index hasta donde mantener
     */
    @Override
    public void keepOnly(int from, int to) {
        checkRanges(from, to);

        int top = Math.min(to, effectiveSize - 1);

        int originalSize = effectiveSize;
        for (int i = 0; i < originalSize; i++) {
            if (!(i >= from && i <= top)) {
                elements[i] = null;
                effectiveSize --;
            }
        }

        fixArraySpace(elements);
    }

    /**
     * Elimina los elementos que se encuentren dentro del rango [from, to].
     *
     * @param from index desde donde eliminar
     * @param to index hasta donde eliminar
     */
    @Override
    public void detach(int from, int to) {
        checkRanges(from, to);

        int top = Math.min(to, effectiveSize - 1);
        for (int i = 0; i <= top; i++) {
            if (i >= from && i <= to) {
                elements[i] = null;
                effectiveSize--;
            }
        }
        fixArraySpace(elements);
    }

    private void checkRanges(int from, int to) {
        if (from > to)
            throw new IllegalArgumentException("from value must be lower than to");
        if (from < 0 || to < 0)
            throw new IllegalArgumentException("Values must me greater than 0");
        if (from >= effectiveSize)
            throw new IllegalArgumentException("from must be inside list bounds");
    }

    /**
     * Genera un nuevo objeto iterador, para recorrer la lista de manera externa.
     *
     * @return un nuevo objeto iterador
     */
    @Override
    public Iterator<E> iterator() {
        // TODO: test if works
        return new Iterator<>() {
            int pointer = 0;
            @Override
            public boolean hasNext() {
                return pointer < effectiveSize;
            }

            @Override
            public E next() {
                if (pointer >= effectiveSize)
                    return null;
                return elements[pointer++];
            }
        };
    }

    /**
     * Busca elementos dentro de la lista mediante {@code target.equals(element)} y devuelve una nueva lista
     * con los elementos que coincidan.
     *
     * @param target elemento a comparar.
     * @return Lista con elementos iguales.
     */
    @Override
    public List<E> findAll(E target) {
        // TODO: implement and test
        return findAll(target, (t, comp)-> (t.equals(comp))? 0: 1);
    }

    /**
     * Busca elementos dentro de la lista de acuerdo a lo especificado en el comparador.
     * Los elementos que retornen 0 se ingresaran a la nueva lista.
     *
     * @param target objeto a comparar con los elementos de la lista.
     * @param cmp comparador con el {@literal target} como primer argumento.
     * @return lista con elementos de acuerdo al resultado del comparador.
     */
    @Override
    public List<E> findAll(E target, Comparator<E> cmp) {
        // TODO: implement and test
        List<E> tmp = new ArrayList<>();
        for (int i = 0; i < effectiveSize; i++) {
            if (cmp.compare(target, elements[i]) == 0)
                tmp.addLast(elements[i]);
        }
        return tmp;
    }

    /**
     * Genera una lista con elementos que sean iguales entre esta lista,
     * y la lista {@literal target}. La igualdad se evalua mediante el método equals
     * {@code element.equals(target_element);}.
     *
     * @param target lista a comparar objetos
     * @return Lista con los objetos compartidos entre ambas listas.
     */
    @Override
    public List<E> intersectionWith(List<E> target) {
        // TODO: implement and test
        List<E> tmp = new ArrayList<>();
        forEach((element) ->{
            for (E current : target) {
                if (current.equals(element))
                    tmp.addLast(current);
            }
        });
        return tmp;
    }

}
