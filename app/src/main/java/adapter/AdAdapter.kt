package adapter

import android.app.PendingIntent.getActivity
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.divar.databinding.AdListItemBinding
import model.AdModel


class AdAdapter(
    val context: Context,
    private val list: ArrayList<AdModel>,
    val click: ItemOnClickListener
) : RecyclerView.Adapter<AdAdapter.Holder>() {

    class Holder(private val binding: AdListItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: AdModel) {
            binding.advertise = item
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val layoutInflater = LayoutInflater.from(context)
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
}