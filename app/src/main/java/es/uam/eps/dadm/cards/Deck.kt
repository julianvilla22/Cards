package es.uam.eps.dadm.cards

import java.io.File
import java.time.LocalDateTime
import java.util.*

class Deck(var name: String, var id: String = UUID.randomUUID().toString()) {
    var cards: MutableList<Card> = mutableListOf()

}