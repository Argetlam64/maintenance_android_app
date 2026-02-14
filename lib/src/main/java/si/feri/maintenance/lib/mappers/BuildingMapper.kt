package si.feri.maintenance.lib.mappers

import si.feri.maintenance.lib.domain.Address
import si.feri.maintenance.lib.domain.Building
import si.feri.maintenance.lib.domain.Store
import si.feri.maintenance.lib.entities.BuildingEntity

object BuildingMapper {
    fun toDomain(
        e: BuildingEntity,
        address: Address,
        stores: List<Store>,
        materials: List<Building.MaterialInBuilding>
    ) = Building(
        name = e.name,
        address = address,
        stores = stores.toMutableList(),
        materials = materials.toMutableList(),
        id = e.id
    )
}