package es.uam.eps.dadm.cards

import android.app.Activity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import es.uam.eps.dadm.cards.databinding.FragmentCardEditBinding
import es.uam.eps.dadm.cards.databinding.FragmentCardListBinding

class CardEditFragment : Fragment() {
    lateinit var binding: FragmentCardEditBinding
    lateinit var card: Card
    lateinit var prevQuestion : String
    lateinit var prevAnswer : String
    lateinit var deckId : String

    private fun hideKeyboard(activity: Activity) {
        val imm: InputMethodManager = activity.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        var view = activity.currentFocus
        if (view == null) {
            view = View(activity)
        }
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_card_edit,
            container,
            false
        )

        val args = CardEditFragmentArgs.fromBundle(requireArguments())
        deckId = args.deckid
        card = CardsApplication.getCard(args.cardid, deckId)
        binding.card = card
        prevQuestion = card.question
        prevAnswer = card.answer

        return binding.root
    }

    override fun onStart() {
        super.onStart()

        val questionTextWatcher = object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {}

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                card.question = s.toString()
            }
        }
        val answerTextWatcher = object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {}

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                card.answer = s.toString()
            }
        }
        binding.questionFieldText.addTextChangedListener(questionTextWatcher)
        binding.answerFieldText.addTextChangedListener(answerTextWatcher)
        binding.cardEditAccept.setOnClickListener {
            activity?.let { it1 -> hideKeyboard(it1) }
            it.findNavController()
                    .navigate(CardEditFragmentDirections
                            .actionCardEditFragmentToCardListFragment(deckId))
        }
        binding.cardEditCancel.setOnClickListener {
            card.answer = prevAnswer
            card.question = prevQuestion
            activity?.let { it1 -> hideKeyboard(it1) }
            it.findNavController()
                    .navigate(CardEditFragmentDirections
                            .actionCardEditFragmentToCardListFragment(deckId))
        }

    }
}