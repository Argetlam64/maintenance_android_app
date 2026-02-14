package si.feri.maintenance.lib.entities

import java.time.Instant
import java.util.UUID

class StoreTypeEntity(
    id: UUID,
    timeCreated: Instant,
    timeDeleted: Instant?,
    val value: String
) : BaseEntity(id, timeCreated, timeDeleted)
