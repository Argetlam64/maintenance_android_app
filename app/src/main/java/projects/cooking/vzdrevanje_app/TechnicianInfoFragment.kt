package projects.cooking.vzdrevanje_app

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import projects.cooking.vzdrevanje_app.databinding.FragmentTechnicianInfoBinding

class TechnicianInfoFragment : Fragment() {
    private lateinit var binding: FragmentTechnicianInfoBinding
    lateinit var app: MyApplication

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentTechnicianInfoBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        app = requireContext().applicationContext as MyApplication

        binding.fullNameTextView.text = app.currentTechnician.fullName()
        binding.phoneNumberTextView.text = app.currentTechnician.phoneNumber
        if(app.currentTechnician.car != null){
            binding.carKilometersTextView.text = "${getString(R.string.technician_info_car_kilometers)} ${app.currentTechnician.car?.kmCount.toString()}km"
            binding.carLicencePlateTextView.text =  "${getString(R.string.technician_info_car_licence_plate)} ${app.currentTechnician.car?.licencePlate}"
        }
        else{
            binding.carKilometersTextView.text = getString(R.string.technician_info_no_car_kilometers)
            binding.carLicencePlateTextView.text = getString(R.string.technician_info_no_car_string)
        }

        binding.buildingCountText.text = "${app.currentTechnician.buildings.count()} ${getString(R.string.technician_info_building_count)}"

        binding.notificationButton.setOnClickListener {
            app.handleNotificationsForTasks()
        }

    }
}