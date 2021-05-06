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
    private var avgeas = 0.0

    fun totalDecks(): Int{
        return CardsApplication.decks.size
    }
    fun totalCards(): Int {
        /*var size = 0
        CardsApplication.decks.forEach {
            size += it.cards.size
        }*/
        return cards.value?.size ?: 0
    }
    fun deckCards(deck: Deck): Int{
        return deck.cards.size
    }
    fun totalAverageEasiness(lifecycleOwner: LifecycleOwner): String{

        /*CardsApplication.decks.forEach { deck ->
            deck.cards.forEach{card ->
                easiness += card.easiness
            }
        }*/
        cards.observe(lifecycleOwner){
            var total = totalCards()
            var easiness = 0.0
            it.forEach{card ->
                easiness += card.easiness
            }
            avgeas = easiness / total
        }
        return String.format("%.2f", avgeas)
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