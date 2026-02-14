package si.feri.maintenance.lib.mappers

import si.feri.maintenance.lib.domain.PostalCode
import si.feri.maintenance.lib.entities.PostalCodeEntity

object PostalCodeMapper {
    fun toDomain(e: PostalCodeEntity) =
        PostalCode(
            value = e.value,
            id = e.id
        )
}