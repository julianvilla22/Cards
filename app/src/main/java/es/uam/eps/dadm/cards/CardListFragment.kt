package es.uam.eps.dadm.cards

import android.content.Context
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.google.android.material.snackbar.Snackbar
import es.uam.eps.dadm.cards.database.CardDatabase
import es.uam.eps.dadm.cards.databinding.FragmentCardListBinding
import es.uam.eps.dadm.cards.databinding.FragmentTitleBinding
import java.util.concurrent.Executors

class CardListFragment : Fragment() {
    private val executor = Executors.newSingleThreadExecutor()

    private lateinit var binding: FragmentCardListBinding
    private lateinit var adapter: CardAdapter
    private val cardListViewModel by lazy {
        ViewModelProvider(this).get(CardListViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
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
        //deck = CardsApplication.getDeck(args.deckid)
        //deck = CardsApplication.tempdeck


        //adapter.data = deck.cards
        adapter.data = emptyList()
        binding.cardRecyclerView.adapter = adapter

        /*binding.studyButton.setOnClickListener {
            if (CardsApplication.numberOfCardsLeft() > 0)
                it.findNavController().navigate(R.id.action_cardListFragment_to_studyFragment)
            else
                Toast.makeText(activity, R.string.no_more_cards, Toast.LENGTH_SHORT).show()
        }*/
        binding.newCardFab.setOnClickListener {
            val card = Card("", "", deckId = args.deckid)
            //CardsApplication.addCard(card, deck)
            executor.execute {CardDatabase.getInstance(context = it.context).cardDao.addCard(card)}
            it.findNavController().navigate(CardListFragmentDirections
                .actionCardListFragmentToCardEditFragment(card.id,args.deckid))
        }
        cardListViewModel.loadDeckId(args.deckid)
        cardListViewModel.cards.observe(
                viewLifecycleOwner,
                Observer {
                    adapter.data = it
                    adapter.notifyDataSetChanged()
                })

        return binding.root
    }
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.fragment_card_list, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.settings -> {
                // Aquí irá el código para arrancar las preferencias de Cards
            }
        }
        return true
    }
}