package maliauka.sasha.db.room

import android.content.Context
import android.util.Log
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import maliauka.sasha.db.databaseName
import maliauka.sasha.db.randomDate
import maliauka.sasha.db.randomName
import maliauka.sasha.model.Purchase
import kotlin.random.Random

@Database(entities = [Purchase::class], version = 1, exportSchema = false)
@TypeConverters(TypeConverter::class)
abstract class PurchaseDatabase : RoomDatabase() {
    abstract fun purchaseDao(): PurchaseDao

    companion object {

        @Volatile
        private var INSTANCE: PurchaseDatabase? = null

        fun getDatabase(context: Context, scope: CoroutineScope): PurchaseDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    PurchaseDatabase::class.java,
                    databaseName
                )
                    .fallbackToDestructiveMigration()
                    .addCallback(PurchaseDatabaseCallback(scope))
                    .setQueryCallback(PurchaseQueryCallback(), {
                        it.run()
                    })
                    .build()
                INSTANCE = instance

                instance
            }
        }
    }

    private class PurchaseQueryCallback : RoomDatabase.QueryCallback {
        override fun onQuery(sqlQuery: String, bindArgs: MutableList<Any>) {
            Log.d("SHAS", sqlQuery + "\n" + bindArgs.joinToString("\n"))
        }
    }

    private class PurchaseDatabaseCallback(
        private val scope: CoroutineScope
    ) : RoomDatabase.Callback() {

        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)
            INSTANCE?.let { database ->
                scope.launch(Dispatchers.IO) {
                    populateDatabase(database.purchaseDao())
                }
            }
        }

        suspend fun populateDatabase(dao: PurchaseDao) {
            dao.deleteAll()

            repeat(Random.nextInt(9, 15)) {
                dao.insert(
                    Purchase(
                        randomName(), randomDate(), Random.nextLong(1, 1000000)
                    )
                )
            }
        }
    }
}
