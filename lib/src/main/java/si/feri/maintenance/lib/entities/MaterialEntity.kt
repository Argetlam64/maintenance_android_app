package si.feri.maintenance.lib.entities

import java.time.Instant
import java.util.UUID

class MaterialEntity(
    id: UUID,
    timeCreated: Instant,
    timeDeleted: Instant?,
    val name: String,
    val manufacturerId: UUID
) : BaseEntity(id, timeCreated, timeDeleted)
