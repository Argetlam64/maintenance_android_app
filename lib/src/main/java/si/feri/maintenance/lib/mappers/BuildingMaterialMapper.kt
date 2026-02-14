package si.feri.maintenance.lib.mappers

import si.feri.maintenance.lib.domain.Building
import si.feri.maintenance.lib.domain.Material

object BuildingMaterialMapper {
    fun toDomain(
        material: Material,
        count: Int
    ) = Building.MaterialInBuilding(
        material = material,
        count = count
    )
}