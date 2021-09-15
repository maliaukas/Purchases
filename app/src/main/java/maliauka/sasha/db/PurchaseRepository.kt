package maliauka.sasha.db

import android.app.Application
import androidx.annotation.WorkerThread
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import maliauka.sasha.db.cursor.PurchaseOpenHelper
import maliauka.sasha.db.room.PurchaseDao
import maliauka.sasha.model.Purchase


class PurchaseRepository(
    private val purchaseDao: PurchaseDao,
    private val application: Application,
    private val dbHelper: PurchaseOpenHelper = PurchaseOpenHelper(application)
) {
    val notifyCursor = MutableLiveData(true)
    var useRoom = true

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insert(p: Purchase) {
        if (useRoom) {
            purchaseDao.insert(p)
        } else {
            dbHelper.insert(p)
        }
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun update(p: Purchase) {
        if (useRoom) {
            purchaseDao.update(p)
        } else {
            dbHelper.update(p)
            notifyCursor.postValue(!(notifyCursor.value!!))
        }
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun delete(p: Purchase) {
        if (useRoom) {
            purchaseDao.delete(p)
        } else {
            dbHelper.delete(p)
            notifyCursor.postValue(!(notifyCursor.value!!))
        }
    }

    fun getPurchasesSorted(column: String, ascending: Boolean): Flow<List<Purchase>> {
        return if (useRoom) {
            purchaseDao.getAllSorted(column, ascending)
        } else {
            flowOf(dbHelper.getAllSorted(column, ascending))
        }
    }
}