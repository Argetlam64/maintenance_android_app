package si.feri.maintenance.lib.repositories

import si.feri.maintenance.lib.domain.Address
import java.util.UUID

interface AddressRepository {
    fun getById(id: UUID): Address?
    fun getByStore(id: UUID): Address?
    fun getByTask(id: UUID): Address?
}