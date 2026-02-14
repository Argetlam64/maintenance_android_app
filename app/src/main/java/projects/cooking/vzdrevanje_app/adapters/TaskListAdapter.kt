package projects.cooking.vzdrevanje_app.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import projects.cooking.vzdrevanje_app.MyApplication
import projects.cooking.vzdrevanje_app.databinding.RecyclerRowTaskBinding
import si.feri.maintenance.lib.domain.Address
import si.feri.maintenance.lib.domain.Task

data class TaskWithAddress (val task: Task, val address: Address, val storeName: String)

class TaskListAdapter (val tasks: MutableList<Task>,
                        val app: MyApplication
                        ,val onItemClick: (position: Int) -> Unit
                        ,val onLongClick: (position: Int) -> Unit
):
    RecyclerView.Adapter<TaskListAdapter.MyViewHolder>(){
    var tasksWithAddresses: MutableList<TaskWithAddress> = mutableListOf()
    init{
        for(task in tasks){
            val address = app.getAddressByTaskId(task.id)
            val storeName = app.getStoreNameByTaskId(task.id)
            if(address != null && storeName != null){
                tasksWithAddresses.add(TaskWithAddress(task, address, storeName))
            }
        }
    }

    inner class MyViewHolder(
        val binding: RecyclerRowTaskBinding
    ) : RecyclerView.ViewHolder(binding.root){
        init{
            binding.root.setOnClickListener {
                onItemClick(adapterPosition)
            }
            binding.root.setOnLongClickListener {
                app.currentTask = tasksWithAddresses[adapterPosition].task
                onLongClick(adapterPosition)
                true
            }
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = RecyclerRowTaskBinding.inflate(inflater, parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int){
        val task: Task = tasksWithAddresses[position].task
        val address: Address = tasksWithAddresses[position].address
        val storeName: String = tasksWithAddresses[position].storeName

        holder.binding.taskTitle.text = task.title
        holder.binding.taskDescription.text = task.description
        holder.binding.taskStoreName.text = storeName
        holder.binding.taskAddress.text = address.toString()
    }

    override fun getItemCount(): Int = tasksWithAddresses.size
}




