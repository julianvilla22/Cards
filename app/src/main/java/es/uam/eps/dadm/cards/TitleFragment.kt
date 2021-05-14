package es.uam.eps.dadm.cards

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
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
        binding.logout.setOnClickListener {
            var auth: FirebaseAuth
            auth = Firebase.auth
            auth.signOut()
            val intent: Intent = Intent(it.context, EmailPasswordActivity::class.java)
            startActivity(intent)
        }

        return binding.root
    }

    override fun onResume() {
        super.onResume()
        if (Firebase.auth.currentUser == null){
            val intent: Intent = Intent(context, EmailPasswordActivity::class.java)
            startActivity(intent)
        }
    }
}