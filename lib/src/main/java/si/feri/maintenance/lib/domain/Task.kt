package si.feri.maintenance.lib.domain

import com.android.identity.documenttype.DocumentAttributeType
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.UUID

class Task (
    var title: String,
    var description: String? = "",
    var deadline: Instant? = null,
    val dateAdded: Instant = Instant.now(),
    var finished: Boolean = false,
    val id: UUID = UUID.randomUUID()
){
    val statusText = when {
        finished -> "DONE"
        isOverdue(Instant.now()) -> "OVERDUE"
        else -> "OPEN"
    }
    fun isOverdue(now: Instant): Boolean{
        return deadline != null && now.isAfter(deadline)
    }

    override fun toString(): String{
        return "$title:\n$description"
    }

    fun toggleFinished(){
        finished = !finished
    }

    fun dateToString(instant: Instant): String{
        val formatter = DateTimeFormatter
            .ofPattern("dd.MM.yyyy HH:mm")
            .withZone(ZoneId.systemDefault())

        return formatter.format(instant)
    }

    fun getDateString(): String{
        return dateToString(dateAdded)
    }

    fun getDeadlineString(): String{
        deadline?.let { return dateToString(it) }
        return "No deadline"
    }
}