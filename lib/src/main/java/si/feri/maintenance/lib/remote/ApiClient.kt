package si.feri.maintenance.lib.remote

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ApiClient(
    baseUrl: String
) {

    private val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(baseUrl)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val technicianApi: TechnicianApi = retrofit.create(TechnicianApi::class.java)
    val taskApi: TaskApi = retrofit.create(TaskApi::class.java)
}