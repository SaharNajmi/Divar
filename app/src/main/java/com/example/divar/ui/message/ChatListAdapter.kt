package com.example.divar.ui.message

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.divar.R
import com.example.divar.common.Constants.BASE_URL
import com.example.divar.data.model.Chat
import com.example.divar.service.ImageLoadingService
import com.example.divar.ui.view.MyImageView

class ChatListAdapter(
    private val imageLoadingService: ImageLoadingService,
    private val list: ArrayList<Chat>,
    private val chatClickListener: ChatClickListener
) : RecyclerView.Adapter<ChatListAdapter.Holder>() {


    inner class Holder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val image = itemView.findViewById<MyImageView>(R.id.msg_img_banner)
        private val title = itemView.findViewById<TextView>(R.id.msg_title_banner)
        private val lastMessage = itemView.findViewById<TextView>(R.id.msg_last)

        fun bind(chat: Chat) {
            imageLoadingService.load(image, "${BASE_URL}${chat.bannerImage}")
            title.text = chat.bannerTitle
            lastMessage.text = chat.message

            itemView.setOnClickListener {
                chatClickListener.onBannerClick(chat)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder = Holder(
        LayoutInflater.from(parent.context).inflate(R.layout.chat_list, parent, false)
    )

    override fun onBindViewHolder(holder: Holder, position: Int) = holder.bind(list[position])

    override fun getItemCount(): Int = list.size

    interface ChatClickListener {
        fun onBannerClick(chat: Chat)
    }
}