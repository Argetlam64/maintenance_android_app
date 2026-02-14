package projects.cooking.vzdrevanje_app.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import projects.cooking.vzdrevanje_app.R
import projects.cooking.vzdrevanje_app.databinding.RecyclerRowStoreBinding
import si.feri.maintenance.lib.domain.Store

class StoreListAdapter(val stores: MutableList<Store>,
                       val onItemClick: (position: Int) -> Unit
) : RecyclerView.Adapter<StoreListAdapter.MyViewHolder>(){
    inner class MyViewHolder(
        val binding: RecyclerRowStoreBinding
    ) : RecyclerView.ViewHolder(binding.root){
        init{
            binding.root.setOnClickListener {
                onItemClick(adapterPosition)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = RecyclerRowStoreBinding.inflate(inflater, parent, false)
        return MyViewHolder(binding)
    }


    override fun onBindViewHolder(holder: MyViewHolder, position: Int){
        val store = stores[position]

        holder.binding.storeType.text = store.storeType.value
        holder.binding.unfinishedTaskCount.text = store.getUnfinishedTaskCount().toString()
        holder.binding.storeName.text = store.name
        if(store.storeType.value == "market"){
            holder.binding.storeImage.setImageResource(R.drawable.store)
        }
        else if(store.storeType.value == "drogerija"){
            holder.binding.storeImage.setImageResource(R.drawable.drug_store)
        }
    }

    override fun getItemCount(): Int = stores.size
}