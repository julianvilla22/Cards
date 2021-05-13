package es.uam.eps.dadm.cards

import java.time.LocalDateTime
import java.util.*

class Cloze(
    question: String,
    answer: String,
    date: String = LocalDateTime.now().toString(),
    id: String = UUID.randomUUID().toString(),
    repetitions: Int = 0,
    interval: Long = 1L,
    nextPracticeDate: String = date,
    easiness: Double = 2.5,
    deckId: Long,
    lastDate: String
) :
    Card(question=question, answer=answer, date=date, id=id, repetitions=repetitions, interval=interval, nextPracticeDate=nextPracticeDate, easiness=easiness, deckId=deckId, lastDate = lastDate) {
    companion object {
        fun fromString(str: String) : Cloze {
            val data = str.split("|")
            return Cloze(
                question = data[1].trim(),
                answer = data[2].trim(),
                date = data[3].trim(),
                id = data[4].trim(),
                easiness = data[5].trim().toDouble(),
                repetitions = data[6].trim().toInt(),
                interval = data[7].trim().toLong(),
                nextPracticeDate = data[8].trim(),
                deckId = data[9].trim().toLong(),
                lastDate = data[10].trim(),
            )
        }
    }

    override fun show() {
        var dif = -1

        print(" $question")
        print(" (INTRO para ver respuesta)")
        readLine()
        var splitted = question.split("*")
        print(" ${splitted[0]}$answer${splitted[2]}")
        while (dif !in listOf(0, 3, 5)) {
            print("(Teclea 0 -> Difícil 3 -> Dudo 5 -> Fácil): ")
            dif = readLine()?.toIntOrNull() ?: -1
        }
        quality = dif
    }

    override fun toString(): String {
        return "cloze | $question | $answer | $date | $id | $easiness | $repetitions | $interval | $nextPracticeDate | $deckId | $lastDate\n"
    }

}