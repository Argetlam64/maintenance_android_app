package projects.cooking.vzdrevanje_app.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import projects.cooking.vzdrevanje_app.databinding.RecyclerRowTechnicianBinding
import si.feri.maintenance.lib.domain.Technician

class TechnicianListAdapter (val technicians: MutableList<Technician>)
    : RecyclerView.Adapter<TechnicianListAdapter.MyViewHolder>() {
    inner class MyViewHolder(
            val binding: RecyclerRowTechnicianBinding
        ): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder( parent: ViewGroup, viewType: Int): MyViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = RecyclerRowTechnicianBinding.inflate(inflater, parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder( holder: MyViewHolder, position: Int) {
        val technician = technicians[position]

        holder.binding.technicianListName.text = technician.fullName()
        holder.binding.technicianListPhoneNumber.text = technician.phoneNumber
    }

    override fun getItemCount(): Int = technicians.size
}