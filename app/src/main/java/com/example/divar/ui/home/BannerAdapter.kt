package com.example.divar.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import com.example.divar.common.ItemOnClickListener
import com.example.divar.data.db.dao.entities.Advertise
import com.example.divar.databinding.AdListItemBinding

class BannerAdapter(
    private var list: ArrayList<Advertise>,
    private val click: ItemOnClickListener
) : RecyclerView.Adapter<BannerAdapter.Holder>(), Filterable {

    lateinit var date: String

    var filterList = ArrayList<Advertise>()

    fun setData(list: ArrayList<Advertise>) {
        this.filterList = list!!
        notifyDataSetChanged()
    }

    class Holder(private val binding: AdListItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Advertise) {
            binding.advertise = item
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = AdListItemBinding.inflate(layoutInflater, parent, false)
        return Holder(binding)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.bind(list[position])

        holder.itemView.setOnClickListener {
            click.onItemClick(list[position])
        }
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(charSequence: CharSequence?): FilterResults? {
                val filterResult = FilterResults()
                if (charSequence == null) {
                    filterList = list
                } else {
                    val searChar = charSequence.toString().toLowerCase()
                    val itemModel = ArrayList<Advertise>()
                    for (item in filterList) {
                        if (item.title!!.contains(searChar)) {
                            itemModel.add(item)
                        }
                    }
                    filterResult.count = itemModel.size
                    filterResult.values = itemModel
                }
                return filterResult
            }

            override fun publishResults(constraint: CharSequence?, filterResults: FilterResults?) {
                list = filterResults!!.values as ArrayList<Advertise>
                notifyDataSetChanged()
            }
        }
    }
}