package projects.cooking.vzdrevanje_app

import android.app.AlertDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import kotlinx.coroutines.launch
import projects.cooking.vzdrevanje_app.adapters.StoreListAdapter
import projects.cooking.vzdrevanje_app.databinding.FragmentBuildingInfoBinding
import projects.cooking.vzdrevanje_app.databinding.FragmentTaskInfoBinding
import si.feri.maintenance.lib.domain.Task
import java.util.UUID
import kotlin.getValue


class TaskInfoFragment : Fragment() {
    private lateinit var binding: FragmentTaskInfoBinding
    private lateinit var app: MyApplication
    val args: TaskInfoFragmentArgs by navArgs()

    fun goBack(){
        findNavController().navigateUp()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentTaskInfoBinding.inflate(inflater, container, false)
        app = requireContext().applicationContext as MyApplication
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        app.currentStoreId = null

        val taskId = runCatching { UUID.fromString(args.taskId) }.getOrNull()
        if (taskId == null) {
            Toast.makeText(requireContext(), "Invalid Task ID", Toast.LENGTH_SHORT).show()
            goBack()
            return
        }

        val task = app.getTaskById(taskId)
        if (task == null) {
            Toast.makeText(requireContext(), "Task not found", Toast.LENGTH_SHORT).show()
            goBack()
            return
        }

        // ---- Task core ----
        binding.taskInfoTitle.text = task.title
        binding.taskInfoDetails.text = task.description?.ifBlank { "" }

        binding.taskInfoDateAdded.text = task.getDateString()

        // ---- Status ----
        if (task.finished) {
            binding.taskInfoStatus.setBackgroundResource(R.drawable.done)
        } else {
            binding.taskInfoStatus.setBackgroundResource(R.drawable.not_done)
        }

        // ---- Deadline ----
        if (task.deadline != null) {
            binding.taskInfoDeadline.text = task.getDeadlineString()
            binding.taskInfoDeadline.visibility = View.VISIBLE
            binding.deadlineIcon.visibility = View.VISIBLE
        }
        else {
            binding.taskInfoDeadline.visibility = View.GONE
            binding.deadlineIcon.visibility = View.GONE
        }

        // ---- Store + Building info ----
        val store = app.getStoreByTaskId(task.id)
        if (store != null) {
            binding.storeName.text = "${store.name} · ${store.storeType.value}"

            val building = app.getBuildingByStoreId(store.id)
            if (building != null) {
                binding.buildingAddress.text =
                    "${building.name} · ${building.address}"
            }
            else {
                binding.buildingAddress.text = ""
            }
        }
        else {
            binding.storeName.text = ""
            binding.buildingAddress.text = ""
        }


        // ---- Navigation ----
        binding.taskInfoBackButton.setOnClickListener {
            goBack()
        }

        binding.taskDeleteButton.setOnClickListener {
            AlertDialog.Builder(requireContext())
                .setTitle(getString(R.string.delete_task_popup_title))
                .setMessage(getString(R.string.delete_task_popup_description))
                .setPositiveButton(getString(R.string.yes)) { _, _ ->
                    viewLifecycleOwner.lifecycleScope.launch {
                        val deleted = app.deleteTaskById(task.id)
                        if(deleted){
                            goBack()
                        }
                        else{
                            Toast.makeText(context, getString(R.string.delete_task_failed), Toast.LENGTH_SHORT).show()
                        }
                    }
                }
                .setNegativeButton(getString(R.string.cancel), null)
                .show()
        }
    }


}