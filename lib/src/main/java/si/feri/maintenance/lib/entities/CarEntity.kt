package si.feri.maintenance.lib.entities

import java.time.Instant
import java.util.UUID

class CarEntity(
    id: UUID,
    timeCreated: Instant,
    timeDeleted: Instant?,
    val licencePlate: String,
    val kmCount: Double,
    val technicianId: UUID
) : BaseEntity(id, timeCreated, timeDeleted)
