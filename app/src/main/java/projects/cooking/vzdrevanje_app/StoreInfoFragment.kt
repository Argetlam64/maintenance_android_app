package projects.cooking.vzdrevanje_app

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import projects.cooking.vzdrevanje_app.databinding.FragmentStoreInfoBinding
import si.feri.maintenance.lib.domain.Address
import si.feri.maintenance.lib.domain.Store
import java.util.UUID

class StoreInfoFragment : Fragment() {

    private lateinit var binding: FragmentStoreInfoBinding
    private lateinit var app: MyApplication
    private lateinit var storeIdString: String
    private var storeAddress: Address? = null
    var store: Store? = null

    fun goBack(){
        findNavController().navigateUp()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        app = requireContext().applicationContext as MyApplication
        binding = FragmentStoreInfoBinding.inflate(inflater, container, false)
        storeIdString = arguments?.getString("storeId", "") ?: ""

        if(storeIdString == ""){
            Toast.makeText(context, "UUID for store not provided", Toast.LENGTH_SHORT).show()
            goBack()
        }
        try{
            val id = UUID.fromString(storeIdString)
            app.currentStoreId = id // Set current store for adding tasks
            app.storeIdForTask = id
            store = app.getStoreById(id)
            storeAddress = app.getAddressByStoreId(id)
            if(store == null){
                goBack()
            }

        }
        catch (err: IllegalArgumentException){
            Toast.makeText(context, "Not a valid UUID", Toast.LENGTH_SHORT).show()
            app.currentStoreId = null
            goBack()
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // This ensures the fragment is only added once
        if (savedInstanceState == null) {
            val tasksListFragment = TasksListFragment().apply {
                arguments = Bundle().apply {
                    putString("storeId", storeIdString)
                }
            }
            childFragmentManager.beginTransaction()
                .replace(R.id.tasksListContainer, tasksListFragment)
                .commit()
        }

        binding.storeInfoStoreName.text = store?.name
        binding.StoreInfoStoreAddress.text = storeAddress.toString()

        binding.storeInfoBackButton.setOnClickListener {
            goBack()
        }

        binding.storeInfoAddTaskButton.setOnClickListener {
            app.currentTask = null
            // currentStoreId is already set in onCreateView
            findNavController().navigate(R.id.action_global_editTaskFragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        app.currentStoreId = null // Clean up global state
    }
}