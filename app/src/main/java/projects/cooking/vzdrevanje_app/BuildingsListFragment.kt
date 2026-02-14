package projects.cooking.vzdrevanje_app

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import projects.cooking.vzdrevanje_app.adapters.BuildingListAdapter
import projects.cooking.vzdrevanje_app.databinding.FragmentBuildingsListBinding

class BuildingsListFragment : Fragment() {
    private lateinit var binding: FragmentBuildingsListBinding
    private lateinit var app: MyApplication
    lateinit var adapter: BuildingListAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        app = requireContext().applicationContext as MyApplication
        binding = FragmentBuildingsListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter = BuildingListAdapter(app.currentTechnician.buildings,
            {pos ->
                val action =
                    MyNavDirections.actionGlobalBuildingInfoFragment(
                        app.currentTechnician.buildings[pos].id.toString()
                    )

                findNavController().navigate(action)
            })
        binding.BuildingsRecyclerView.adapter = adapter
        binding.BuildingsRecyclerView.layoutManager = LinearLayoutManager(requireContext().applicationContext)
    }

}