package es.uam.eps.dadm.cards

import android.app.Application
import es.uam.eps.dadm.cards.database.CardDatabase

class CardsApplication: Application() {

    init {
        cards.add(Card("To wake up", "Despertarse"))
        cards.add(Card("To give in", "Dar el brazo a torcer"))
        cards.add(Card("To pick up", "Recoger"))
    }

    override fun onCreate() {
        super.onCreate()
        val cardDatabase = CardDatabase.getInstance(context = this)
        cardDatabase.cardDao.addCard(Card(question = "Despertarse", answer = "To wake up"))
    }

    companion object {
        var cards: MutableList<Card> = mutableListOf<Card>()
        fun getCard(id: String): Card {
            return cards.filter { it.id == id }[0]
        }
        fun addCard(card: Card) {
            cards.add(card)
        }
        fun deleteCard(id: String) {
            cards.remove(getCard(id))
        }
    }
}