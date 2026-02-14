package projects.cooking.vzdrevanje_app

import android.Manifest
import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.net.http.HttpException
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresExtension
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat
import si.feri.maintenance.lib.domain.Address
import si.feri.maintenance.lib.domain.Building
import si.feri.maintenance.lib.domain.Car
import si.feri.maintenance.lib.domain.City
import si.feri.maintenance.lib.domain.PostalCode
import si.feri.maintenance.lib.domain.Store
import si.feri.maintenance.lib.domain.StoreType
import si.feri.maintenance.lib.domain.Task
import si.feri.maintenance.lib.domain.Technician
import java.util.UUID
import androidx.core.content.edit
import androidx.core.os.LocaleListCompat
import androidx.navigation.NavDeepLink
import androidx.navigation.NavDeepLinkBuilder
import si.feri.maintenance.lib.domain.Material
import si.feri.maintenance.lib.remote.ApiClient
import si.feri.maintenance.lib.remote.dto.TechnicianFullMapper
import si.feri.maintenance.lib.remote.dto.TechnicianListMapper
import si.feri.maintenance.lib.remote.repos.RemoteTaskRepository
import si.feri.maintenance.lib.remote.repos.RemoteTechnicianRepository
import si.feri.maintenance.lib.repositories.TechnicianRepository
import java.time.Duration
import java.time.Instant
import java.time.temporal.ChronoUnit

class MyApplication : Application() {

    var technicians: MutableList<Technician> = mutableListOf()
    lateinit var currentTechnician: Technician
    var currentStoreId: UUID? = null
    lateinit var prefs: SharedPreferences
    var darkMode: Boolean = true
    var currentLanguage: String = "en"
    var currentTask: Task? = null
    var storeIdForTask: UUID? = null
    var listView = true
    lateinit var apiClient: ApiClient
    lateinit var technicianRepository: TechnicianRepository
    lateinit var taskRepository: RemoteTaskRepository
    var online: Boolean = false

    object PrefKeys{
        val DARK_MODE = "dark_mode"
        val LANGUAGE = "language"
    }



    override fun onCreate() {
        super.onCreate()
        prefs = getSharedPreferences("app_prefs", MODE_PRIVATE)
        darkMode = getMode()
        currentLanguage = prefs.getString(PrefKeys.LANGUAGE, "en")!!
        apiClient = ApiClient("http://10.54.43.53:8080/api/")
        technicianRepository = RemoteTechnicianRepository(apiClient.technicianApi)
        taskRepository = RemoteTaskRepository(apiClient.taskApi)
        createNotificationChannel(this)
    }

    fun createNotificationChannel(context: Context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                "tasks",
                "Task reminders",
                NotificationManager.IMPORTANCE_DEFAULT
            ).apply {
                description = "Task deadline notifications"
            }

