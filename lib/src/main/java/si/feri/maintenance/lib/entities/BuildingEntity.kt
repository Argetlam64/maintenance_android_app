package si.feri.maintenance.lib.entities

import java.time.Instant
import java.util.UUID

class BuildingEntity(
    id: UUID,
    timeCreated: Instant,
    timeDeleted: Instant?,
    val name: String,
    val addressId: UUID,
    val technicianId: UUID
) : BaseEntity(id, timeCreated, timeDeleted)
