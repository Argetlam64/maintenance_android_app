package si.feri.maintenance.lib.repositories

import si.feri.maintenance.lib.domain.Task
import java.util.UUID

interface TaskRepository {
    //fun getByStoreId(id: UUID): MutableList<Task>
    //fun getById(id: UUID): Task?
    suspend fun add(storeId: UUID, task: Task)
    suspend fun delete(taskId: UUID)
    suspend fun update(task: Task): Task?
}