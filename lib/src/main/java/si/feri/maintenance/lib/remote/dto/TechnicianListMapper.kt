package si.feri.maintenance.lib.remote.dto

import si.feri.maintenance.lib.domain.Building
import si.feri.maintenance.lib.domain.Technician

object TechnicianListMapper {
    fun toDomain(dtos: List<TechnicianInfoDto>): MutableList<Technician> {
        return dtos.map {
            Technician(
                it.name,
                it.surname,
                mutableListOf(),
                it.phone_number
            )
        }.toMutableList()
    }

}