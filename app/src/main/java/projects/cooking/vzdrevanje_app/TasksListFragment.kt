package projects.cooking.vzdrevanje_app

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import projects.cooking.vzdrevanje_app.adapters.TaskListAdapter
import projects.cooking.vzdrevanje_app.databinding.FragmentTasksListBinding
import java.util.UUID


class TasksListFragment : Fragment() {
    private lateinit var binding: FragmentTasksListBinding
    private lateinit var adapter: TaskListAdapter
    private lateinit var app: MyApplication

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentTasksListBinding.inflate(inflater, container, false)
        app = requireContext().applicationContext as MyApplication
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val storeId = arguments?.getString("storeId")

        if (storeId == null) {
            // No storeId argument, so show all tasks
            app.currentStoreId = null
            val tasks = app.getAllTasks()
            adapter = TaskListAdapter(tasks, app, { position ->
                app.currentTask = tasks[position]
                findNavController().navigate(
                    R.id.action_global_taskInfoFragment,
                    bundleOf("taskId" to tasks[position].id.toString())
                )
            }, { position ->
                app.currentTask = tasks[position]
                findNavController().navigate(R.id.action_global_editTaskFragment)
            })
        } else {
            // A storeId was provided, so show tasks for that store
            val storeUUID = UUID.fromString(storeId)
            val store = app.getStoreById(storeUUID)
            if (store != null) {
                app.currentStoreId = store.id
                adapter = TaskListAdapter(store.tasks, app, { position ->
                    app.currentTask = store.tasks[position]
                    findNavController().navigate(
                        R.id.action_global_taskInfoFragment,
                        bundleOf("taskId" to store.tasks[position].id.toString())
                    )
                }, { position ->
                    app.currentTask = store.tasks[position]
                    findNavController().navigate(R.id.action_global_editTaskFragment)
                })
            } else {
                // If the storeId is invalid, show an empty list
                adapter = TaskListAdapter(mutableListOf(), app, {}, {})
            }
        }

        binding.TaskListRecyclerView.adapter = adapter
        binding.TaskListRecyclerView.layoutManager = LinearLayoutManager(requireContext().applicationContext)
    }

}