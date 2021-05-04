package es.uam.eps.dadm.cards

import android.view.View
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDateTime
import java.util.*
import kotlin.math.roundToLong

@Entity(tableName = "cards_table")
open class Card(
    @ColumnInfo(name = "cards_table")
    var question: String,
    var answer: String,
    var date: String = LocalDateTime.now().toString(),
    @PrimaryKey
    var id: String = UUID.randomUUID().toString(),
    var repetitions: Int = 0,
    var interval: Long = 1L,
    var nextPracticeDate: String = date,
    var easiness: Double = 2.5,
    var answered: Boolean = false

) {
    var quality = -1
    var shortDate : String = ""
        get() = date.substring(0,10)
    var shortNextDate : String = ""
        get() = nextPracticeDate.substring(0,10)

    companion object {
        fun fromString(str: String) : Card {
            val data = str.split("|")
            return Card(
                question = data[1].trim(),
                answer = data[2].trim(),
                date = data[3].trim(),
                id = data[4].trim(),
                easiness = data[5].trim().toDouble(),
                repetitions = data[6].trim().toInt(),
                interval = data[7].trim().toLong(),
                nextPracticeDate = data[8].trim(),
            )

        }
    }

    open fun show() {
        var dif = -1

        print(" $question")
        print(" (INTRO para ver respuesta)")
        readLine()
        print(" $answer")
        while (dif !in listOf(0, 3, 5)) {
            print("(Teclea 0 -> Difícil 3 -> Dudo 5 -> Fácil): ")
            dif = readLine()?.toIntOrNull() ?: -1
        }
        quality = dif


    }

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
        nextPracticeDate = currentDate.plusDays(interval).toString()
    }

    fun details() {
        val nextPracticeDateFormat =
            LocalDateTime.parse(nextPracticeDate).toLocalDate()//format(DateTimeFormatter.ofPattern("yyyy - MM - dd"))
        println("eas = ${"%.2f".format(easiness)} rep = $repetitions int = $interval next = $nextPracticeDateFormat")
    }
    fun info(): String {
        val nextPracticeDateFormat =
            LocalDateTime.parse(nextPracticeDate).toLocalDate()//format(DateTimeFormatter.ofPattern("yyyy - MM - dd"))
        return "eas = ${"%.2f".format(easiness)} rep = $repetitions int = $interval next = $nextPracticeDateFormat"
    }

    fun simulate(period: Long) {
        println("Simulación de la tarjeta $question:")
        var now = LocalDateTime.now()

        for (day in 0..period) {
            println(now.toLocalDate())
            if (LocalDateTime.parse(nextPracticeDate) <= now) {
                show()
                update(now)
                details()
            }
            now = now.plusDays(1)
        }
    }

    fun update_from_view(view: View) {
        quality = when(view.id) {
            R.id.easy_button -> 5
            R.id.doubt_button -> 3
            R.id.difficult_button -> 0
            else -> throw Exception("Unavailable quality")
        }
        update(LocalDateTime.now())
    }

    override fun toString(): String {
        return "card | $question | $answer | $date | $id | $easiness | $repetitions | $interval | $nextPracticeDate\n"
    }
    fun isDue(date:LocalDateTime): Boolean {
        val next = LocalDateTime.parse(nextPracticeDate)
        val result = date.compareTo(next)
        return result >= 0
    }

    fun update_easy() {
        quality = 5
        update(LocalDateTime.now())
    }
    fun update_doubt() {
        quality = 3
        update(LocalDateTime.now())
    }
    fun update_difficult() {
        quality = 0
        update(LocalDateTime.now())
    }

}