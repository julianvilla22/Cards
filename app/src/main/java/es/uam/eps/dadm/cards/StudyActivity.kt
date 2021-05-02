package es.uam.eps.dadm.cards

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import es.uam.eps.dadm.cards.databinding.ActivityStudyBinding
import timber.log.Timber

private const val ANSWERED_KEY = "es.uam.eps.dadm.cards:answered"

class StudyActivity : AppCompatActivity() {
    //lateinit var binding: ActivityStudyBinding
    //private val viewModel: StudyViewModel by lazy {
    //    ViewModelProvider(this).get(StudyViewModel::class.java)
    //}


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //binding = DataBindingUtil.setContentView(this, R.layout.activity_study)

        Timber.i("onCreate called")

    }

    override fun onStart() {
        super.onStart()
        Timber.i("onStart called")

    }

    override fun onResume() {
        super.onResume()
        Timber.i("onResume called")
        //binding.invalidateAll()
    }

    override fun onPause() {
        super.onPause()
        Timber.i("onPause called")
    }

    override fun onStop() {
        super.onStop()
        Timber.i("onStop called")

    }

    override fun onDestroy() {
        super.onDestroy()
        Timber.i("onDestroy called")
    }
    override fun onRestart() {
        super.onRestart()
        Timber.i("onRestart called")
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        Timber.i("onSaveInstanceState called")
        //outState.putBoolean(ANSWERED_KEY, card.answered)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        Timber.i("onRestoreInstanceState called")
        //card.answered = savedInstanceState?.getBoolean(ANSWERED_KEY) ?: false

    }
}

