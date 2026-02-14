package si.feri.maintenance.lib.remote

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import si.feri.maintenance.lib.remote.dto.TaskCreateDto
import si.feri.maintenance.lib.remote.dto.TaskDto

interface TaskApi {

    @POST("tasks/")
    suspend fun createTask(
        @Body task: TaskCreateDto
    ): TaskDto

    @PUT("tasks/{id}")
    suspend fun updateTask(
        @Path("id") id: String,
        @Body task: TaskDto
    ): TaskDto

    @DELETE("tasks/{id}")
    suspend fun deleteTask(
        @Path("id") id: String
    ): Response<Unit>
}