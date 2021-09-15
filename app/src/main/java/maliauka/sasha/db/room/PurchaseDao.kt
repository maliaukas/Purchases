package maliauka.sasha.db.room

import androidx.room.*
import kotlinx.coroutines.flow.Flow
import maliauka.sasha.db.costCol
import maliauka.sasha.db.databaseName
import maliauka.sasha.db.dateCol
import maliauka.sasha.db.nameCol
import maliauka.sasha.model.Purchase

@Dao
interface PurchaseDao {

    @Query("delete from $databaseName")
    suspend fun deleteAll()

    @Insert
    suspend fun insert(p: Purchase)

    @Update
    suspend fun update(p: Purchase)

    @Delete
    suspend fun delete(p: Purchase)

    @Query("select * from $databaseName")
    fun getAll(): Flow<List<Purchase>>

    fun getAllSorted(column: String, ascending: Boolean): Flow<List<Purchase>> {
        return when ((column to ascending)) {
            nameCol to true -> getAllSortedByNameAsc()
            nameCol to false -> getAllSortedByNameDesc()
            costCol to true -> getAllSortedByCostAsc()
            costCol to false -> getAllSortedByCostDesc()
            dateCol to true -> getAllSortedByDateAsc()
            dateCol to false -> getAllSortedByDateDesc()
            else -> throw IllegalArgumentException("Wrong sorting parameters!")
        }
    }

    @Query("select * from $databaseName order by $nameCol desc")
    fun getAllSortedByNameDesc(): Flow<List<Purchase>>

    @Query("select * from $databaseName order by $nameCol asc")
    fun getAllSortedByNameAsc(): Flow<List<Purchase>>

    @Query("select * from $databaseName order by $costCol desc")
    fun getAllSortedByCostDesc(): Flow<List<Purchase>>

    @Query("select * from $databaseName order by $costCol asc")
    fun getAllSortedByCostAsc(): Flow<List<Purchase>>

    @Query("select * from $databaseName order by $dateCol desc")
    fun getAllSortedByDateDesc(): Flow<List<Purchase>>

    @Query("select * from $databaseName order by $dateCol asc")
    fun getAllSortedByDateAsc(): Flow<List<Purchase>>
}