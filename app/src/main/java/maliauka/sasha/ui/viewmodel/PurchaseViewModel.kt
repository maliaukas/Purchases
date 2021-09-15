package maliauka.sasha.ui.viewmodel

import androidx.lifecycle.*
import kotlinx.coroutines.launch
import maliauka.sasha.model.Purchase
import maliauka.sasha.db.PurchaseRepository

class PurchaseViewModel(private val repository: PurchaseRepository) : ViewModel() {
    private class TripleTrigger<A, B, C>(a: LiveData<A>, b: LiveData<B>, c: LiveData<C>) :
        MediatorLiveData<Triple<A?, B?, C?>>() {
        init {
            addSource(a) { value = Triple(it, b.value, c.value) }
            addSource(b) { value = Triple(a.value, it, c.value) }
            addSource(c) { value = Triple(a.value, b.value, it) }
        }
    }

    private val sortColumn = MutableLiveData<String>()

    private val sortOrder = MutableLiveData<Boolean>()

    val purchases: LiveData<List<Purchase>> =
        Transformations.switchMap(TripleTrigger(sortColumn, sortOrder, repository.notifyCursor)) {
            repository.getPurchasesSorted(it.first!!, it.second!!).asLiveData()
        }

    fun setSortColumn(column: String) {
        sortColumn.value = column
    }

    fun setSortOrder(order: Boolean) {
        sortOrder.value = order
    }

    fun setUseRoom(value: Boolean) {
        repository.useRoom = value
    }

    fun insert(p: Purchase) = viewModelScope.launch {
        repository.insert(p)
    }

    fun update(p: Purchase) = viewModelScope.launch {
        repository.update(p)
    }

    fun delete(p: Purchase) = viewModelScope.launch {
        repository.delete(p)
    }
}