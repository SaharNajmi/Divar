package com.example.divar.ui.favorite

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.divar.R
import com.example.divar.common.Constants.BASE_URL
import com.example.divar.data.db.dao.entities.Advertise
import kotlinx.android.synthetic.main.favorite_item.view.*

class FavoriteAdapter(
    private val context: Context,
    private val list: ArrayList<Advertise>,
    private val favoriteBannerClickListener: FavoriteBannerClickListener
) :
    RecyclerView.Adapter<FavoriteAdapter.Holder>() {

    inner class Holder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val image = itemView.findViewById<ImageView>(R.id.fav_img)
        private val title = itemView.findViewById<TextView>(R.id.fav_title)
        private val city = itemView.findViewById<TextView>(R.id.fav_city)

        fun bind(item: Advertise) {
            Glide.with(context)
                .load("${BASE_URL}${item.img1}")
                .error(R.mipmap.ic_launcher)
                .placeholder(R.mipmap.ic_launcher_round)
                .into(image)

            title.text = item.title
            city.text = item.city

            itemView.setOnClickListener {
                item.favorite = true
                notifyItemChanged(adapterPosition)
                favoriteBannerClickListener.onClick(item)
            }

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

    interface FavoriteBannerClickListener {
        fun onClick(banner: Advertise)
        fun deleteItemClick(banner: Advertise)
    }
}

