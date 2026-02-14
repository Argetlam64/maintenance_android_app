package si.feri.maintenance.lib.remote.repos

import TechnicianFullDto
import si.feri.maintenance.lib.remote.TechnicianApi
import si.feri.maintenance.lib.remote.dto.TechnicianInfoDto
import si.feri.maintenance.lib.remote.dto.TechnicianListDto
import si.feri.maintenance.lib.repositories.TechnicianRepository

class RemoteTechnicianRepository(
    private val api: TechnicianApi
) : TechnicianRepository {

    override suspend fun getFullTechnician(id: String): TechnicianFullDto {
        return api.getTechnicianFull(id)
    }

    override suspend fun getTechnicianList(): List<TechnicianInfoDto> {
        return api.getAllTechnicians()
    }
}