package projects.cooking.vzdrevanje_app

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import projects.cooking.vzdrevanje_app.adapters.StoreListAdapter
import projects.cooking.vzdrevanje_app.databinding.FragmentStoresListBinding
import si.feri.maintenance.lib.domain.Building
import java.util.UUID

class StoresListFragment : Fragment() {
    private lateinit var binding: FragmentStoresListBinding
    private lateinit var adapter: StoreListAdapter
    lateinit var building: Building
    private lateinit var app: MyApplication

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentStoresListBinding.inflate(inflater, container, false)
        app = requireContext().applicationContext as MyApplication
        val buildingId = arguments
            ?.getString("buildingId")
            ?. let{ UUID.fromString(it) }
            ?: return binding.root

        building = app.getBuildingByUUID(buildingId)!!
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val app = requireContext().applicationContext as MyApplication // get the MyApplication for saved info

        adapter = StoreListAdapter(building.stores,
            {pos ->
                val storeId =  building.stores[pos].id.toString()
                app.currentStoreId = building.stores[pos].id
                findNavController().navigate(R.id.action_buildingInfoFragment_to_storeInfoFragment,
                    bundleOf("storeId" to storeId)
                )
            })

        binding.storesRecyclerView.adapter = adapter
        binding.storesRecyclerView.layoutManager = LinearLayoutManager(requireContext().applicationContext)
        binding.storesRecyclerView.addItemDecoration(
            DividerItemDecoration(requireContext(),
                DividerItemDecoration.VERTICAL))
    }

}