package si.feri.maintenance.lib.remote.dto

import TechnicianFullDto
import si.feri.maintenance.lib.domain.Address
import si.feri.maintenance.lib.domain.Car
import si.feri.maintenance.lib.domain.Technician
import si.feri.maintenance.lib.domain.Building
import si.feri.maintenance.lib.domain.City
import si.feri.maintenance.lib.domain.PostalCode
import si.feri.maintenance.lib.domain.Store
import si.feri.maintenance.lib.domain.StoreType
import si.feri.maintenance.lib.domain.Task
import java.time.Instant

import java.util.UUID

object TechnicianFullMapper {

    fun toDomain(dto: TechnicianFullDto): Technician =
        Technician(
            id = UUID.fromString(dto.id),
            name = dto.name,
            surname = dto.surname,
            phoneNumber = dto.phone_number,
            privilege = dto.privilege,

            car = dto.car?.let { carDto ->
                Car(
                    id = UUID.fromString(carDto.id),
                    licencePlate = carDto.licence_plate,
                    kmCount = carDto.km_count
                )
            },

            buildings = dto.buildings.map { buildingDto ->
                Building(
                    id = UUID.fromString(buildingDto.id),
                    name = buildingDto.name,

                    address = Address(
                        streetName = buildingDto.address.street_name,
                        houseNumber = buildingDto.address.house_number,
                        latitude = buildingDto.address.latitude,
                        longitude = buildingDto.address.longitude,
                        city = City(
                            name = buildingDto.address.city.name,
                            postalCode = PostalCode(buildingDto.address.city.postal_code)
                        )
                    ),

                    stores = buildingDto.stores.map { storeDto ->
                        Store(
                            id = UUID.fromString(storeDto.id),
                            name = storeDto.name,
                            storeType = StoreType(storeDto.type),
                            tasks = storeDto.tasks.map { taskDto ->
                                Task(
                                    id = UUID.fromString(taskDto.id),
                                    title = taskDto.title,
                                    description = taskDto.description,
                                    finished = taskDto.finished == 1,
                                    deadline = taskDto.deadline?.let { Instant.parse(it) }
                                )
                            }.toMutableList()
                        )
                    }.toMutableList()
                )
            }.toMutableList()
        )
}