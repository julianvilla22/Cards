package es.uam.eps.dadm.cards

import android.app.Activity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import es.uam.eps.dadm.cards.database.CardDatabase
import es.uam.eps.dadm.cards.databinding.FragmentDeckAddBinding
import java.util.concurrent.Executors


class DeckAddFragment : Fragment() {
    lateinit var binding: FragmentDeckAddBinding
    lateinit var deck: Deck
    lateinit var name: String
    var deckId: Long = 0

    private val executor = Executors.newSingleThreadExecutor()

    private val viewModel by lazy {
        ViewModelProvider(this).get(DeckAddViewModel::class.java)
    }

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
                inflater,
                R.layout.fragment_deck_add,
                container,
                false
        )
        viewModel.higherId.observe(viewLifecycleOwner){id->
                deckId = id ?: 0
            }

        return binding.root
    }


    private fun hideKeyboard(activity: Activity) {
        val imm: InputMethodManager = activity.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        var view = activity.currentFocus
        if (view == null) {
            view = View(activity)
        }
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }

    override fun onStart() {
        super.onStart()

        val nameTextWatcher = object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {}

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                name = s.toString()
            }
        }
        binding.nameFieldText.addTextChangedListener(nameTextWatcher)
        binding.cardEditAccept.setOnClickListener {
            val vm by lazy {
                ViewModelProvider(this).get(DeckAddViewModel::class.java)
            }

            deck = Deck(name = name, id =deckId)

            executor.execute {
                CardDatabase.getInstance(it.context).cardDao.addDeck(deck)
            }
            activity?.let { it1 -> hideKeyboard(it1) }
            it.findNavController()
                    .navigate(DeckAddFragmentDirections.actionDeckAddFragmentToDeckListFragment())
        }
        binding.cardEditCancel.setOnClickListener {
            activity?.let { it1 -> hideKeyboard(it1) }
            it.findNavController()
                    .navigate(DeckAddFragmentDirections.actionDeckAddFragmentToDeckListFragment())
        }

    }
}