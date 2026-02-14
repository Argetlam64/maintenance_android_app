package si.feri.maintenance.lib.remote.dto

import java.util.UUID

data class TechnicianInfoDto(
    val id: UUID,
    val name: String,
    val surname: String,
    val phone_number: String,
    val privilege: Int
    )

data class TechnicianListDto(
    val technicians: List<TechnicianInfoDto>
)