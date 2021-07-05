@file: JvmName("LstUtils")
package com.neoterux.tda.list
import com.neoterux.tda.list.*

/**
 * Suma los números de tipo [java.lang.Integer] en la lista
 */
fun List<Int>.sum() : Int {
    var carry = 0
    val it = iterator()
    while (it.hasNext()) {
        carry+= it.next()
    }
    return carry
}

/**
 * Suma los números de tipo [java.lang.Float] en la lista
 */
fun List<Float>.sum() : Float {
    var carry = 0f
    val it = iterator()
    while (it.hasNext()) {
        carry+= it.next()
    }
    return carry
}

/**
 * Suma los números de tipo [java.lang.Double] en la lista
 */
fun List<Double>.sum() : Double {
    var carry = 0.0
    val it = iterator()
    while (it.hasNext()) {
        carry+= it.next()
    }
    return carry
}

/**
 * Genera un nuevo [com.neoterux.tda.list.ArrayList] a partir de determinados items
 */
fun <T> arraylistOf(vararg items:T): ArrayList<T> = ArrayList<T>(items.size).apply {
    items.forEach { addLast(it) }
}

/**
 * Añade un elemento a la última posición
 */
operator fun <T> List<T>.plusAssign( value: T){
    addLast(value)
}

/**
 * Rota los elementos de la lista 1 vez hacia la izquierda
 */
operator fun <T: Any> LinkedList<T>.dec() = this.apply{ rotate(-1) }

operator fun <T> List<T>.plus(target: Iterable<T>) = this.apply {
    target.forEach {
        this += it
    }
}

/**
 * Obtiene la intersección con el [target]
 */
operator fun <T> List<T>.rem(target: List<T>): List<T>? = this.intersectionWith(target)





