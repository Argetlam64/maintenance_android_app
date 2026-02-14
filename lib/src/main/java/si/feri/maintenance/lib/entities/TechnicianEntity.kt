package si.feri.maintenance.lib.entities

import java.time.Instant
import java.util.UUID

class TechnicianEntity(
    id: UUID,
    timeCreated: Instant,
    timeDeleted: Instant?,
    val name: String,
    val surname: String,
    val phoneNumber: String,
    val password: String,
    val privilege: Int
) : BaseEntity(id, timeCreated, timeDeleted)
