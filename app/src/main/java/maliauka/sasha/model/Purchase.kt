package maliauka.sasha.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.datetime.LocalDate
import maliauka.sasha.db.*
import org.jetbrains.annotations.NotNull

@Entity(tableName = tableName)
data class Purchase(

    @NotNull
    @ColumnInfo(name = nameCol)
    val name: String,

    @NotNull
    @ColumnInfo(name = dateCol)
    val date: LocalDate,

    @NotNull
    @ColumnInfo(name = costCol)
    val cost: Long
) {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = idCol)
    var id: Int = 0
}