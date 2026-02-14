package projects.cooking.vzdrevanje_app

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import projects.cooking.vzdrevanje_app.adapters.MaterialListAdapter
import projects.cooking.vzdrevanje_app.databinding.FragmentMaterialListBinding
import projects.cooking.vzdrevanje_app.databinding.FragmentTasksListBinding
import si.feri.maintenance.lib.domain.Building
import java.util.UUID

class MaterialListFragment : Fragment() {
    private lateinit var binding: FragmentMaterialListBinding
    lateinit var adapter: MaterialListAdapter
    lateinit var app: MyApplication
    lateinit var building : Building

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMaterialListBinding.inflate(inflater, container, false)
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

        adapter = MaterialListAdapter(building.materials)

        binding.MaterialListFragment.adapter = adapter
        binding.MaterialListFragment.layoutManager = LinearLayoutManager(requireContext().applicationContext)

    }
}