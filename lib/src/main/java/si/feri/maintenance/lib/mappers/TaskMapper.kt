package si.feri.maintenance.lib.mappers

import si.feri.maintenance.lib.domain.Task
import si.feri.maintenance.lib.entities.TaskEntity

object TaskMapper {
    fun toDomain(e: TaskEntity) =
        Task(
            title = e.title,
            description = e.description ?: "",
            deadline = e.deadline,
            finished = e.finished,
            id = e.id
        )
}
