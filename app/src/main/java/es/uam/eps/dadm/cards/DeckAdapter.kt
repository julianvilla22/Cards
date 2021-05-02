package es.uam.eps.dadm.cards

import android.app.AlertDialog
import android.content.DialogInterface
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import es.uam.eps.dadm.cards.databinding.ListItemDeckBinding

class DeckAdapter() : RecyclerView.Adapter<DeckAdapter.DeckHolder>() {
    lateinit var binding: ListItemDeckBinding

    var data = listOf<Deck>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    inner class DeckHolder(view: View) : RecyclerView.ViewHolder(view) {
        lateinit var deck: Deck
        fun bind(deck: Deck) {
            binding.deck = deck
            this.deck=deck
        }
        init {
            view.setOnClickListener {
                val id = deck.id
                it.findNavController()
                    .navigate(DeckListFragmentDirections
                        .actionDeckListFragmentToCardListFragment(id))
            }
            binding.deleteDeckButton.setOnClickListener{
                var dialogo = AlertDialog.Builder(view.context)
                dialogo.setTitle(R.string.card_delete_window_title)
                dialogo.setMessage(R.string.deck_delete_window_text)
                dialogo.setPositiveButton(R.string.card_delete_window_confirm){ _: DialogInterface, _: Int ->
                    CardsApplication.delDeck(deck)
                    notifyItemRemoved(bindingAdapterPosition)
                }
                dialogo.setNegativeButton(R.string.card_delete_window_cancel){ _: DialogInterface, _: Int ->}
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