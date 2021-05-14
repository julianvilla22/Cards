package es.uam.eps.dadm.cards

import android.content.Context
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase
import es.uam.eps.dadm.cards.database.CardDatabase
import java.util.concurrent.Executors

class FirebaseSync() {
    companion object {
        var auth = Firebase.auth
        private val executor = Executors.newSingleThreadExecutor()


        lateinit var reference: DatabaseReference
        fun uploadUserData(context: Context, viewLifecycleOwner: LifecycleOwner) {
            if (auth.currentUser != null) {
                reference = FirebaseDatabase.getInstance().getReference("users")
                    .child(auth.currentUser!!.uid)
                reference.removeValue()
                var decks: LiveData<List<DeckWithCards>> =
                    CardDatabase.getInstance(context).cardDao.getDecksWithCards()
                decks.observe(viewLifecycleOwner) {
                    it.forEach { dwc ->
                        reference.child("decks").child(dwc.deck.id.toString()).setValue(dwc.deck)
                        dwc.cards.forEach { card ->
                            reference.child("cards").child(card.id).setValue(card)
                        }

                    }
                }

            }


        }

        fun downloadUserData(context: Context) {
            val ref =
                FirebaseDatabase.getInstance().getReference("users").child(auth.currentUser!!.uid)
            lateinit var dwc: HashMap<String, DeckWithCards>
            clearLocalDB(context)
            ref.child("cards").get().addOnSuccessListener {
                it.children.forEach { card ->
                    executor.execute {
                        card.getValue(Card::class.java)?.let { it1 ->
                            CardDatabase.getInstance(context).cardDao.addCard(it1)
                        }
                    }
                }
            }
            ref.child("decks").get().addOnSuccessListener {
                it.children.forEach { deck ->
                    executor.execute {
                        deck.getValue(Deck::class.java)?.let { d ->
                            CardDatabase.getInstance(context).cardDao.addDeck(d)
                        }
                    }
                }
            }

        }

        fun clearLocalDB(context: Context) {
            executor.execute {
                CardDatabase.getInstance(context).cardDao.let {
                    it.delDecks()
                    it.delCards()
                }
            }

        }
    }

}
