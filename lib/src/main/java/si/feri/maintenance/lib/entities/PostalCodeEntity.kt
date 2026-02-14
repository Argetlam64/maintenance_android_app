package si.feri.maintenance.lib.entities

import java.time.Instant
import java.util.UUID

class PostalCodeEntity(
    id: UUID,
    timeCreated: Instant,
    timeDeleted: Instant?,
    val value: String
) : BaseEntity(id, timeCreated, timeDeleted)