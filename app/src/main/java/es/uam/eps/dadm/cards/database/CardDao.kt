package es.uam.eps.dadm.cards.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import es.uam.eps.dadm.cards.Card

@Dao
interface CardDao {
    @Query("SELECT * FROM cards_table")
    fun getCards(): List<Card>

    @Query("SELECT * FROM cards_table WHERE id = :id")
    fun getCard(id: String): Card?

    @Insert
    fun addCard(card: Card)

}