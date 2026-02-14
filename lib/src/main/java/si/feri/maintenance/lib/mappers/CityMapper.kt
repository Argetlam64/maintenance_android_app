package si.feri.maintenance.lib.mappers

import si.feri.maintenance.lib.domain.City
import si.feri.maintenance.lib.domain.PostalCode
import si.feri.maintenance.lib.entities.CityEntity

object CityMapper {
    fun toDomain(
        e: CityEntity,
        postalCode: PostalCode
    ) = City(
        name = e.name,
        postalCode = postalCode,
        id = e.id
    )
}
