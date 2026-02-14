package si.feri.maintenance.lib.domain

import java.time.Instant
import java.util.UUID

class Store (
    val name: String,
    val storeType: StoreType,
    val id: UUID = UUID.randomUUID(),
    val tasks: MutableList<Task> = mutableListOf<Task>()
) {

    fun addTask(taskName: String,
                taskDescription: String,
                deadline: Instant? = null,
                finished: Boolean = false,
                id: UUID = UUID.randomUUID()){
        tasks.add(Task(taskName, taskDescription, deadline, Instant.now(), finished, id))
    }

    fun removeTask(taskId: UUID){
        tasks.removeIf { it.id == taskId }
    }

    fun toggleTaskCompleted(taskId: UUID){
        for(task in tasks){
            if(task.id == taskId){
                task.toggleFinished()
            }
        }
    }

    fun getUnfinishedTaskCount(): Int{
        var count = 0
        for(task in tasks){
            if(!task.finished){
                count++
            }
        }
        return count
    }
}