data class TechnicianFullDto(
    val id: String,
    val name: String,
    val surname: String,
    val phone_number: String,
    val privilege: Int,
    val car: CarDto?,
    val buildings: List<BuildingDto>
)

data class CarDto(
    val id: String,
    val licence_plate: String,
    val km_count: Double
)

data class BuildingDto(
    val id: String,
    val name: String,
    val address: AddressDto,
    val stores: List<StoreDto>
)

data class AddressDto(
    val street_name: String,
    val house_number: String?,
    val latitude: Double,
    val longitude: Double,
    val city: CityDto
)

data class CityDto(
    val name: String,
    val postal_code: String
)

data class StoreDto(
    val id: String,
    val name: String,
    val type: String,
    val tasks: List<TaskDto>
)

data class TaskDto(
    val id: String,
    val title: String,
    val description: String?,
    val finished: Int,
    val deadline: String?
)
