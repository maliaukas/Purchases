package maliauka.sasha.db.room

import androidx.room.*
import kotlinx.coroutines.flow.Flow
import maliauka.sasha.model.Purchase

@Dao
interface PurchaseDao {

    @Query("delete from purchases")
    suspend fun deleteAll()

    @Insert
    suspend fun insert(p: Purchase)

    @Update
    suspend fun update(p: Purchase)

    @Delete
    suspend fun delete(p: Purchase)

    @Query("select * from purchases")
    fun getAll(): Flow<List<Purchase>>

    fun getAllSorted(column: String, ascending: Boolean): Flow<List<Purchase>> {
        return when ((column to ascending)) {
            "p_name" to true -> getAllSortedByNameAsc()
            "p_name" to false -> getAllSortedByNameDesc()
            "p_cost" to true -> getAllSortedByCostAsc()
            "p_cost" to false -> getAllSortedByCostDesc()
            "p_date" to true -> getAllSortedByDateAsc()
            "p_date" to false -> getAllSortedByDateDesc()
            else -> throw IllegalArgumentException("Wrong sorting parameters!")
        }
    }

    @Query("select * from purchases order by p_name desc")
    fun getAllSortedByNameDesc(): Flow<List<Purchase>>

    @Query("select * from purchases order by p_name asc")
    fun getAllSortedByNameAsc(): Flow<List<Purchase>>

    @Query("select * from purchases order by p_cost desc")
    fun getAllSortedByCostDesc(): Flow<List<Purchase>>

    @Query("select * from purchases order by p_cost asc")
    fun getAllSortedByCostAsc(): Flow<List<Purchase>>

    @Query("select * from purchases order by p_date desc")
    fun getAllSortedByDateDesc(): Flow<List<Purchase>>

    @Query("select * from purchases order by p_date asc")
    fun getAllSortedByDateAsc(): Flow<List<Purchase>>
}