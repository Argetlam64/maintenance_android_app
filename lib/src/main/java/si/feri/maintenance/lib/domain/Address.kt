package si.feri.maintenance.lib.domain

import java.util.UUID

class Address(
    val city: City,
    val streetName: String,
    val latitude: Double,
    val longitude: Double,
    val houseNumber: String? = null,
    val id: UUID = UUID.randomUUID()
){
    override fun toString(): String {
        return "$streetName $houseNumber, ${city.postalCode.value} ${city.name}"
    }


}