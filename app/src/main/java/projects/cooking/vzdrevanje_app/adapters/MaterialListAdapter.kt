package projects.cooking.vzdrevanje_app.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.getString
import androidx.recyclerview.widget.RecyclerView
import projects.cooking.vzdrevanje_app.R
import projects.cooking.vzdrevanje_app.databinding.RecyclerRowBuildingBinding
import projects.cooking.vzdrevanje_app.databinding.RecyclerRowMaterialBinding
import si.feri.maintenance.lib.domain.Building

class MaterialListAdapter (val materials: MutableList<Building.MaterialInBuilding>):
RecyclerView.Adapter<MaterialListAdapter.MyViewHolder>(){
    inner class MyViewHolder(val binding: RecyclerRowMaterialBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = RecyclerRowMaterialBinding.inflate(inflater, parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MaterialListAdapter.MyViewHolder, position: Int){
        val material = materials[position].material
        val count = materials[position].count

        holder.binding.materialName.text = material.name
        holder.binding.materialCount.text = count.toString()
        holder.binding.materialManufacturer.text =
            material.manufacturer?.takeIf { it.isNotBlank() }
                ?: ContextCompat.getString(
                    holder.itemView.context,
                    R.string.material_row_no_manufacturer
                )
    }


    override fun getItemCount(): Int = materials.size
}
