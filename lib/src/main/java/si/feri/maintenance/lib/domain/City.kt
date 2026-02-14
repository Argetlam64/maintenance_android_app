package si.feri.maintenance.lib.domain

import java.util.UUID

class City(
    val name: String,
    val postalCode: PostalCode,
    val id: UUID = UUID.randomUUID()
) {

}