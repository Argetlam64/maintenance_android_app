package si.feri.maintenance.lib.domain

import java.time.Instant
import java.util.UUID

class Material (
    val name: String,
    val manufacturer: String? = null,
    val dateAdded: Instant = Instant.now(),
    val id: UUID = UUID.randomUUID()
){

}