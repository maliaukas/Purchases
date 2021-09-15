package maliauka.sasha.ui.list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import maliauka.sasha.databinding.ListItemBinding
import maliauka.sasha.model.Purchase


class PurchaseAdapter(private val clickListener: OnPurchaseClickListener) :
    ListAdapter<Purchase, PurchaseViewHolder>(itemComparator) {

    /* Creates and inflates view and return PurchaseViewHolder. */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PurchaseViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ListItemBinding.inflate(layoutInflater, parent, false)
        return PurchaseViewHolder(binding)
    }

    /* Gets current Purchase and uses it to bind view. */
    override fun onBindViewHolder(holder: PurchaseViewHolder, position: Int) {
        val p = getItem(position)
        holder.bind(p)

        holder.itemView.setOnLongClickListener {
            clickListener.onClick(p)
            true
        }
    }

    private companion object {

        private val itemComparator = object : DiffUtil.ItemCallback<Purchase>() {
            override fun areItemsTheSame(oldItem: Purchase, newItem: Purchase): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Purchase, newItem: Purchase): Boolean {
                return oldItem == newItem
            }
        }
    }
}