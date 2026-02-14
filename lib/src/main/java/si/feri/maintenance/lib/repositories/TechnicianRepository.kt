package si.feri.maintenance.lib.repositories

import TechnicianFullDto
import si.feri.maintenance.lib.domain.Technician
import si.feri.maintenance.lib.remote.dto.TechnicianInfoDto
import si.feri.maintenance.lib.remote.dto.TechnicianListDto
import java.util.UUID

interface TechnicianRepository {
    //fun getById(id: UUID): Technician?
    //fun getAll(): MutableList<Technician>
    suspend fun getFullTechnician(id: String): TechnicianFullDto
    suspend fun getTechnicianList(): List<TechnicianInfoDto>
}