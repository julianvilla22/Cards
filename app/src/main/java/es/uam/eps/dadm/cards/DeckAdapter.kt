package es.uam.eps.dadm.cards

import android.app.AlertDialog
import android.content.DialogInterface
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import es.uam.eps.dadm.cards.database.CardDatabase
import es.uam.eps.dadm.cards.databinding.ListItemDeckBinding
import java.util.concurrent.Executors

class DeckAdapter() : RecyclerView.Adapter<DeckAdapter.DeckHolder>() {
    lateinit var binding: ListItemDeckBinding
    private val executor = Executors.newSingleThreadExecutor()

    var data = listOf<DeckWithCards>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    inner class DeckHolder(view: View) : RecyclerView.ViewHolder(view) {
        lateinit var deck: Deck
        fun bind(dc: DeckWithCards) {
            binding.deck = dc.deck
            this.deck = dc.deck
        }

        init {
            view.setOnClickListener {
                val id = deck.id
                it.findNavController()
                    .navigate(
                        DeckListFragmentDirections
                            .actionDeckListFragmentToCardListFragment(id)
                    )
            }
            binding.deleteDeckButton.setOnClickListener {
                var dialogo = AlertDialog.Builder(view.context)
                dialogo.setTitle(R.string.card_delete_window_title)
                dialogo.setMessage(R.string.deck_delete_window_text)
                dialogo.setPositiveButton(R.string.card_delete_window_confirm) { _: DialogInterface, _: Int ->
                    notifyItemRemoved(bindingAdapterPosition)
                    executor.execute {
                        CardDatabase.getInstance(view.context).cardDao.delDeck(deck)
                        CardDatabase.getInstance(view.context).cardDao.delDeckCards(deck.id)
                    }
                }
                dialogo.setNegativeButton(R.string.card_delete_window_cancel) { _: DialogInterface, _: Int -> }
                dialogo.show()

            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): DeckHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        binding = ListItemDeckBinding.inflate(layoutInflater, parent, false)
        return DeckHolder(binding.root)
    }

    override fun getItemCount() = data.size

    override fun onBindViewHolder(holder: DeckHolder, position: Int) {
        holder.bind(data[position])
    }
}