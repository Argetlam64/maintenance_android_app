package si.feri.maintenance.lib.remote.repos

import si.feri.maintenance.lib.domain.Task
import si.feri.maintenance.lib.remote.TaskApi
import si.feri.maintenance.lib.remote.dto.TaskCreateDto
import si.feri.maintenance.lib.remote.dto.TaskDto
import si.feri.maintenance.lib.repositories.TaskRepository
import java.time.Instant
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter
import java.util.UUID

fun formatInstantForMysql(instant: Instant): String {
    return DateTimeFormatter
        .ofPattern("yyyy-MM-dd HH:mm:ss")
        .withZone(ZoneOffset.UTC)
        .format(instant)
}

class RemoteTaskRepository(
    private val api: TaskApi
) : TaskRepository {

    override suspend fun add(storeId: UUID, task: Task) {
        val dto = TaskCreateDto(
            title = task.title,
            description = task.description,
            finished = if (task.finished) 1 else 0,
            deadline = task.deadline?.let { formatInstantForMysql(it) },
            store_id = storeId.toString()
        )

        api.createTask(dto)
    }

    override suspend fun delete(taskId: UUID) {
        api.deleteTask(taskId.toString())
    }

    override suspend fun update(task: Task): Task? {
        val dto = TaskDto(
            id = task.id,
            title = task.title,
            description = task.description,
            finished = if (task.finished) 1 else 0,
            deadline = task.deadline?.let { formatInstantForMysql(it) }
        )

        val updated: TaskDto = api.updateTask(task.id.toString(), dto)
        return updated.toDomain()
    }
}
