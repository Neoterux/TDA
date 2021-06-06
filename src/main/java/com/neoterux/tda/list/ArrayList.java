package com.neoterux.tda.list;

import com.neoterux.tda.collection.IterableCollection;
import com.neoterux.tda.collection.SearchableCollection;

import java.util.Objects;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

/**
 * Implementación estática del TDA List
 *
 * @param <E> tipo de dato que va a almacenar el ArrayList
 */
public class ArrayList<E> implements List<E>, SearchableCollection<E>, IterableCollection<E>, MutableList<E> {

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

        if (effectiveSize == capacity) {
            addCapacity();
        }
        return false;
    }

    /**
     * Añade un objeto al final de la lista.
     *
     * @param e elemento a añadir.
     * @return true si se añadió con éxito.
     */
    @Override
    public boolean addLast(E e) {
        if (effectiveSize == capacity)
            addCapacity();
        if(isEmpty()) {
            elements[0] = e;
        }else {
            elements[effectiveSize] = e;
        }
        effectiveSize++;
        return false;
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

    private void addCapacity() {
        E[] backup = elements;
        E[] newArr = (E[]) new Object[capacity<<1];
        for (int i = 0; i < backup.length; i++) {
            newArr[i] = backup[i];
        }
        capacity = newArr.length;
        elements = newArr;

    }

    /*
     * Busca el objeto especificado dentro del array de acuerdo a su índice.
     * Tiene una dificultad de O(1).
     *
     * @param index indice del objeto a buscar.
     * @return el objeto a buscar, null si no se encuentra
     */
//    @Override
//    public E findObject(int index) {
//        if(index < 0 || index >= effectiveSize)
//            throw new IndexOutOfBoundsException("index more than ArrayList size");
//        return elements[index];
//    }

    /**
     * Localiza el objeto especificado entro del array interno y devuelve su índice.
     * En el peor caso su dificultad es de O(n).
     * En caso de que haya 2 referencias a un mismo objeto dentro del array, devuelve la primera
     * ocurrencia del objeto a buscar.
     *
     * @param object objeto a localizar
     * @return el índice del objeto, en caso de no encontrarlo devuelve -1.
     */
    @Override
    public int findIndex(E object) {
        for(int i = 0; i < effectiveSize; i++){
            // Search the same instance or equals method defined by objects.
            if(elements[i] == object || elements[i].equals(object)) {
                return i;
            }
        }
        return -1;
    }

    /**
     * Recorre toda la lista y aplica a cada elemento la acción especificada.
     *
     * @param action acción a realizar con los elementos.
     */
    @Override
    public void forEach(final Consumer<E> action) {
        for(int i = 0; i < effectiveSize; i++) {
            action.accept(elements[i]);
        }
    }

    @Override
    public void indexForEach(final BiConsumer<Integer, E> action) {
        for(int i = 0; i < effectiveSize; i++) {
            action.accept(i, elements[i]);
        }
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
        int top = Math.min(to, effectiveSize - 1);
        for (int i = 0; i <= top; i++) {
            if (i >= from && i <= to) {
                elements[i] = null;
                effectiveSize--;
            }
        }
        fixArraySpace(elements);
    }
}
