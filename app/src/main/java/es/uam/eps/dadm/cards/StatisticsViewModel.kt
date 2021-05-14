package es.uam.eps.dadm.cards

import android.app.Application
import android.graphics.Color
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import es.uam.eps.dadm.cards.database.CardDatabase
import java.time.LocalDate
import java.time.LocalDateTime
import java.util.concurrent.Executors

class StatisticsViewModel(application: Application) : AndroidViewModel(application) {
    private val executor = Executors.newSingleThreadExecutor()
    private val context = getApplication<Application>().applicationContext
    val decks: LiveData<List<Deck>> = CardDatabase.getInstance(context).cardDao.getDecks()
    private val _cards = MutableLiveData<List<Card>>()
    val cards: LiveData<List<Card>>
        get() = _cards
    private var dataMap = HashMap<LocalDate, Float>()
    init {
        _cards.value = CardDatabase.getInstance(context).cardDao.getCards().value
    }

    fun totalDecks(): Int {
        return decks.value?.size ?: 0
    }

    fun totalCards(): Int {
        return cards.value?.size ?: 0
    }

    fun deckCards(deck: Deck): Int {
        return CardDatabase.getInstance(context = context).cardDao.getDeckCards(deck.id).value?.size
            ?: 0
    }

    fun barCharGetData(lifecycleOwner: LifecycleOwner): BarData {

        val bargroup = ArrayList<BarEntry>()
        _cards.value?.forEach() {
            var date = LocalDateTime.parse(it.nextPracticeDate).toLocalDate()
            dataMap[date] = dataMap.getOrDefault(date, 0F) + 1F
        }
        var i = 0F;
        dataMap.keys.sorted().forEach() {
            if (dataMap[it] != null) {
                dataMap[it]?.let { it1 -> BarEntry(i, it1, it.toString()) }
                    ?.let { it2 -> bargroup.add(it2) }
                i++
            }

        }
        var data = BarDataSet(bargroup, "")
        data.addColor(Color.parseColor("#AF5FFF"))
        data.addColor(Color.parseColor("#7C5FFF"))
        data.addColor(Color.parseColor("#FF5FE9"))
        data.addColor(Color.parseColor("#FAFF5F"))
        data.addColor(Color.parseColor("#FFC05F"))
        data.valueTextSize = 16f

        return BarData(data)
    }





}