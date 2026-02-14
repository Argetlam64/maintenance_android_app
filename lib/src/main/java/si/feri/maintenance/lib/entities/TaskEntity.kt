package si.feri.maintenance.lib.entities

import java.time.Instant
import java.util.UUID

class TaskEntity(
    id: UUID,
    timeCreated: Instant,
    timeDeleted: Instant?,
    val title: String,
    val description: String?,
    val finished: Boolean,
    val deadline: Instant?,
    val storeId: UUID
) : BaseEntity(id, timeCreated, timeDeleted)
