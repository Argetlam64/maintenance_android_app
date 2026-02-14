package si.feri.maintenance.lib.repositories

import si.feri.maintenance.lib.domain.Store
import java.util.UUID

interface StoreRepository {
    fun getById(id: UUID): Store?
    fun getByTask(id: UUID): Store?
}