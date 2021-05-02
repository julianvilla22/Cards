package es.uam.eps.dadm.cards

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import es.uam.eps.dadm.cards.databinding.FragmentStudyBinding
import es.uam.eps.dadm.cards.databinding.FragmentTitleBinding
import timber.log.Timber

class StudyFragment: Fragment() {
    lateinit var binding: FragmentStudyBinding
    private val viewModel: StudyViewModel by lazy {
        ViewModelProvider(this).get(StudyViewModel::class.java)
    }

    private var listener = View.OnClickListener { v ->
        // Asigana a quality el valor 0, 3 o 5,
        // dependiendo del botón que se haya pulsado
        val quality = when (v?.id) {
            binding.difficultButton.id -> 0
            binding.doubtButton.id -> 3
            binding.easyButton.id -> 5
            else -> -1
        }
        // Llama al método update de viewModel
        viewModel.update(quality)

        // Si la propiedad card de viewModel es null
        // informa al usuario mediante un Toast de que
        // no quedan tarjetas
        if(viewModel.card == null){
            binding.relativeLayout.visibility = View.INVISIBLE
            Toast.makeText(activity, getString(R.string.no_more_cards), Toast.LENGTH_LONG).show()
        }
        binding.invalidateAll()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate<FragmentStudyBinding>(inflater, R.layout.fragment_study, container, false)
        binding.studyViewModel = viewModel
        binding.answerButton.setOnClickListener {
            viewModel.card?.answered = true
            binding.invalidateAll()
        }
        binding.difficultButton.setOnClickListener(listener)
        binding.doubtButton.setOnClickListener(listener)
        binding.easyButton.setOnClickListener(listener)
        return binding.root
    }
}