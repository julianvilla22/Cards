package es.uam.eps.dadm.cards

import android.os.Bundle
import android.provider.ContactsContract
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import es.uam.eps.dadm.cards.databinding.FragmentStatisticsBinding


class StatisticsFragment : Fragment() {
    lateinit var binding : FragmentStatisticsBinding
    private val viewModel: StatisticsViewModel by lazy {
        ViewModelProvider(this).get(StatisticsViewModel::class.java)
    }
    private fun getEachDeckCards():String{
        var cad = ""
        CardsApplication.decks.forEach{
            cad += "${resources.getText(R.string.statistics_deck_cards)} ${it.name}: ${viewModel.deckCards(it)}\n"
        }
        return cad
    }
    private fun getEachDeckEasiness():String{
        var cad = ""
        CardsApplication.decks.forEach{
            cad += "${it.name}${resources.getText(R.string.statistics_easiness_average_deck)} ${viewModel.deckAverageEasiness(it)}\n"
        }
        return cad
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_statistics, container, false)
        binding.statisticsTotalDecks.text = "${resources.getText(R.string.statistics_deck_total)}${viewModel.totalDecks()}"
        binding.statisticsTotalCards.text = "${resources.getText(R.string.statistics_cards_total)}${viewModel.totalCards()}"
        binding.statisticsDeckCards.text = getEachDeckCards()
        binding.statisticsDeckTotalEasiness.text = "${resources.getText(R.string.statistics_easiness_average_total)}${viewModel.totalAverageEasiness(viewLifecycleOwner)}"
        binding.statisticsDeckEasiness.text = getEachDeckEasiness()


        return binding.root
    }




}