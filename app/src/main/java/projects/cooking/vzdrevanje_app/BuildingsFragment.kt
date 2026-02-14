package projects.cooking.vzdrevanje_app

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import projects.cooking.vzdrevanje_app.databinding.FragmentBuildingInfoBinding
import projects.cooking.vzdrevanje_app.databinding.FragmentBuildingsBinding


class BuildingsFragment : Fragment() {
    private lateinit var binding: FragmentBuildingsBinding
    private lateinit var app: MyApplication

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentBuildingsBinding.inflate(inflater, container, false)
        app = requireContext().applicationContext as MyApplication

        return binding.root
    }

    fun showList(){
        childFragmentManager.beginTransaction()
            .replace(binding.listMapFragmentContainer.id, BuildingsListFragment()).commit()
        binding.buildingsMapViewText.text = getText(R.string.buildings_list_view_text)
    }

    fun showMaps(){
        childFragmentManager.beginTransaction()
            .replace(binding.listMapFragmentContainer.id, MapsFragment()).commit()
        binding.buildingsMapViewText.text = getText(R.string.buildings_map_view_text)
    }

    fun showView(){
        if(app.listView){
            showList()
        }
        else{
            showMaps()
        }
    }
    fun switchListView(){
        app.listView = !app.listView
        showView()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        showView()

        binding.buildingsMapViewSwitch.setOnClickListener {
            switchListView()
        }
    }
}