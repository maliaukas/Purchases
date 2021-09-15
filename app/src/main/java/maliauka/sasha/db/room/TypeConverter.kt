package maliauka.sasha.db.room

import androidx.room.TypeConverter
import kotlinx.datetime.LocalDate

class TypeConverter {
    @TypeConverter
    fun toLocalDate(value: String?): LocalDate? {
        return value?.let {
            return LocalDate.parse(value)
        }
    }

    @TypeConverter
    fun fromLocalDate(date: LocalDate?): String? {
        return date?.toString()
    }
}