package si.feri.maintenance.lib.domain

import java.util.UUID



class Building (
    val name: String,
    val address: Address,
    val stores: MutableList<Store> = mutableListOf(),
    val materials: MutableList<MaterialInBuilding> = mutableListOf(),
    val id: UUID = UUID.randomUUID()
){
    class MaterialInBuilding(
        val material: Material,
        var count: Int
    )

    fun addMaterial(material: Material, count: Int){
        materials.add(MaterialInBuilding(material, count))
    }

    //add negative number to subtract
    fun addMaterialCount(materialId: UUID, count: Int){
        for (material in materials){
            if(material.material.id == materialId){
                material.count += count
                return
            }
        }
    }

    fun removeMaterial(materialId: UUID){
        materials.removeIf {materialId == it.material.id}
    }

    fun getNumOfTasks(): Int{
        var count = 0
        for(store in stores){
            count += store.tasks.count()
        }
        return count
    }

    fun addStore(store: Store){
        stores.add(store)
    }

    fun getStoresString(): String{
        var str = ""
        for(store in stores){
            str += "${store.name} (${store.storeType.value})\n"
        }
        return str
    }

}