package si.feri.maintenance.lib.mappers

import si.feri.maintenance.lib.domain.Car
import si.feri.maintenance.lib.domain.Technician
import si.feri.maintenance.lib.entities.TechnicianEntity
import java.time.Instant

object TechnicianMapper {

    fun toDomain(
        e: TechnicianEntity,
        car: Car?
    ) = Technician(
        name = e.name,
        surname = e.surname,
        phoneNumber = e.phoneNumber,
        car = car,
        id = e.id
    )

    fun toEntity(d: Technician) = TechnicianEntity(
        id = d.id,
        timeCreated = Instant.now(),   // DB will override on insert
        timeDeleted = null,
        name = d.name,
        surname = d.surname,
        phoneNumber = d.phoneNumber,
        password = "",                // handled elsewhere
        privilege = 0
    )
}
