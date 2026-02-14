package si.feri.maintenance.lib.entities

import java.time.Instant
import java.util.UUID

class BuildingMaterialEntity(
    id: UUID,
    timeCreated: Instant,
    timeDeleted: Instant?,
    val materialId: UUID,
    val buildingId: UUID,
    val count: Int
) : BaseEntity(id, timeCreated, timeDeleted)
