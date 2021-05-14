package es.uam.eps.dadm.cards

import android.app.AlertDialog
import android.content.DialogInterface
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.RelativeLayout
import androidx.core.content.res.ResourcesCompat
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import es.uam.eps.dadm.cards.database.CardDatabase
import es.uam.eps.dadm.cards.databinding.ListItemCardBinding
import java.util.concurrent.Executors

class CardAdapter() : RecyclerView.Adapter<CardAdapter.CardHolder>() {
    lateinit var binding: ListItemCardBinding
    private val executor = Executors.newSingleThreadExecutor()

    var data = listOf<Card>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }


    inner class CardHolder(view: View) : RecyclerView.ViewHolder(view) {
        var extraInfo = false
        lateinit var card: Card
        fun bind(card: Card) {
            binding.card = card
            this.card = card
        }

        init {


            view.setOnClickListener {
                val id = card.id
                it.findNavController()
                    .navigate(
                        CardListFragmentDirections
                            .actionCardListFragmentToCardEditFragment(id, card.deckId)
                    )
            }
            binding.infoCardButton.setOnClickListener {
                if (extraInfo) {
                    view.findViewById<RelativeLayout>(R.id.optional_info_layout).visibility = GONE
                    view.findViewById<ImageButton>(R.id.info_card_button).background =
                        ResourcesCompat.getDrawable(
                            view.context.resources,
                            R.drawable.info_card,
                            null
                        )
                    extraInfo = false
                } else {
                    view.findViewById<RelativeLayout>(R.id.optional_info_layout).visibility =
                        VISIBLE
                    view.findViewById<ImageButton>(R.id.info_card_button).background =
                        ResourcesCompat.getDrawable(
                            view.context.resources,
                            R.drawable.info_card_selected,
                            null
                        )
                    extraInfo = true
                }
            }
            binding.deleteCardButton.setOnClickListener {
                var dialogo = AlertDialog.Builder(view.context)
                dialogo.setTitle(R.string.card_delete_window_title)
                dialogo.setMessage(R.string.card_delete_window_text)
                dialogo.setPositiveButton(R.string.card_delete_window_confirm) { _: DialogInterface, _: Int ->
                    executor.execute {
                        CardDatabase.getInstance(view.context).cardDao.delCard(card)
                    }
                    notifyItemRemoved(bindingAdapterPosition)
                }
                dialogo.setNegativeButton(R.string.card_delete_window_cancel) { _: DialogInterface, _: Int -> }
                dialogo.show()

            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CardHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        binding = ListItemCardBinding.inflate(layoutInflater, parent, false)
        return CardHolder(binding.root)
    }

    override fun getItemCount() = data.size

    override fun onBindViewHolder(holder: CardHolder, position: Int) {
        holder.bind(data[position])
    }
}