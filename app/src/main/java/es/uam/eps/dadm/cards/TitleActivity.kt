package es.uam.eps.dadm.cards

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import es.uam.eps.dadm.cards.databinding.ActivityTitleBinding
import timber.log.Timber

class TitleActivity : AppCompatActivity(){
    lateinit var binding: ActivityTitleBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_title)
        NavigationUI.setupWithNavController(
            binding.navView,
            findNavController(R.id.navHostFragment))
    }
    override fun onStart() {
        super.onStart()
        Timber.i("onStart called")

    }

    override fun onResume() {
        super.onResume()
        Timber.i("onResume called")
        supportActionBar?.hide()
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
}