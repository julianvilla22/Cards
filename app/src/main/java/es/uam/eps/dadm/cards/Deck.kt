package es.uam.eps.dadm.cards

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.File
import java.time.LocalDateTime
import java.util.*

@Entity(tableName = "decks_table")
data class Deck(
    @PrimaryKey
    var id: Long,
    @ColumnInfo(name = "deck_name")
    var name: String
) {
    constructor() : this(27, "nombre")
}