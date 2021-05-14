package es.uam.eps.dadm.cards

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import es.uam.eps.dadm.cards.database.CardDatabase
import java.util.concurrent.Executors

class StatisticsViewModel(application: Application) : AndroidViewModel(application) {
    private val executor = Executors.newSingleThreadExecutor()
    private val context = getApplication<Application>().applicationContext
    val cards: LiveData<List<Card>> = CardDatabase.getInstance(context).cardDao.getCards()
    val decks: LiveData<List<Deck>> = CardDatabase.getInstance(context).cardDao.getDecks()
    private var avgeas = 0.0

    fun totalDecks(): Int {
        return decks.value!!.size
    }

    fun totalCards(): Int {
        return cards.value?.size ?: 0
    }

    fun deckCards(deck: Deck): Int {
        return CardDatabase.getInstance(context = context).cardDao.getDeckCards(deck.id).value?.size
            ?: 0
    }

    fun totalAverageEasiness(lifecycleOwner: LifecycleOwner): String {

        cards.observe(lifecycleOwner) {
            var total = totalCards()
            var easiness = 0.0
            it.forEach { card ->
                easiness += card.easiness
            }
            avgeas = easiness / total
        }
        return String.format("%.2f", avgeas)
    }

    fun deckAverageEasiness(deck: Deck): String {
        var total = CardDatabase.getInstance(context).cardDao.getCards().value?.size ?: 0
        var easiness = 0.0
        var list = CardDatabase.getInstance(context = context).cardDao.getDeckCards(deck.id).value
        list?.forEach { card ->
            easiness += card.easiness
        }
        easiness /= total
        return String.format("%.2f", easiness)
    }
}