package maliauka.sasha.ui.list

import androidx.recyclerview.widget.RecyclerView
import maliauka.sasha.databinding.ListItemBinding
import maliauka.sasha.model.Purchase

/* ViewHolder for Purchase, takes in the inflated view and the onClick behavior. */

class PurchaseViewHolder(
    private val binding: ListItemBinding,
) : RecyclerView.ViewHolder(binding.root) {

    var currentPurchase: Purchase? = null
        private set

    /* Bind Purchase data. */
    fun bind(purchase: Purchase) {
        currentPurchase = purchase

        with(binding) {
            name.text = purchase.name
            date.text = purchase.date.toString()
            cost.text = "${purchase.cost}"
        }
    }
}