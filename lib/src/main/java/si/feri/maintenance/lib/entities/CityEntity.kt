package si.feri.maintenance.lib.entities

import java.time.Instant
import java.util.UUID

class CityEntity(
    id: UUID,
    timeCreated: Instant,
    timeDeleted: Instant?,
    val name: String,
    val postalNumberId: UUID
) : BaseEntity(id, timeCreated, timeDeleted)