package si.feri.maintenance.lib.mappers
/*
import si.feri.maintenance.lib.domain.Address
import si.feri.maintenance.lib.domain.Building
import si.feri.maintenance.lib.domain.Car
import si.feri.maintenance.lib.domain.City
import si.feri.maintenance.lib.domain.Material
import si.feri.maintenance.lib.domain.PostalCode
import si.feri.maintenance.lib.domain.Store
import si.feri.maintenance.lib.domain.StoreType
import si.feri.maintenance.lib.domain.Task
import si.feri.maintenance.lib.domain.Technician
import si.feri.maintenance.lib.entities.AddressEntity
import si.feri.maintenance.lib.entities.BuildingEntity
import si.feri.maintenance.lib.entities.CarEntity
import si.feri.maintenance.lib.entities.CityEntity
import si.feri.maintenance.lib.entities.MaterialEntity
import si.feri.maintenance.lib.entities.PostalCodeEntity
import si.feri.maintenance.lib.entities.StoreEntity
import si.feri.maintenance.lib.entities.StoreTypeEntity
import si.feri.maintenance.lib.entities.TaskEntity
import si.feri.maintenance.lib.entities.TechnicianEntity
import java.util.UUID


fun mapCarEntityToDomain(
    entity: CarEntity
): Car{
    return Car( entity.licencePlate, entity.kmCount, entity.id)
}


fun mapPostalCodeEntityToDomain(
    entity: PostalCodeEntity
): PostalCode{
    return PostalCode(
        entity.value,
        entity.id
    )
}


fun mapCityEntityToDomain(
    entity: CityEntity,
    postalCodeProvider: (UUID) -> PostalCode
): City{
    return City(
        entity.name,
        postalCodeProvider(entity.postalCodeId),
        entity.id
    )
}


fun mapAddressEntityToDomain(
    entity: AddressEntity,
    cityProvider: (UUID) -> City
): Address{
    return Address(
        cityProvider(entity.cityId),
        entity.streetName,
        entity.houseNumber,
        entity.id,
    )
}


fun mapBuildingEntityToDomain(
    entity: BuildingEntity,
    addressProvider: (UUID) -> Address
): Building {
    return Building(
        entity.name,
        addressProvider(entity.addressId),
        id = entity.id
    )
}


fun mapStoreEntityToDomain(
    entity: StoreEntity,
    storeTypeProvider: (UUID) -> StoreType,
    buildingProvider: (UUID) -> Building
): Store {
    return Store(
        entity.id,
        entity.name,
        storeTypeProvider(entity.storeTypeId),
        buildingProvider(entity.buildingId)
    )
}


fun mapStoreTypeEntityToDomain(
    entity: StoreTypeEntity
): StoreType{
    return StoreType(
        entity.id,
        entity.value
    )
}


fun mapMaterialEntityToDomain(
    entity: MaterialEntity,
    buildingProvider: (UUID) -> Building
): Material {
    return Material(
        entity.id,
        entity.name
    )
}


fun mapTechnicianEntityToDomain(
    entity: TechnicianEntity,
    carProvider: (UUID) -> Car
): Technician {
    return Technician(
        entity.id,
        entity.name,
        entity.surname,
        carProvider(entity.carId)
    )
}

fun mapTaskEntityToDomain(
    entity: TaskEntity,
    storeProvider: (UUID) -> Store
): Task {
    return Task(
        entity.id,
        entity.title,
        entity.description,
        storeProvider(entity.storeId),
        entity.deadline
    )
}*/