            val manager = context.getSystemService(NotificationManager::class.java)
            manager.createNotificationChannel(channel)
        }
    }

    fun handleNotificationsForTasks() {
        for (building in currentTechnician.buildings) {
            for (store in building.stores) {
                for (task in store.tasks) {
                    if (task.deadline == null) {
                        continue
                    }
                    val now = Instant.now()
                    val diff = Duration.between(now, task.deadline)
                    if (!diff.isNegative && diff <= Duration.ofDays(7)) {
                        val args = TaskInfoFragmentArgs(task.id.toString()).toBundle()
                        val pendingIntent = NavDeepLinkBuilder(this)
                            .setComponentName(MainActivity::class.java)
                            .setGraph(R.navigation.my_nav)
                            .setDestination(R.id.taskInfoFragment)
                            .setArguments(args)
                            .createPendingIntent()

                        showNotification(
                            this,
                            task.title,
                            "Description: " + task.description,
                            pendingIntent
                        )
                    }
                }
            }
        }
    }

    fun showNotification(context: Context, title: String, text: String, intent: PendingIntent? = null) {

        if (Build.VERSION.SDK_INT >= 33) {
            if (ContextCompat.checkSelfPermission(
                    context,
                    Manifest.permission.POST_NOTIFICATIONS
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                return // permission not granted → do nothing
            }
        }

        if(intent == null) {
            val notification = NotificationCompat.Builder(context, "tasks")
                .setSmallIcon(R.drawable.info) // MUST be a monochrome icon
                .setContentTitle(title)
                .setContentText(text)
                .setAutoCancel(true)
                .build()

            NotificationManagerCompat.from(context)
                .notify(System.currentTimeMillis().toInt(), notification)
        }
        else{
            val notification = NotificationCompat.Builder(context, "tasks")
                .setSmallIcon(android.R.drawable.ic_dialog_info)
                .setContentTitle(title)
                .setContentText(text)
                .setContentIntent(intent)
                .setAutoCancel(true)
                .build()

            NotificationManagerCompat.from(context)
                .notify(System.currentTimeMillis().toInt(), notification)
        }

    }


    fun getMode(): Boolean{
        val mode = prefs.getBoolean(PrefKeys.DARK_MODE, true)
        if (!prefs.contains(PrefKeys.DARK_MODE)) {
            prefs.edit {
                putBoolean(PrefKeys.DARK_MODE, mode)
            }
        }
        AppCompatDelegate.setDefaultNightMode(
            if (mode)
                AppCompatDelegate.MODE_NIGHT_YES
            else
                AppCompatDelegate.MODE_NIGHT_NO
        )
        return mode
    }

    fun changeDarkMode(){
        darkMode = !darkMode
        prefs.edit{
            putBoolean(PrefKeys.DARK_MODE, darkMode)
        }
        AppCompatDelegate.setDefaultNightMode(
            if (darkMode)
                AppCompatDelegate.MODE_NIGHT_YES
            else
                AppCompatDelegate.MODE_NIGHT_NO
        )
    }

    fun setLanguage(lang: String) {
        currentLanguage = lang
        prefs.edit { putString(PrefKeys.LANGUAGE, lang) }

        val locales = LocaleListCompat.forLanguageTags(lang)
        AppCompatDelegate.setApplicationLocales(locales)
    }


    fun getBuildingByUUID(uuid: UUID): Building?{
        for(building in currentTechnician.buildings){
            if(building.id == uuid){
                return building
            }
        }
        return null
    }

    fun getStoreById(uuid: UUID): Store?{
        for(building in currentTechnician.buildings){
            for(store in building.stores){
                if(store.id == uuid){
                    return store
                }
            }
        }
        return null
    }

    fun getBuildingByStoreId(uuid: UUID): Building?{
        for(building in currentTechnician.buildings){
            for(store in building.stores){
                if(store.id == uuid){
                    return building
                }
            }
        }
        return null
    }

    fun getStoreByTaskId(uuid: UUID): Store?{
        for(building in currentTechnician.buildings){
            for(store in building.stores){
                for(task in store.tasks){
                    if(task.id == uuid){
                        return store
                    }
                }
            }
        }
        return null
    }

    fun getAddressByStoreId(uuid: UUID) : Address?{
        for(building in currentTechnician.buildings){
            for(store in building.stores){
                if(store.id == uuid){
                    return building.address
                }
            }
        }
        return null
    }

    fun getAddressByTaskId(uuid: UUID): Address?{
        for(building in currentTechnician.buildings){
            for(store in building.stores){
                for(task in store.tasks){
                    if(task.id == uuid){
                        return building.address
                    }
                }
            }
        }
        return null
    }

    fun getAllTasks(): MutableList<Task>{
        val tasks: MutableList<Task> = mutableListOf()
        for(building in currentTechnician.buildings){
            for(store in building.stores){
                for(task in store.tasks){
                    tasks.add(task)
                }
            }
        }
        return tasks
    }

    fun getStoreNameByTaskId(uuid: UUID): String?{
        for(building in currentTechnician.buildings){
            for(store in building.stores){
                for(task in store.tasks){
                    if(task.id == uuid){
                        return store.name
                    }
                }
            }
        }
        return null
    }

    fun getTaskById(uuid: UUID): Task?{
        for(building in currentTechnician.buildings){
            for(store in building.stores){
                for(task in store.tasks){
                    if(task.id == uuid){
                        return task
                    }
                }
            }
        }
        return null
    }

    suspend fun deleteTaskById(uuid: UUID): Boolean{
        for(building in currentTechnician.buildings){
            for(store in building.stores){
                val removed = store.tasks.removeIf { it.id == uuid }
                if(removed && online){
                    Log.d("Delete", "Deleting UUID: $uuid")
                    taskRepository.delete(uuid)
                }
               if(removed){
                   return true
               }
            }
        }
        return false
    }

    suspend fun editTask(){
        try {
            if(online){
                taskRepository.update(currentTask!!)
            }
        }
        catch(err: IllegalArgumentException){
            Log.e("Invalud argument", "Current task does not exist, trying to update it anyways")
        }
    }

    @RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
    suspend fun addTaskToStore(task: Task){
        for(building in currentTechnician.buildings){
            for(store in building.stores){
                if(store.id == storeIdForTask){
                    store.addTask(
                        task.title,
                        task.description!!,
                        task.deadline,
                        task.finished,
                        task.id
                    )
                    if(online){
                        try {
                            taskRepository.add(store.id, task)
                        }
                        catch (error: HttpException){
                            Log.d("debug", "Http error: " + error.toString())
                        }
                    }
                }
            }
        }
    }

    suspend fun loadFromServer(): Boolean{
        return try {
            currentTechnician =
                TechnicianFullMapper.toDomain(
                    technicianRepository.getFullTechnician(
                        "2b26071d-b3ed-49a7-9fc2-23032c922d5b"
                    )
                )

            technicians = TechnicianListMapper.toDomain(
                technicianRepository.getTechnicianList()
            )

            online = true
            true
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }


    fun fillTechnicians(){
        val dacia = Car("LJ-KN-200", 128113.0)
        val opel = Car("KR-KK-121", 192842.0)
        val david = Technician("David", "Erbežnik", phoneNumber = "+386 64 152 129", car = dacia)
        val martin = Technician("Martin", "Leskovec", phoneNumber = "+386 51 123 121", car = opel)

        technicians.add(david)
        technicians.add(martin)
        /*
        CoroutineScope(Dispatchers.IO).launch {
            try {
                currentTechnician = TechnicianFullMapper.toDomain(technicianRepository.getFullTechnician("2b26071d-b3ed-49a7-9fc2-23032c922d5b"))
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }*/



        val addressLitija = Address(City("Litija", PostalCode("1270")), "Litijska", 46.5547, 15.6459,"5")
        val addressMaribor = Address(City("Maribor", PostalCode("2000")), "Ljubljanska",46.5598, 15.6402, "3")
        val addressLjubljana = Address(City("Ljubljana", PostalCode("1000")), "Koroška",46.5531, 15.6305, "12")

        val drogerija = StoreType("drogerija")
        val market = StoreType("market")
        val cashNCarry = StoreType("Cash n carry")

        val tusDrogerijaLitija = Store("TUŠ drogerija občina", drogerija)
        val tusMarketLitija = Store("Tuš market občina", market)
        val tusCCBTCStore = Store("Tuš cash and carry BTC", cashNCarry)
        val tusMarketMaribor = Store("Tuš market Lent", market)


        tusDrogerijaLitija.addTask("Clean the root",
            "Clear the roof of the leaves and mud, ladder is in the basement")

        tusDrogerijaLitija.addTask("Fill the salt tank",
            "Fill the salt tank, use the fancy salt, don't get it everywhere")


        val obcinaLitija = Building("Občina Litija", addressLitija)
        val tusCCBTC = Building("Tuš cash and carry BTC", addressLjubljana)
        val lent = Building("Lent Maribor", addressMaribor)

        obcinaLitija.addMaterial(Material("36W/830"), 2)
        obcinaLitija.addMaterial(Material("CDM-T 78W"), 2)
        obcinaLitija.addMaterial(Material("Flag"), 2)

        obcinaLitija.addStore(tusDrogerijaLitija)
        obcinaLitija.addStore(tusMarketLitija)
        tusCCBTC.addStore(tusCCBTCStore)
        lent.addStore(tusMarketMaribor)





        val addressKoper = Address(City("Koper", PostalCode("6000")), "Pristaniška", 46.5610, 15.6673,"8")
        val addressCelje = Address(City("Celje", PostalCode("3000")), "Mariborska", 46.5462, 15.6609,"22")

        val bakery = StoreType("bakery")
        val hardware = StoreType("hardware")

        val tusBakeryKoper = Store("Tuš pekarna Koper", bakery)
        val tusMarketCelje = Store("Tuš market Center", market)
        val merkurCelje = Store("Merkur Celje", hardware)

        tusDrogerijaLitija.addTask(
            "Clean the root",
            "Clear the roof of the leaves and mud, ladder is in the basement",
            id = UUID.fromString("00000000-0000-0000-0000-000000000001")
        )

        tusDrogerijaLitija.addTask(
            "Fill the salt tank",
            "Fill the salt tank, use the fancy salt, don't get it everywhere",
            id = UUID.fromString("00000000-0000-0000-0000-000000000002")
        )

        tusBakeryKoper.addTask(
            "Clean ovens",
            "Deep clean all ovens after closing time",
            Instant.now(),
            true,
            UUID.fromString("00000000-0000-0000-0000-000000000003")
        )

        tusMarketCelje.addTask(
            "Check fridges",
            "Verify temperature logs and clean condenser grills",
            id = UUID.fromString("00000000-0000-0000-0000-000000000004")
        )

        merkurCelje.addTask(
            "Inspect shelves",
            "Check metal shelves for rust and loose bolts",
            id = UUID.fromString("00000000-0000-0000-0000-000000000005")
        )

        tusDrogerijaLitija.addTask(
            "Inspect ventilation",
            "Check ventilation filters in storage and sales area",
            id = UUID.fromString("00000000-0000-0000-0000-000000000006")
        )

        tusDrogerijaLitija.addTask(
            "Emergency lights test",
            "Test all emergency exit lights and replace faulty units",
            id = UUID.fromString("00000000-0000-0000-0000-000000000007")
        )

        tusMarketLitija.addTask(
            "Clean freezer room",
            "Remove ice buildup and clean floor drains",
            id = UUID.fromString("00000000-0000-0000-0000-000000000008")
        )

        tusMarketLitija.addTask(
            "Check fire extinguishers",
            "Verify pressure and inspection dates",
            id = UUID.fromString("00000000-0000-0000-0000-000000000009")
        )

        tusCCBTCStore.addTask(
            "Loading dock inspection",
            "Inspect dock levelers and safety rails",
            Instant.now().plus(1, ChronoUnit.DAYS),
            false,
            UUID.fromString("00000000-0000-0000-0000-000000000010")
        )

        tusCCBTCStore.addTask(
            "Pallet rack check",
            "Check racks for damage and loose anchors",
            id = UUID.fromString("00000000-0000-0000-0000-000000000011")
        )

        tusMarketMaribor.addTask(
            "Entrance door service",
            "Lubricate automatic doors and check sensors",
            id = UUID.fromString("00000000-0000-0000-0000-000000000012")
        )

        tusMarketMaribor.addTask(
            "Parking lights",
            "Replace broken exterior light bulbs",
            id = UUID.fromString("00000000-0000-0000-0000-000000000013")
        )

        tusBakeryKoper.addTask(
            "Vent hood cleaning",
            "Degrease ventilation hood above ovens",
            id = UUID.fromString("00000000-0000-0000-0000-000000000014")
        )

        tusBakeryKoper.addTask(
            "Flour storage check",
            "Inspect for moisture and pest activity",
            id = UUID.fromString("00000000-0000-0000-0000-000000000015")
        )

        tusMarketCelje.addTask(
            "POS cable check",
            "Secure loose cables under checkout counters",
            id = UUID.fromString("00000000-0000-0000-0000-000000000016")
        )

        tusMarketCelje.addTask(
            "Cold room alarm test",
            "Test temperature alarm system",
            id = UUID.fromString("00000000-0000-0000-0000-000000000017")
        )

        merkurCelje.addTask(
            "Forklift charging station",
            "Inspect charging cables and safety signage",
            id = UUID.fromString("00000000-0000-0000-0000-000000000018")
        )

        merkurCelje.addTask(
            "Outdoor material yard",
            "Check fence integrity and gate locks",
            Instant.now().plus(1, ChronoUnit.DAYS),
            false,
            UUID.fromString("00000000-0000-0000-0000-000000000019")
        )

        val koperCenter = Building("Koper Center", addressKoper)
        val celjeCenter = Building("Celje Center", addressCelje)

        koperCenter.addStore(tusBakeryKoper)
        celjeCenter.addStore(tusMarketCelje)
        celjeCenter.addStore(merkurCelje)

        val renault = Car("KP-AA-404", 86420.0)

        val nina = Technician("Nina", "Kovač", phoneNumber = "+386 64 882 121", car = renault)

        technicians.add(nina)
        martin.addBuilding(koperCenter)
        martin.addBuilding(celjeCenter)
        martin.addBuilding(tusCCBTC)
        martin.addBuilding(lent)
        martin.addBuilding(obcinaLitija)
        currentTechnician = martin
    }
}