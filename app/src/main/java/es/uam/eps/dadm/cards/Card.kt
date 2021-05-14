package es.uam.eps.dadm.cards

import android.view.View
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDate
import java.time.LocalDateTime
import java.util.*
import kotlin.collections.HashMap
import kotlin.math.roundToLong

@Entity(tableName = "cards_table")
open class Card(
    @ColumnInfo(name = "cards_table")
    var question: String,
    var answer: String,
    var deckId: Long,
    @PrimaryKey
    var id: String = UUID.randomUUID().toString(),
    var date: String = LocalDateTime.now().toString()

) {
    var lastDate: String = LocalDateTime.now().minusDays(1).toString()
    var repetitions: Int = 0
    var interval: Long = 1L
    var nextPracticeDate: String = date
    var easiness: Double = 2.5
    var answered: Boolean = false
    var quality = -1
    var shortDate: String = ""
        get() = date.substring(0, 10)
    var shortNextDate: String = ""
        get() = nextPracticeDate.substring(0, 10)

    constructor() : this(
        "Pregunta",
        "Respuesta",
        100,
        LocalDateTime.now().toString(),
        UUID.randomUUID().toString()
    )

    fun update(currentDate: LocalDateTime) {
        var new = easiness + 0.1 - (5 - quality) * (0.08 + (5 - quality) * 0.02)
        easiness = if (new > 1.3)
            new
        else
            1.3

        repetitions = if (quality == 0)
            0
        else
            repetitions + 1

        interval = when (repetitions) {
            0, 1 -> 1
            2 -> 6L
            else -> (interval * easiness).roundToLong()
        }
        lastDate = currentDate.toString()
        nextPracticeDate = currentDate.plusDays(interval).toString()
    }

    override fun toString(): String {
        return "card | $question | $answer | $date | $id | $easiness | $repetitions | $interval | $nextPracticeDate | $deckId | $lastDate\n"
    }

    fun isDue(date: LocalDateTime): Boolean {
        val next = LocalDateTime.parse(nextPracticeDate)
        val result = date.compareTo(next)
        return result >= 0
    }

    fun studiedToday(): Boolean {
        val last = LocalDateTime.parse(lastDate).toLocalDate()
        val now = LocalDate.now()
        if (last.compareTo(now) == 0)
            return true
        return false
    }


}