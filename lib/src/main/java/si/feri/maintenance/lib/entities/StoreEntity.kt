package si.feri.maintenance.lib.entities

import java.time.Instant
import java.util.UUID

class StoreEntity(
    id: UUID,
    timeCreated: Instant,
    timeDeleted: Instant?,
    val name: String,
    val storeTypeId: UUID,
    val buildingId: UUID
) : BaseEntity(id, timeCreated, timeDeleted)
