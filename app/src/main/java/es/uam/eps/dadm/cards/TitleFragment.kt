package es.uam.eps.dadm.cards

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import es.uam.eps.dadm.cards.databinding.FragmentTitleBinding

class TitleFragment: Fragment() {
    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        (activity as AppCompatActivity).supportActionBar?.hide()
        val binding = DataBindingUtil.inflate<FragmentTitleBinding>(
                inflater,
                R.layout.fragment_title,
                container,
                false)

        binding.cardsTitleTextView.setOnClickListener { view ->
            view.findNavController().navigate(R.id.action_titleFragment_to_deckListFragment)
            (activity as AppCompatActivity).supportActionBar?.show()
        }

        return binding.root
    }
}