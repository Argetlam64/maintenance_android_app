package si.feri.maintenance.lib.repositories

import si.feri.maintenance.lib.domain.Material
import java.util.UUID

interface MaterialRepository {
    fun getById(id: UUID): Material?
    fun getByBuilding(id: UUID): MutableList<Material>
    fun getAll(): MutableList<Material>
}