package si.feri.maintenance.lib.remote.dto

data class TaskCreateDto(
    val title: String,
    val description: String? = null,
    val finished: Int = 0,
    val deadline: String? = null,
    val store_id: String
)