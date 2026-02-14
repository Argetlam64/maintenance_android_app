package si.feri.maintenance.lib.remote

import TechnicianFullDto
import retrofit2.http.GET
import retrofit2.http.Path
import si.feri.maintenance.lib.remote.dto.TechnicianInfoDto
import si.feri.maintenance.lib.remote.dto.TechnicianListDto

interface TechnicianApi {

    @GET("technicians/{id}/full")
    suspend fun getTechnicianFull(
        @Path("id") id: String
    ): TechnicianFullDto

    @GET("technicians/")
    suspend fun getAllTechnicians(): List<TechnicianInfoDto>
}