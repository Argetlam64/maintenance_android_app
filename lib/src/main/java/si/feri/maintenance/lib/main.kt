package si.feri.maintenance.lib
import kotlinx.coroutines.runBlocking
import si.feri.maintenance.lib.domain.Address
import java.util.UUID
import si.feri.maintenance.lib.domain.Building
import si.feri.maintenance.lib.domain.Car
import si.feri.maintenance.lib.domain.City
import si.feri.maintenance.lib.domain.Material
import si.feri.maintenance.lib.domain.PostalCode
import si.feri.maintenance.lib.domain.Store
import si.feri.maintenance.lib.domain.StoreType
import si.feri.maintenance.lib.domain.Task
import si.feri.maintenance.lib.domain.Technician
import si.feri.maintenance.lib.remote.ApiClient
import si.feri.maintenance.lib.remote.dto.TechnicianFullMapper
import si.feri.maintenance.lib.remote.dto.TechnicianListMapper
import si.feri.maintenance.lib.remote.repos.RemoteTechnicianRepository
import si.feri.maintenance.lib.repositories.TechnicianRepository
import java.time.Instant

fun main() = runBlocking{
    val apiClient = ApiClient(
        baseUrl = "http://localhost:8080/api/"
    )

    val repo = RemoteTechnicianRepository(apiClient.technicianApi)

    val tech = TechnicianFullMapper.toDomain(repo.getFullTechnician("2b26071d-b3ed-49a7-9fc2-23032c922d5b"))

    val technicians = TechnicianListMapper.toDomain(
        repo.getTechnicianList()
    )

    println("Name: ${tech.name}")
    println("Buildings: ${tech.buildings.size}")

    println("Buildings:")
    for(t in technicians){
        println(t.name)
    }
}