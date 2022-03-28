package com.example.divar.ui.message

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.observe
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.divar.R
import com.example.divar.common.Constants
import com.example.divar.common.MyFragment
import com.example.divar.data.model.Chat
import com.example.divar.ui.auth.UserViewModel
import kotlinx.android.synthetic.main.fragment_chat.*
import kotlinx.android.synthetic.main.layout_empty_view.view.*
import org.koin.android.ext.android.get
import org.koin.android.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class ChatFragment : MyFragment(), ChatListAdapter.ChatClickListener {

    //get my phone number
    private val userViewModel: UserViewModel by viewModel()
    private lateinit var viewModel: MessageViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_chat, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val myPhone = userViewModel.phoneNumber
        val messageViewModel: MessageViewModel by viewModel { parametersOf(myPhone, 0) }
        viewModel = messageViewModel

        //check login for show list chat
        checkLoginState()

        //get list my chat
        messageViewModel.userMessage.observe(viewLifecycleOwner) {
            if (it.isNotEmpty()) {
                rec_chat_list.layoutManager =
                    LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
                rec_chat_list.adapter = ChatListAdapter(get(), it as ArrayList<Chat>, this)
                rec_chat_list.visibility = View.VISIBLE
                emptyLayout.visibility = View.GONE
            } else {
                //show empty layout
                emptyLayout.visibility = View.VISIBLE
                rec_chat_list.visibility = View.GONE
                emptyLayout.txtEmpty.text = getString(R.string.emptyChat)
            }
        }

        //show or not show ProgressBar
        messageViewModel.progress.observe(viewLifecycleOwner) {
            setProgress(it)
        }
    }

    private fun checkLoginState() {
        if (userViewModel.isSignIn)
            alertGoToLogin.visibility = View.GONE
        else
            alertGoToLogin.visibility = View.VISIBLE
    }

    override fun onBannerClick(chat: Chat) {
        val goSendMessage = Intent(requireContext(), SendMessageActivity::class.java)
        goSendMessage.putExtra(Constants.BANNER_ID, chat.bannerID)
        goSendMessage.putExtra(Constants.BANNER_TITLE, chat.bannerTitle)
        goSendMessage.putExtra(Constants.BANNER_IMAGE, chat.bannerImage)
        goSendMessage.putExtra(Constants.SENDER, chat.sender)
        goSendMessage.putExtra(Constants.RECEIVER, chat.receiver)
        startActivity(goSendMessage)
    }

    override fun onResume() {
        super.onResume()
        //update messages
        viewModel.getMessage()
    }
}