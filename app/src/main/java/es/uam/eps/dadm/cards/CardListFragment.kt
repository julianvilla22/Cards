package es.uam.eps.dadm.cards

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.preference.Preference
import androidx.preference.PreferenceManager
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.database.FirebaseDatabase
import es.uam.eps.dadm.cards.database.CardDatabase
import es.uam.eps.dadm.cards.databinding.FragmentCardListBinding
import es.uam.eps.dadm.cards.databinding.FragmentTitleBinding
import timber.log.Timber
import java.util.concurrent.Executors

private const val DATABASENAME = "tarjetas"

class CardListFragment : Fragment() {
    private val executor = Executors.newSingleThreadExecutor()

    private lateinit var binding: FragmentCardListBinding
    private lateinit var adapter: CardAdapter
    private val cardListViewModel by lazy {
        ViewModelProvider(this).get(CardListViewModel::class.java)
        //ViewModelProvider(this).get(CardListFirebaseViewModel::class.java)
    }

    private var reference = FirebaseDatabase.getInstance().getReference(DATABASENAME)

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



        adapter.data = emptyList()
        binding.cardRecyclerView.adapter = adapter

        binding.newCardFab.setOnClickListener {
            val card = Card("", "", deckId = args.deckid)
            //CardsApplication.addCard(card, deck)
            executor.execute { CardDatabase.getInstance(context = it.context).cardDao.addCard(card) }
            //reference.child(card.id).setValue(card)
            it.findNavController().navigate(
                CardListFragmentDirections
                    .actionCardListFragmentToCardEditFragment(card.id, args.deckid)
            )
        }
        cardListViewModel.loadDeckId(args.deckid)
        cardListViewModel.cards.observe(
            viewLifecycleOwner,
            {
                adapter.data = it
                adapter.notifyDataSetChanged()
            })
        Timber.i(context?.let { SettingsActivity.getMaximumNumberOfCards(it) })

        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.fragment_card_list, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.settings -> {
                startActivity(Intent(activity, SettingsActivity::class.java))
            }
        }
        return true
    }
}