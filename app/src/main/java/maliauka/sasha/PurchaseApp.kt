package maliauka.sasha

import android.app.Application
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import maliauka.sasha.db.room.PurchaseDatabase
import maliauka.sasha.db.PurchaseRepository

class PurchaseApp : Application() {
    // No need to cancel this scope as it'll be torn down with the process
    val applicationScope = CoroutineScope(SupervisorJob())

    val database by lazy { PurchaseDatabase.getDatabase(this, applicationScope) }
    val repository by lazy { PurchaseRepository(database.purchaseDao(), this) }
}