package si.feri.maintenance.lib.entities

import java.time.Instant
import java.util.UUID

class ManufacturerEntity(
    id: UUID,
    timeCreated: Instant,
    timeDeleted: Instant?,
    val name: String
) : BaseEntity(id, timeCreated, timeDeleted)