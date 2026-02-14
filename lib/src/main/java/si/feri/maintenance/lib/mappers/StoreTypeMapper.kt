package si.feri.maintenance.lib.mappers

import si.feri.maintenance.lib.domain.StoreType
import si.feri.maintenance.lib.entities.StoreTypeEntity

object StoreTypeMapper {
    fun toDomain(e: StoreTypeEntity) =
        StoreType(
            value = e.value,
            id = e.id
        )
}
