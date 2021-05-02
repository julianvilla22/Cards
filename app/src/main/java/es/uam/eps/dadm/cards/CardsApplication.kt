package es.uam.eps.dadm.cards

import android.app.Application
import timber.log.Timber
import java.time.LocalDateTime

class CardsApplication : Application() {

    init {
        var ingles = Deck("Inglés")
        ingles.cards.add(Card("To wake up", "Despertarse"))
        ingles.cards.add(Card("To pick up", "Recoger"))
        decks.add(ingles)
        var frances = Deck("Francés")
        frances.cards.add(Card("Bonjour", "Buenos días"))
        frances.cards.add(Card("Bon voyage", "Buen viaje"))
        decks.add(frances)
    }

    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
    }

    companion object {
        var decks: MutableList<Deck> = mutableListOf<Deck>()
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

    }
}