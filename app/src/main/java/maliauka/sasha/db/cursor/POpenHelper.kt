package maliauka.sasha.db.cursor

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import kotlinx.datetime.LocalDate
import maliauka.sasha.db.*
import maliauka.sasha.model.Purchase

class POpenHelper(context: Context) :
    SQLiteOpenHelper(context, databaseName, null, 1) {

    override fun onCreate(p0: SQLiteDatabase?) {
    }

    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {
    }

    fun delete(p: Purchase) {
        writableDatabase.delete(
            tableName,
            idCol + "=" + p.id.toString(),
            null
        )
    }

    private fun getCv(p: Purchase): ContentValues {
        val cv = ContentValues()
        cv.put(costCol, p.cost)
        cv.put(nameCol, p.name)
        cv.put(dateCol, p.date.toString())
        return cv
    }

    fun insert(p: Purchase) {
        val cv = getCv(p)
        writableDatabase.insert(tableName, null, cv)
    }

    fun update(p: Purchase) {
        val cv = getCv(p)
        writableDatabase.update(
            tableName,
            cv,
            idCol + "=" + p.id.toString(),
            null
        )
    }

    fun getAllSorted(column: String, ascending: Boolean): List<Purchase> {
        val cursor = readableDatabase.query(
            tableName,
            arrayOf(
                idCol,
                costCol,
                dateCol,
                nameCol
            ),
            null,
            null,
            null,
            null,
            column + if (ascending) " ASC" else " DESC"
        )

        val list = mutableListOf<Purchase>()

        val idIdx = cursor.getColumnIndex(idCol)
        val nameIdx = cursor.getColumnIndex(nameCol)
        val costIdx = cursor.getColumnIndex(costCol)
        val dateIdx = cursor.getColumnIndex(dateCol)

        if (cursor.moveToFirst()) {
            do {
                val id = cursor.getInt(idIdx)
                val name = cursor.getString(nameIdx)
                val cost = cursor.getLong(costIdx)
                val date = LocalDate.parse(cursor.getString(dateIdx))

                val p = Purchase(name, date, cost)
                p.id = id

                list.add(p)
            } while (cursor.moveToNext())
        }

        cursor.close()

        return list
    }
}