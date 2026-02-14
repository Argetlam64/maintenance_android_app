package projects.cooking.vzdrevanje_app.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import projects.cooking.vzdrevanje_app.databinding.RecyclerRowBuildingBinding
import si.feri.maintenance.lib.domain.Building

class BuildingListAdapter (val buildings: MutableList<Building>,
                           val onItemClick: (position: Int) -> Unit
): RecyclerView.Adapter< BuildingListAdapter.MyViewHolder>(){
    inner class MyViewHolder(val binding: RecyclerRowBuildingBinding) : RecyclerView.ViewHolder(binding.root){
        init{
            binding.root.setOnClickListener {
                onItemClick(adapterPosition)
            }
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = RecyclerRowBuildingBinding.inflate(inflater, parent, false)
        return MyViewHolder(binding)
    }


    override fun onBindViewHolder(holder: MyViewHolder, position: Int){
        val building = buildings[position]

        holder.binding.buildingListName.text = building.name
        holder.binding.buildingListAddress.text = building.address.toString()
        holder.binding.buildingListStores.text = building.getStoresString()
    }

    override fun getItemCount(): Int = buildings.size
}