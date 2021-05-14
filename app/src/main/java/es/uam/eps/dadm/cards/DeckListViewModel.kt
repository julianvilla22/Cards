package es.uam.eps.dadm.cards

import android.annotation.SuppressLint
import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import es.uam.eps.dadm.cards.database.CardDatabase

class DeckListViewModel(application: Application) : AndroidViewModel(application) {
    private val context = getApplication<Application>().applicationContext
    var decks: LiveData<List<DeckWithCards>> =
        CardDatabase.getInstance(context).cardDao.getDecksWithCards()

}