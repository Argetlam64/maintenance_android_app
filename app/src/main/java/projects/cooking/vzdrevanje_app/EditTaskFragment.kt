package projects.cooking.vzdrevanje_app

import android.app.DatePickerDialog
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresExtension
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import kotlinx.coroutines.launch
import projects.cooking.vzdrevanje_app.databinding.FragmentEditTaskBinding
import si.feri.maintenance.lib.domain.Task
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.Calendar

class EditTaskFragment : Fragment() {
    private lateinit var binding: FragmentEditTaskBinding
    private lateinit var app: MyApplication
    private var deadline: Instant? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentEditTaskBinding.inflate(inflater, container, false)
        app = requireContext().applicationContext as MyApplication
        return binding.root
    }

    fun goBack(){
        findNavController().navigateUp()
    }

    private fun updateDeadlineDisplay() {
        if (deadline != null) {
            val localDate = deadline!!.atZone(ZoneId.systemDefault()).toLocalDate()
            binding.deadlineDisplay.text = localDate.format(DateTimeFormatter.ofPattern("dd.MM.yyyy"))
        } else {
            binding.deadlineDisplay.text = getString(R.string.task_edit_no_deadline)
        }
    }

    private fun showDatePickerDialog() {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        DatePickerDialog(requireContext(), {
            _, selectedYear, selectedMonth, selectedDay ->
            val selectedDate = LocalDate.of(selectedYear, selectedMonth + 1, selectedDay)
            deadline = selectedDate.atStartOfDay(ZoneId.systemDefault()).toInstant()
            updateDeadlineDisplay()
        }, year, month, day).show()
    }

    @RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.taskEditBackButton.setOnClickListener {
            goBack()
        }

        binding.setDeadlineButton.setOnClickListener {
            showDatePickerDialog()
        }

        binding.removeDeadlineButton.setOnClickListener {
            deadline = null
            updateDeadlineDisplay()
        }

        if(app.currentTask != null){
            binding.taskEditAcceptButton.setText(R.string.task_edit_edit)
            binding.editTaskEditTitle.setText(app.currentTask!!.title)
            binding.editTaskEditDescription.setText(app.currentTask!!.description)
            deadline = app.currentTask!!.deadline
            updateDeadlineDisplay()
        }

        binding.taskEditAcceptButton.setOnClickListener {
            viewLifecycleOwner.lifecycleScope.launch{
                val title = binding.editTaskEditTitle.text.toString()
                val description = binding.editTaskEditDescription.text.toString()

                if(app.currentTask == null){ // if we are adding task
                    app.addTaskToStore(
                        Task(title, description, deadline = deadline)
                    )
                    goBack()
                }
                else{ // if we are editing a task
                    app.currentTask!!.title = title
                    app.currentTask!!.description = description
                    app.currentTask!!.deadline = deadline
                    app.editTask()
                    goBack()
                }
            }
        }
    }

}