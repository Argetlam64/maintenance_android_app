package si.feri.maintenance.lib.repositories

import si.feri.maintenance.lib.domain.Building
import java.util.UUID

interface BuildingRepository {
    fun getById(id: UUID): Building?
    fun getByTechnician(id: UUID): MutableList<Building>
    fun getByStoreId(id: UUID): MutableList<Building>
}