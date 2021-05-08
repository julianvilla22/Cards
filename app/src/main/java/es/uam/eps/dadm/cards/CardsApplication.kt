package es.uam.eps.dadm.cards

import android.app.Application
import es.uam.eps.dadm.cards.database.CardDatabase
import timber.log.Timber
import java.time.LocalDateTime
import java.util.concurrent.Executors

class CardsApplication: Application() {
    private val executor = Executors.newSingleThreadExecutor()

    init {
        /*tempdeck = Deck("deck")
        decks.add(tempdeck)
        cards.add(Card("To wake up", "Despertarse"))
        cards.add(Card("To give in", "Dar el brazo a torcer"))
        cards.add(Card("To pick up", "Recoger"))
        cards.forEach{
            addCard(it, tempdeck)
        }*/

    }

    override fun onCreate() {
        super.onCreate()
        val cardDatabase = CardDatabase.getInstance(context = this)
        executor.execute {
           /* cardDatabase.cardDao.addCard(
                    Card(question = "To push in", answer = "Colarse", deckId = 0)
            )
            cardDatabase.cardDao.addCard(
                    Card(question = "Despertarse", answer = "To wake up", deckId = 0)
            )
            cardDatabase.cardDao.addCard(
                Card(question = "Bonjour", answer = "Buenos días", deckId = 1)
            )
            cardDatabase.cardDao.addDeck(Deck(0, "Inglés"))
            cardDatabase.cardDao.addDeck(Deck(1, "Francés"))*/
        }
        Timber.plant(Timber.DebugTree())
    }
/*
    companion object {
        var cards: MutableList<Card> = mutableListOf<Card>()
        var decks: MutableList<Deck> = mutableListOf<Deck>()

        lateinit var tempdeck: Deck

        fun getCard(id: String): Card {
            return cards.filter { it.id == id }[0]
        }
        fun addCard(card: Card) {
            cards.add(card)
        }
        fun deleteCard(id: String) {
            cards.remove(getCard(id))
        }
        fun numberOfCardsLeft(): Int {
            var size = 0
            decks.forEach {
                size += it.cards.filter { card -> card.isDue(LocalDateTime.now()) }.size
            }
            return size
        }

        fun getCard(id: String, deckId : String): Card {
            return getDeck(deckId).cards.first() { card -> card.id == id }
        }
        fun randomCard(): Card?{
            var card: Card?
            while (numberOfCardsLeft() > 0){
                card =  decks.random().cards.filter { it.isDue(LocalDateTime.now()) }.randomOrNull()
                if (card != null)
                    return card
            }
            return null
        }

        fun getDeck(id: String): Deck {
            return decks.first{deck -> deck.id == id }
        }
        fun getDeckbyCard(card: Card): Deck {
            decks.forEach{
                if(it.cards.contains(card))
                    return it;
            }
            return Deck("")
        }
        fun delCard(card: Card){
            getDeckbyCard(card).cards.remove(card)
        }
        fun delDeck(deck: Deck){
            decks.remove(deck)
        }

        fun addCard(card: Card, deck: Deck) {
            deck.cards.add(card)
        }

        fun addDeck(name: String){
            decks.add(Deck(name))
        }
        fun totalDecks(): Int{
            return decks.size
        }
        fun totalCards(): Int {
            var size = 0
            decks.forEach {
                size += it.cards.size
            }
            return size
        }
        fun deckCards(deck: Deck): Int{
            return deck.cards.size
        }
        fun totalAverageEasiness(): String{
            var total = totalCards()
            var easiness = 0.0
            decks.forEach {deck ->
                deck.cards.forEach{card ->
                    easiness += card.easiness
                }
            }
            easiness /= total
            return String.format("%.2f", easiness)

        }
        fun deckAverageEasiness(deck: Deck): String{
            var total = deck.cards.size
            var easiness = 0.0
            deck.cards.forEach{card ->
                easiness += card.easiness
            }
            easiness /= total
            return String.format("%.2f", easiness)
        }
    }*/
}