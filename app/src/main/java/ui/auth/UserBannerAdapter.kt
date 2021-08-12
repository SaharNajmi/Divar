package ui.auth

import android.app.AlertDialog
import android.app.Application
import android.content.Context
import android.content.DialogInterface
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.example.divar.databinding.UserBannerBinding
import commom.ItemOnClickListener
import data.model.AdModel
import data.model.MSG
import kotlinx.android.synthetic.main.user_banner.view.*
import ui.home.BannerViewModel


class UserBannerAdapter(
    val context: Context,
    private var list: ArrayList<AdModel>,
    private val click: ItemOnClickListener
) : RecyclerView.Adapter<UserBannerAdapter.Holder>() {

    private lateinit var viewModel: BannerViewModel
    lateinit var date: String
    var newList = ArrayList<AdModel>()

    fun updateList(list: ArrayList<AdModel>?) {
        this.newList = list!!
        notifyDataSetChanged()
    }

    class Holder(private val binding: UserBannerBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: AdModel) {
            binding.advertise = item
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val layoutInflater = LayoutInflater.from(context)
        val binding = UserBannerBinding.inflate(layoutInflater, parent, false)
        return Holder(binding)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.bind(list[position])

        date = list[position].date
        list[position].date = calculateDate(date)

        holder.itemView.setOnClickListener {
            click.onItemClick(list[position])
        }
        holder.itemView.img_del.setOnClickListener {

            /* ==================================alert dialog delete product=====================================*/
            // build alert dialog
            val dialogBuilder = AlertDialog.Builder(context)

            // set message of alert dialog
            dialogBuilder.setMessage("آیا میخواهید این آگهی را حذف کنید؟")
                // if the dialog is cancelable
                .setCancelable(false)
                // positive button text and action
                .setPositiveButton("بله", DialogInterface.OnClickListener { dialog, id ->
                    viewModel = ViewModelProvider.AndroidViewModelFactory.getInstance(Application())
                        .create(BannerViewModel::class.java)
                    deleteBanner(list[position].id)
                    //    updateList(list)
                })
                // negative button text and action
                .setNegativeButton("خیر", DialogInterface.OnClickListener { dialog, id ->
                    dialog.cancel()
                })

            // create dialog box
            val alert = dialogBuilder.create()
            // show alert dialog
            alert.show()
        }
    }

    private fun calculateDate(item: String): String {
        // date +="همین الان
        //0-59  کمتر یک ساعت
        //60-1439  کمتر از یک روز
        //1440-43199  از یک روز تا 29 روز
        //43200-518339 یک ماه تا 12 ماه
        //518400 سال
        val getDate = Integer.parseInt(item)
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


    private fun deleteBanner(id: Int) {
        viewModel.deleteBanner(id).observe((context as AppCompatActivity), object : Observer<MSG> {
            override fun onChanged(t: MSG?) {
                Toast.makeText(context, t!!.msg, Toast.LENGTH_LONG).show()
            }
        })
    }
}