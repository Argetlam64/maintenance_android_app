package si.feri.maintenance.lib.domain

import java.util.UUID

class Car (
    val licencePlate: String,
    var kmCount: Double? = null,
    val id: UUID = UUID.randomUUID()
){
    fun changeKmCount(newCount: Double){
        kmCount = newCount
    }
}