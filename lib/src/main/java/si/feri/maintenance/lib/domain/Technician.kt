package si.feri.maintenance.lib.domain

import java.util.UUID

class Technician(
    val name: String,
    val surname: String,
    val buildings: MutableList<Building> = mutableListOf(),
    val phoneNumber: String,
    val car: Car? = null,
    val privilege: Int = 0,
    val id: UUID = UUID.randomUUID()
){
    fun fullName(): String {
        return "$name $surname"
    }

    fun addBuilding(building: Building){
        buildings.add(building)
    }

    fun removeBuilding(buildingId: UUID){
        buildings.removeIf { it.id == buildingId }
    }

    fun getStores() : MutableList<Store>{
        val stores: MutableList<Store> = mutableListOf()
        for(building in buildings){
            for(store in building.stores){
                stores.add(store)
            }
        }
        return stores
    }
}