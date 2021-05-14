package es.uam.eps.dadm.cards

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import androidx.preference.PreferenceManager
import com.google.firebase.FirebaseError
import com.google.firebase.database.*
import es.uam.eps.dadm.cards.databinding.ActivityTitleBinding
import timber.log.Timber

class TitleActivity : AppCompatActivity(){
    lateinit var binding: ActivityTitleBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_title)

        val database: FirebaseDatabase = FirebaseDatabase.getInstance()
        val reference = database.getReference("mensaje")
        reference.setValue("Hola desde Cards 3")
        reference.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
                Timber.i("DATA error")
            }

            override fun onDataChange(p0: DataSnapshot) {
                Toast.makeText(
                    baseContext,
                    p0.value.toString(),
                    Toast.LENGTH_LONG
                ).show()
                Timber.i("DB CHANGED:"+p0.value.toString())
            }
        })
        NavigationUI.setupWithNavController(
            binding.navView,
            findNavController(R.id.navHostFragment))
        PreferenceManager.setDefaultValues(
            this,
            R.xml.root_preferences,
            false
        )
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