package es.uam.eps.dadm.cards

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import es.uam.eps.dadm.cards.database.CardDatabase
import es.uam.eps.dadm.cards.databinding.FragmentCardListBinding
import es.uam.eps.dadm.cards.databinding.FragmentSyncBinding
import timber.log.Timber

class SyncFragment : Fragment()  {
    private lateinit var binding: FragmentSyncBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_sync,
            container,
            false
        )
        binding.downloadButton.setOnClickListener {
            FirebaseSync.downloadUserData(it.context)
        }
        binding.uploadButton.setOnClickListener {
            FirebaseSync.uploadUserData(it.context, viewLifecycleOwner)
        }


        return binding.root
    }
}