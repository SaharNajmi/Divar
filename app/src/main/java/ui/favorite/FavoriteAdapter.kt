package ui.favorite

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.divar.R
import commom.BASE_URL
import data.model.AdModel
import kotlinx.android.synthetic.main.favorite_item.view.*

class FavoriteAdapter(
    var context: Context,
    val list: ArrayList<AdModel>,
    val favoriteBannerClickListener: FavoriteBannerClickListener
) :
    RecyclerView.Adapter<FavoriteAdapter.Holder>() {

    inner class Holder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val image = itemView.findViewById<ImageView>(R.id.fav_img)
        val title = itemView.findViewById<TextView>(R.id.fav_title)
        val city = itemView.findViewById<TextView>(R.id.fav_city)

        fun bind(item: AdModel) {
            Glide.with(context)
                .load("${BASE_URL}${item.img1}")
                .error(R.mipmap.ic_launcher)
                .placeholder(R.mipmap.ic_launcher_round)
                .into(image)

            title.text = item.title
            city.text = item.city

            //item click
            itemView.setOnClickListener {
                item.favorite = true
                notifyItemChanged(adapterPosition)
                favoriteBannerClickListener.onClick(item)
            }

            //delete favorite
            itemView.fav_delete.setOnClickListener {
                favoriteBannerClickListener.deleteItemClick(item)
                list.remove(item)
                notifyItemRemoved(adapterPosition)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder =
        Holder(LayoutInflater.from(parent.context).inflate(R.layout.favorite_item, parent, false))

    override fun onBindViewHolder(holder: Holder, position: Int) = holder.bind(list[position])

    override fun getItemCount(): Int = list.size
}

interface FavoriteBannerClickListener {
    fun onClick(banner: AdModel)
    fun deleteItemClick(banner: AdModel)
}
