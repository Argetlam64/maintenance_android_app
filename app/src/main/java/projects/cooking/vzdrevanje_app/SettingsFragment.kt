package projects.cooking.vzdrevanje_app

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.navigation.fragment.findNavController
import projects.cooking.vzdrevanje_app.databinding.FragmentSettingsBinding


class SettingsFragment : Fragment() {
    private lateinit var binding: FragmentSettingsBinding
    private lateinit var app: MyApplication

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSettingsBinding.inflate(inflater, container, false)
        app = requireContext().applicationContext as MyApplication
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.settingsBackButton.setOnClickListener {
            findNavController().navigateUp()
        }
        binding.settingsDarkModeSwitch.isChecked = app.darkMode
        binding.settingsDarkModeSwitch.setOnCheckedChangeListener { _, isChecked ->
            app.changeDarkMode()
        }



        val languages = listOf(
            "English" to "en",
            "Slovenščina" to "sl"
        )

        binding.spinner.adapter =
            ArrayAdapter(
                requireContext(),
                R.layout.spinner_item,
                languages.map { it.first }
            )

        val currentLang = app.currentLanguage
        val index = languages.indexOfFirst { it.second == currentLang }
        if (index >= 0) {
            binding.spinner.setSelection(index, false)
        }


        binding.spinner.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {

                override fun onItemSelected(
                    parent: AdapterView<*>,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    val langCode = languages[position].second
                    if (app.currentLanguage != langCode) {
                        app.setLanguage(langCode)
                    }
                }

                override fun onNothingSelected(parent: AdapterView<*>) {}
            }
    }
}