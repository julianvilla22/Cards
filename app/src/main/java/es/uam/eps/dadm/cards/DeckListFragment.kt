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
import es.uam.eps.dadm.cards.DeckListFragmentDirections.Companion.actionDeckListFragmentToDeckAddFragment
import es.uam.eps.dadm.cards.databinding.FragmentCardListBinding
import es.uam.eps.dadm.cards.databinding.FragmentDeckListBinding
import es.uam.eps.dadm.cards.databinding.FragmentTitleBinding

class DeckListFragment : Fragment() {
    private lateinit var binding: FragmentDeckListBinding
    private lateinit var adapter: DeckAdapter

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
                R.layout.fragment_deck_list,
                container,
                false
        )
        adapter = DeckAdapter()
        adapter.data = CardsApplication.decks
        binding.deckRecyclerView.adapter = adapter

        binding.newDeckFab.setOnClickListener {
            it.findNavController().navigate(actionDeckListFragmentToDeckAddFragment())
        }


        return binding.root
    }
}