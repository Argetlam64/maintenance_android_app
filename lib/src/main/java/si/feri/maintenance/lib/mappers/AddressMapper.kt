package si.feri.maintenance.lib.mappers

import si.feri.maintenance.lib.domain.Address
import si.feri.maintenance.lib.domain.City
import si.feri.maintenance.lib.entities.AddressEntity

object AddressMapper {
    fun toDomain(
        e: AddressEntity,
        city: City
    ) = Address(
        city = city,
        streetName = e.streetName,
        houseNumber = e.houseNumber,
        latitude = e.latitude,
        longitude = e.longitude,
        id = e.id
    )
}
