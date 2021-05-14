package es.uam.eps.dadm.cards.database

import androidx.lifecycle.LiveData
import androidx.room.*
import es.uam.eps.dadm.cards.Card
import es.uam.eps.dadm.cards.Deck
import es.uam.eps.dadm.cards.DeckWithCards

@Dao
interface CardDao {
    @Query("SELECT * FROM cards_table")
    fun getCards(): LiveData<List<Card>>

    @Query("SELECT * FROM cards_table")
    fun getCardsList(): List<Card>

    @Query("SELECT * FROM cards_table WHERE id = :id")
    fun getCard(id: String): LiveData<Card?>

    @Insert(onConflict = OnConflictStrategy.ABORT)
    fun addCard(card: Card)

    @Delete
    fun delCard(card: Card)

    @Update
    fun update(card: Card)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addDeck(deck: Deck)

    @Query("SELECT * FROM cards_table where deckId = :deckId")
    fun getDeckCards(deckId: Long): LiveData<List<Card>>

    @Query("SELECT * FROM decks_table")
    fun getDecks(): LiveData<List<Deck>>

    @Query("SELECT * FROM decks_table where id = :id")
    fun getDeck(id: Long): LiveData<Deck?>

    @Delete
    fun delDeck(deck: Deck)

    @Query("DELETE FROM decks_table")
    fun delDecks()

    @Query("DELETE FROM cards_table")
    fun delCards()

    @Query("DELETE FROM cards_table WHERE deckId = :deckId")
    fun delDeckCards(deckId: Long)

    @Transaction
    @Query("SELECT * FROM decks_table")
    fun getDecksWithCards(): LiveData<List<DeckWithCards>>

    @Transaction
    @Query("SELECT * FROM decks_table WHERE id = :deckId")
    fun getDeckWithCards(deckId: Long): LiveData<DeckWithCards>

    @Query("SELECT id + 1 FROM decks_table ORDER BY id DESC LIMIT 1")
    fun getHighestId(): LiveData<Long>

    @Query("SELECT id + 1 FROM decks_table ORDER BY id DESC LIMIT 1")
    fun getHighestIdL(): LiveData<Long?>


}