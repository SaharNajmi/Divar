package ui.message

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.observe
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.divar.R
import commom.MyFragment
import data.model.ChatList
import kotlinx.android.synthetic.main.fragment_chat.*
import kotlinx.android.synthetic.main.layout_empty_view.view.*
import org.koin.android.ext.android.get
import org.koin.android.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf
import ui.auth.UserViewModel


class ChatFragment : MyFragment(), ChatClickListener {

    //get my phone number
    val userViewModel: UserViewModel by viewModel()
    lateinit var viewModel: MessageViewModel

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
                rec_chat_list.adapter = ChatListAdapter(get(), it as ArrayList<ChatList>, this)
                rec_chat_list.visibility = View.VISIBLE
                emptyLayout.visibility = View.GONE
            } else {
                //اگر لیست چت خالی بود لیوت خالی بودن را نشان دهد
                emptyLayout.visibility = View.VISIBLE
                rec_chat_list.visibility = View.GONE
                emptyLayout.txtEmpty.text = getString(R.string.emptyChat)
            }
        }

        //show or not show ProgressBar
        messageViewModel.progressLiveData.observe(viewLifecycleOwner) {
            setProgress(it)
        }
    }

    private fun checkLoginState() {
        if (userViewModel.isSignIn)
            alertGoToLogin.visibility = View.GONE
        else
            alertGoToLogin.visibility = View.VISIBLE
    }

    override fun onBannerClick(chatList: ChatList) {
        val goSendMessage = Intent(requireContext(), SendMessageActivity::class.java)
        goSendMessage.putExtra("BANNER_ID", chatList.bannerID)
        goSendMessage.putExtra("BANNER_TITLE", chatList.bannerTitle)
        goSendMessage.putExtra("BANNER_IMAGE", chatList.bannerImage)
        goSendMessage.putExtra("SENDER", chatList.sender)
        goSendMessage.putExtra("RECEIVER", chatList.receiver)
        startActivity(goSendMessage)
    }

    override fun onResume() {
        super.onResume()
        //موقعی که برمیگردیم به لیست چت ها آخرین پیام ارسال شده آپدیت بشه
        viewModel.getMessage()
    }
}