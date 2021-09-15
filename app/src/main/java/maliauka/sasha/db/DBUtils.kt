package maliauka.sasha.db

import kotlinx.datetime.LocalDate
import kotlin.random.Random

const val databaseName = "purchase_database"
const val tableName = "purchases"
const val costCol = "p_cost"
const val nameCol = "p_name"
const val dateCol = "p_date"
const val idCol = "_id"

fun randomDate(): LocalDate {
    return LocalDate(
        year = Random.nextInt(2000, 2021),
        monthNumber = Random.nextInt(1, 13),
        dayOfMonth = Random.nextInt(1, 27)
    )
}

private val names = listOf(
    "Spinach",
    "Pizza",
    "Ice Cream",
    "Spaghetti",
    "Grapes",
    "Apples",
    "Watermelon",
    "Toothpaste",
    "TV",
    "Guitar",
    "Books",
)

fun randomName(): String {
    return names.random()
}

private fun <T> List<T>.random(): T {
    return this[Random.nextInt(0, this.size)]
}

