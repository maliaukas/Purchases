package maliauka.sasha.ui.list

import maliauka.sasha.model.Purchase

interface OnPurchaseClickListener {
    fun onClick(p: Purchase)
}