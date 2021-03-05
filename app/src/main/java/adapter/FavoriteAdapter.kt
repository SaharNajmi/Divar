package adapter

import RoomDatabase.RowClickListener
import RoomDatabase.FavoriteEntity
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import api.ApiClient
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
           /* Glide.with(context)
                .load("${ApiClient.BASE_URL}${image}")
                .error(R.mipmap.ic_launcher)
                .placeholder(R.mipmap.ic_launcher_round)
                .into(view.)*/
        }
    }

    lateinit var date: String

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view = LayoutInflater.from(context).inflate(R.layout.list_fav, parent, false)
        return Holder(view)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        /*    val item = list[position]
            val view = holder.itemView

            date = list[position].date
            list[position].date = calculateDate(date)

            view.txt_title.text = item.title
            view.txt_price.text = item.price

            Glide.with(context)
                .load("${ApiClient.BASE_URL}${item.img1}")
                .error(R.mipmap.ic_launcher)
                .placeholder(R.mipmap.ic_launcher_round)
                .into(view.img_fav)*/

        holder.itemView.setOnClickListener {
            clicked.onItemClick(list[position])
        }
        holder.bind(list[position])
    }

    fun saveInformation(favorite: List<FavoriteEntity>) {
        this.list = favorite
        notifyDataSetChanged()
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
                date = " دقیقه پیش"
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
}