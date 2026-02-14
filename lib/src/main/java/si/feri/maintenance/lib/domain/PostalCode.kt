package si.feri.maintenance.lib.domain
import java.util.UUID;

class PostalCode(
    val value: String,
    val id: UUID = UUID.randomUUID()
) {

}