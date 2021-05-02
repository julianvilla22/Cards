package es.uam.eps.dadm.cards

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import es.uam.eps.dadm.cards.CardsApplication.Companion.decks
import timber.log.Timber
import java.time.LocalDateTime

class StudyViewModel : ViewModel() {
    var card: Card? = null
    private var _cardsLeft = MutableLiveData<Int>()
    val cardsLeft: LiveData<Int>
        get() = _cardsLeft

    init {
        card = random_card()
        _cardsLeft.value = CardsApplication.numberOfCardsLeft()
    }

    fun update(quality: Int) {
        Timber.i("SE HA PULSADO BOTON DE CALIDAD: ${quality}")
        card?.quality = quality
        card?.update(LocalDateTime.now())
        card = random_card()
        _cardsLeft.value = cardsLeft.value?.minus(1)
    }

    private fun random_card() = CardsApplication.randomCard()
}