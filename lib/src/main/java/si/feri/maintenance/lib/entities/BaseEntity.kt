package si.feri.maintenance.lib.entities

import java.time.Instant
import java.util.UUID

abstract class BaseEntity(
    val id: UUID,
    val timeCreated: Instant,
    val timeDeleted: Instant?
)