package com.example.divar.ui.message

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.divar.R
import com.example.divar.commom.BASE_URL
import com.example.divar.data.model.ChatList
import com.example.divar.service.ImageLoadingService
import com.example.divar.ui.view.MyImageView

class ChatListAdapter(
    var imageLoadingService: ImageLoadingService,
    val list: ArrayList<ChatList>,
    val chatClickListener: ChatClickListener
) : RecyclerView.Adapter<ChatListAdapter.Holder>() {


    inner class Holder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val image = itemView.findViewById<MyImageView>(R.id.msg_img_banner)
        val title = itemView.findViewById<TextView>(R.id.msg_title_banner)
        val lastMessage = itemView.findViewById<TextView>(R.id.msg_last)

        fun bind(chatList: ChatList) {
            imageLoadingService.load(image, "${BASE_URL}${chatList.bannerImage}")
            title.text = chatList.bannerTitle
            lastMessage.text = chatList.message

            // نباید از خود آداپتر کاربر را به اکتیویتی بفرستیم اطلاعات را به فرگمنت پاس بده فرگمنت تصمیم میگیره که دیتا را به کجا بفرسته
            itemView.setOnClickListener {
                chatClickListener.onBannerClick(chatList)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder = Holder(
        LayoutInflater.from(parent.context).inflate(R.layout.chat_list, parent, false)
    )


    override fun onBindViewHolder(holder: Holder, position: Int) = holder.bind(list[position])

    override fun getItemCount(): Int = list.size
}

interface ChatClickListener {
    fun onBannerClick(chatList: ChatList)
}