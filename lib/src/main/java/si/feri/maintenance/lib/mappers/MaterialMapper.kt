package si.feri.maintenance.lib.mappers

import si.feri.maintenance.lib.domain.Material
import si.feri.maintenance.lib.entities.MaterialEntity

object MaterialMapper {
    fun toDomain(
        e: MaterialEntity,
        manufacturerName: String
    ) = Material(
        name = e.name,
        manufacturer = manufacturerName,
        id = e.id
    )
}