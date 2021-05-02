package es.uam.eps.dadm.cards

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.google.android.material.snackbar.Snackbar
import es.uam.eps.dadm.cards.databinding.FragmentCardListBinding
import es.uam.eps.dadm.cards.databinding.FragmentTitleBinding

class CardListFragment : Fragment() {
    private lateinit var binding: FragmentCardListBinding
    private lateinit var adapter: CardAdapter
    private lateinit var deck : Deck

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
                inflater,
                R.layout.fragment_card_list,
                container,
                false
        )
        val args = CardListFragmentArgs.fromBundle(requireArguments())
        adapter = CardAdapter()
        deck = CardsApplication.getDeck(args.deckid)
        adapter.data = deck.cards
        binding.cardRecyclerView.adapter = adapter

        /*binding.studyButton.setOnClickListener {
            if (CardsApplication.numberOfCardsLeft() > 0)
                it.findNavController().navigate(R.id.action_cardListFragment_to_studyFragment)
            else
                Toast.makeText(activity, R.string.no_more_cards, Toast.LENGTH_SHORT).show()
        }*/
        binding.newCardFab.setOnClickListener {
            val card = Card("", "")
            CardsApplication.addCard(card, deck)
            it.findNavController().navigate(CardListFragmentDirections
                .actionCardListFragmentToCardEditFragment(card.id,args.deckid))
        }

        return binding.root
    }
}