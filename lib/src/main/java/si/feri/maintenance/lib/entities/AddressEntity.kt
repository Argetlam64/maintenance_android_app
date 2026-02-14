package si.feri.maintenance.lib.entities

import java.time.Instant
import java.util.UUID

class AddressEntity(
    id: UUID,
    timeCreated: Instant,
    timeDeleted: Instant?,
    val streetName: String,
    val houseNumber: String?,
    val latitude: Double,
    val longitude: Double,
    val cityId: UUID
) : BaseEntity(id, timeCreated, timeDeleted)
