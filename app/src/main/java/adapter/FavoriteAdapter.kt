package adapter

import RoomDatabase.FavoriteEntity
import RoomDatabase.RowClickListener
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.divar.R
import kotlinx.android.synthetic.main.list_fav.view.*

class FavoriteAdapter(
    val context: Context,
    var list: List<FavoriteEntity>,
    val clicked: RowClickListener
) : RecyclerView.Adapter<FavoriteAdapter.Holder>() {
    class Holder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val price = itemView.txt_price
        val title = itemView.txt_title
        val date = itemView.txt_date
        val image = itemView.img_fav
        fun bind(data: FavoriteEntity) {
            price.text = data.price
            title.text = data.title
            date.text = data.date
            date.setOnClickListener { }
            Glide.with(image.context)
                .load(data.img1)
                .error(R.mipmap.ic_launcher)
                .placeholder(R.mipmap.ic_launcher_round)
                .into(image)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view = LayoutInflater.from(context).inflate(R.layout.list_fav, parent, false)
        return Holder(view)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.itemView.setOnClickListener {
            clicked.onItemClick(list[position])
        }
        holder.bind(list[position])
    }

    fun saveInformation(favorite: List<FavoriteEntity>) {
        this.list = favorite
        notifyDataSetChanged()
    }
}