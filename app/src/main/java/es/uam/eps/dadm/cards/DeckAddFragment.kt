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
import androidx.navigation.findNavController
import es.uam.eps.dadm.cards.DeckAddFragmentDirections.Companion.actionDeckAddFragmentToDeckListFragment
import es.uam.eps.dadm.cards.databinding.FragmentDeckAddBinding


class DeckAddFragment : Fragment() {
    lateinit var binding: FragmentDeckAddBinding
    lateinit var deck: Deck
    lateinit var name: String

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
            CardsApplication.addDeck(name)
            activity?.let { it1 -> hideKeyboard(it1) }
            it.findNavController()
                    .navigate(actionDeckAddFragmentToDeckListFragment())
        }
        binding.cardEditCancel.setOnClickListener {
            activity?.let { it1 -> hideKeyboard(it1) }
            it.findNavController()
                    .navigate(actionDeckAddFragmentToDeckListFragment())
        }

    }
}