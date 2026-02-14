package si.feri.maintenance.lib.mappers

import si.feri.maintenance.lib.domain.Store
import si.feri.maintenance.lib.domain.StoreType
import si.feri.maintenance.lib.domain.Task
import si.feri.maintenance.lib.entities.StoreEntity

object StoreMapper {
    fun toDomain(
        e: StoreEntity,
        storeType: StoreType,
        tasks: List<Task>
    ) = Store(
        name = e.name,
        storeType = storeType,
        id = e.id,
        tasks = tasks.toMutableList()
    )
}