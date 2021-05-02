package es.uam.eps.dadm.cards

import java.io.File

class Pais(var nombre: String, var cerveza: Int, var vino: Int, var bebida: Int)

fun selector(pais: Pais) : Int {
    return maxOf(pais.cerveza, pais.vino, pais.bebida)
}

fun main() {
    val lineas: List<String> = File("data/bebidas.txt").readLines()
    val paises: MutableList<Pais> = mutableListOf<Pais>()
    var trozos: List<String>
    var nombre: String
    var cerveza: Int
    var vino: Int
    var bebidas: Int

    for (linea in lineas) {
        trozos = linea.split(",")
        nombre = trozos.get(0)
        cerveza = trozos.get(1).toInt()
        bebidas = trozos.get(2).toInt()
        vino = trozos.get(3).toInt()
        paises += Pais(nombre, cerveza, vino, bebidas)
    }

    val grupo = paises.sortedByDescending { selector(it) }
    for (pais in grupo)
        println("${pais.nombre} : ${pais.vino}")
}