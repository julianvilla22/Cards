package es.uam.eps.dadm.cards

import android.app.Application
import androidx.lifecycle.*
//import es.uam.eps.dadm.cards.CardsApplication.Companion.decks
import es.uam.eps.dadm.cards.database.CardDatabase
import timber.log.Timber
import java.time.LocalDateTime
import java.util.concurrent.Executors

class StudyViewModel(application: Application) : AndroidViewModel(application) {
    private val executor = Executors.newSingleThreadExecutor()

    private val context = getApplication<Application>().applicationContext
    var card: Card? = null
    var cards: LiveData<List<Card>> = CardDatabase.getInstance(context).cardDao.getCards()
    var dueCard: LiveData<Card?> =
            Transformations.map(cards, ::due)
    var cardsLeft: LiveData<Int> =
            Transformations.map(cards, ::left)
    var cardsStudied: LiveData<Int> =
        Transformations.map(cards, ::today)
    //var boardVisible: String? = "true"//SettingsActivity.getBoardVisible(context)

    private fun due(cards: List<Card>) = try {
        cards.filter { card -> card.isDue(LocalDateTime.now()) }.random()
    } catch (e: Exception) {
        null
    }

    private fun left(cards: List<Card>) =
            cards.filter { card -> card.isDue(LocalDateTime.now()) }.size

    private fun today(cards: List<Card>) =
        cards.filter { card -> card.studiedToday() }.size

    fun update(quality: Int) {
        card?.quality = quality
        card?.update(LocalDateTime.now())

        executor.execute {
            CardDatabase.getInstance(context).cardDao.update(card!!)
        }
    }
}