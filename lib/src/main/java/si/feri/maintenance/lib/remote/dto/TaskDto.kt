package si.feri.maintenance.lib.remote.dto

import si.feri.maintenance.lib.domain.Task
import java.util.UUID
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException

fun instantFromMysqlString(value: String?): Instant? {
    if (value == null) {
        return null
    }
    return try {
        // Try parsing the standard ISO 8601 format first
        Instant.parse(value)
    } catch (e: DateTimeParseException) {
        // If that fails, fall back to the custom MySQL format
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
        val localDateTime = LocalDateTime.parse(value, formatter)
        localDateTime.toInstant(ZoneOffset.UTC)
    }
}

data class TaskDto (
    val id: UUID,
    val title: String,
    val description: String?,
    val finished: Int,
    val deadline: String?
){
    fun toDomain(): Task?{
        var fin = false
        if(finished > 0){
            fin = true
        }
        return Task(
            title,
            description,
            instantFromMysqlString(deadline),
            finished = fin,
            id = id
        )
    }
}