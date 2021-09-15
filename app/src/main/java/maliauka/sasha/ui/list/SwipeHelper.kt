package maliauka.sasha.ui.list

import androidx.recyclerview.widget.ItemTouchHelper
import maliauka.sasha.model.Purchase


class SwipeHelper(onSwiped: (Purchase) -> Unit) : ItemTouchHelper(SwipeCallback(onSwiped))