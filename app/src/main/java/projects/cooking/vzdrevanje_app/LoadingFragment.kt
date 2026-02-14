package projects.cooking.vzdrevanje_app

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavDeepLinkBuilder
import androidx.navigation.fragment.findNavController
import kotlinx.coroutines.launch
import projects.cooking.vzdrevanje_app.databinding.FragmentLoadingBinding
import java.time.Duration
import java.time.Instant

class LoadingFragment : Fragment() {

    private lateinit var binding: FragmentLoadingBinding
    private lateinit var app: MyApplication

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLoadingBinding.inflate(inflater, container, false)
        app = requireContext().applicationContext as MyApplication

        return binding.root
    }

    fun continueToMain(){
        findNavController().navigate(R.id.action_loadingFragment_to_mainFragment)
    }



    fun getDataFromRemote(){
        viewLifecycleOwner.lifecycleScope.launch {
            val loaded = app.loadFromServer()
            if (loaded) {
                continueToMain()
            }
            else {
                Toast.makeText(context, "Failed to load from the server", Toast.LENGTH_SHORT).show()
            }
        }
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.loadingOfflineButton.setOnClickListener{
            app.fillTechnicians()
            continueToMain()
        }

        binding.loadingOnlineButton.setOnClickListener {
            getDataFromRemote()
        }
    }

}