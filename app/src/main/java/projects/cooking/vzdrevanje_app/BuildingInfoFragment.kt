package projects.cooking.vzdrevanje_app

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import projects.cooking.vzdrevanje_app.databinding.FragmentBuildingInfoBinding
import si.feri.maintenance.lib.domain.Building
import java.util.UUID
import androidx.core.net.toUri

class BuildingInfoFragment : Fragment() {
    private lateinit var binding: FragmentBuildingInfoBinding
    private lateinit var app: MyApplication
    val args: BuildingInfoFragmentArgs by navArgs()
    var stores: Boolean = true

    private lateinit var building: Building
    private lateinit var buildingUUID: UUID

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentBuildingInfoBinding.inflate(inflater, container, false)
        app = requireContext().applicationContext as MyApplication
        return binding.root
    }

    fun goBackToList(){
        findNavController().navigateUp()
        app.currentStoreId = null
    }

    private fun showStores() {
        childFragmentManager.beginTransaction()
            .replace(
                binding.storesListFragmentContainer.id,
                StoresListFragment().apply {
                    arguments = bundleOf(
                        "buildingId" to buildingUUID.toString()
                    )
                }
            )
            .commit()
    }

    private fun showMaterials() {
        childFragmentManager.beginTransaction()
            .replace(
                binding.storesListFragmentContainer.id,
                MaterialListFragment().apply {
                    arguments = bundleOf(
                        "buildingId" to buildingUUID.toString()
                    )
                }
            )
            .commit()
    }

    fun toggleStoreMaterial(){
        stores = !stores
        if(stores){
            binding.toggleStoreMaterialButton.text = getString(R.string.building_info_materials)
            showStores()
        }
        else{
            binding.toggleStoreMaterialButton.text = getString(R.string.building_info_stores)
            showMaterials()
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        buildingUUID = runCatching { UUID.fromString(args.buildingUuid) }
            .getOrNull() ?: return goBackToList() // try catch block, if it doesn't work returns null and returns back to list

        building = app.getBuildingByUUID(buildingUUID)
            ?: run { //if there is no building, return back
                Toast.makeText(context, "Non-existing building UUID", Toast.LENGTH_SHORT).show()
                goBackToList()
                return
            }

        binding.buildingInfoAddress.text = building.address.toString()
        binding.buildingInfoTitle.text = building.name


        val child = childFragmentManager.findFragmentById(binding.storesListFragmentContainer.id) as StoresListFragment
        child.arguments = bundleOf(
            "buildingId" to buildingUUID.toString()
        )

        binding.buildingInfoBackButton.setOnClickListener {
            goBackToList()
        }

        binding.toggleStoreMaterialButton.setOnClickListener {
            toggleStoreMaterial()
        }

        binding.openGpsButton.setOnClickListener {
            val lat = building.address.latitude
            val lon = building.address.longitude
            val label = Uri.encode(building.name)

            //val uri = "google.navigation:q=$lat,$lon".toUri()val
            val uri = "geo:$lat,$lon?q=$lat,$lon($label)".toUri()

            val intent = Intent(Intent.ACTION_VIEW, uri).apply {
                setPackage("com.google.android.apps.maps")
            }

            try {
                startActivity(intent)
            } catch (e: ActivityNotFoundException) {
                // fallback to browser
                //val webUri = "https://www.google.com/maps/dir/?api=1&destination=$lat,$lon".toUri()
                val webUri = "https://www.google.com/maps/search/?api=1&query=$lat,$lon".toUri()
                startActivity(Intent(Intent.ACTION_VIEW, webUri))
            }
        }
    }

}