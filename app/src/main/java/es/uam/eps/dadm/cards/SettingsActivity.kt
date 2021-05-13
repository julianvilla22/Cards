package es.uam.eps.dadm.cards

import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.PreferenceManager

class SettingsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.settings_activity)
        if (savedInstanceState == null) {
            supportFragmentManager
                .beginTransaction()
                .replace(R.id.settings, SettingsFragment())
                .commit()
        }
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }


    class SettingsFragment : PreferenceFragmentCompat() {
        override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
            setPreferencesFromResource(R.xml.root_preferences, rootKey)
        }
    }
    companion object {
        const val MAXIMUM_KEY = "max_number_cards"
        const val MAXIMUM_DEFAULT = "20"
        const val BOARD_VISIBLE = "board"
        const val BOARD_DEFAULT = "true"

        fun getMaximumNumberOfCards(context: Context): String? {
            return PreferenceManager
                .getDefaultSharedPreferences(context)
                .getString(MAXIMUM_KEY, MAXIMUM_DEFAULT)
        }
        fun getBoardVisible(context: Context): String? {
            return PreferenceManager
                .getDefaultSharedPreferences(context)
                .getString(BOARD_VISIBLE, BOARD_DEFAULT)
        }
        fun setMaximumNumberOfCards(context: Context, max: String) {
            val sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(context)
            val editor = sharedPreferences.edit()
            editor.putString(SettingsActivity.MAXIMUM_KEY, max)
            editor.commit ()
            //editor.apply()
        }
    }
}