package si.feri.maintenance.lib.mappers

import si.feri.maintenance.lib.domain.Car
import si.feri.maintenance.lib.entities.CarEntity
import java.time.Instant
import java.util.UUID

object CarMapper {

    fun toDomain(e: CarEntity) = Car(
        licencePlate = e.licencePlate,
        kmCount = e.kmCount,
        id = e.id
    )

    fun toEntity(
        d: Car,
        technicianId: UUID
    ) = CarEntity(
        id = d.id,
        timeCreated = Instant.now(),
        timeDeleted = null,
        licencePlate = d.licencePlate,
        kmCount = d.kmCount ?: 0.0,
        technicianId = technicianId
    )
}
