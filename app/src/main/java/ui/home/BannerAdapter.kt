package ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import com.example.divar.databinding.AdListItemBinding
import commom.ItemOnClickListener
import data.model.AdModel

class BannerAdapter(
    private var list: ArrayList<AdModel>,
    private val click: ItemOnClickListener
) : RecyclerView.Adapter<BannerAdapter.Holder>(), Filterable {

    lateinit var date: String

    var filterList = ArrayList<AdModel>()

    fun setData(list: ArrayList<AdModel>) {
        this.filterList = list!!
        notifyDataSetChanged()
    }

    class Holder(private val binding: AdListItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: AdModel) {
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

        /*      date = list[position].date
              list[position].date = calculateDate(date)
      */
        holder.itemView.setOnClickListener {
            click.onItemClick(list[position])
        }
    }

    fun calculateDate(item: String): String {
        // date +="همین الان
        //0-59  کمتر یک ساعت
        //60-1439  کمتر از یک روز
        //1440-43199  از یک روز تا 29 روز
        //43200-518339 یک ماه تا 12 ماه
        //518400 سال
        val getDate = Integer.parseInt(date)
        if (getDate <= 59) {
            if (getDate.equals("0") || getDate.equals("1"))
                date += "همین الان"
            else
                date += " دقیقه پیش"
        } else if (getDate in 60..1439) {
            val h = (getDate / 60).toString()
            date = h + " ساعت پیش"
        } else if (getDate in 1440..43199) {
            val hh = (getDate / 60 / 24).toString()
            date = hh + " روز پیش"
        } else if (getDate in 43200..518339) {
            val hhh = (getDate / 60 / 24 / 30).toString()
            date = hhh + " ماه پیش"
        } else if (getDate >= 518400) {
            val hhhh = (getDate / 60 / 24 / 30 / 12).toString()
            date = hhhh + " سال پیش"
        }
        return date
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(charSequence: CharSequence?): FilterResults? {
                val filterResult = FilterResults()
                if (charSequence == null) {
                    filterList = list
                } else {
                    val searChar = charSequence.toString().toLowerCase()
                    val itemModel = ArrayList<AdModel>()
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
                list = filterResults!!.values as ArrayList<AdModel>
                notifyDataSetChanged()
            }
        }
    }
}