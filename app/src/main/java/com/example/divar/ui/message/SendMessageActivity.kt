package com.example.divar.ui.message

import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.Gravity
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.observe
import com.example.divar.R
import com.example.divar.common.*
import com.example.divar.data.model.Chat
import com.example.divar.ui.auth.UserViewModel
import io.socket.client.IO
import io.socket.client.Socket
import io.socket.emitter.Emitter
import kotlinx.android.synthetic.main.activity_send_message.*
import org.json.JSONException
import org.json.JSONObject
import org.koin.android.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf
import java.net.URISyntaxException

class SendMessageActivity : AppCompatActivity() {

    private var socket: Socket? = null
    private var handler: Handler? = null
    private lateinit var from: String
    private lateinit var to: String
    private var bannerID: Int = 0
    private lateinit var bannerTitle: String
    private lateinit var bannerImage: String
    private val userViewModel: UserViewModel by viewModel()
    lateinit var chat: ArrayList<Chat>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_send_message)
        from = intent.getStringExtra(Constants.SENDER)
        to = intent.getStringExtra(Constants.RECEIVER)
        bannerImage = intent.getStringExtra(Constants.BANNER_IMAGE)
        bannerTitle = intent.getStringExtra(Constants.BANNER_TITLE)
        bannerID = intent.getIntExtra(Constants.BANNER_ID, 0)

        //view model message
        val messageViewModel: MessageViewModel by viewModel { parametersOf(from, bannerID) }


        //show all messages
        messageViewModel.userMessage.observe(this) {
            chat = it as ArrayList<Chat>

            showAllMessages()
        }


        //back button
        backBtn.setOnClickListener {
            finish()
        }

        //show title
        chat_title.text = bannerTitle

        //socket.io
        handler = Handler()

        try {
            //creating socket instance
            //Connect socket to server
            socket = IO.socket("http://192.168.43.144:3000")

        } catch (e: URISyntaxException) {
            e.printStackTrace()
            Log.d("fail", "Failed to connect")
        }

        socket!!.connect()

        //on : get message
        //emit : send message
        socket!!.on("chat message", onConnect)

        //send message
        btn_send.setOnClickListener {
            if (edt_message.text.toString().trim() != "") {

                //change place sender and receiver
                if (from != userViewModel.phoneNumber) {
                    to = from
                    from = userViewModel.phoneNumber
                }

                sendMessage(
                    from,
                    to,
                    edt_message.text.toString().trim(),
                    bannerID,
                    bannerTitle,
                    bannerImage
                )
                edt_message.setText("")
            }
        }

    }

    private fun showAllMessages() {
        var counter = 0
        for (item in counter until chat.size) {
            //user is sender
            if (chat[counter].sender == userViewModel.phoneNumber) {

                val textView = TextView(this@SendMessageActivity)

                //show message in textView
                textView.text = chat[counter].message

                textView.setTextColor(Color.WHITE)
                textView.setBackgroundResource(R.drawable.sendchat)

                var layoutParams = LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
                )
                layoutParams.gravity = Gravity.RIGHT
                layoutParams.setMargins(10, 7, 10, 7)

                textView.layoutParams = layoutParams
                linearMessage.addView(textView)
            } else {
                val textView = TextView(this@SendMessageActivity)
                textView.text = chat[counter].message
                textView.setTextColor(resources.getColor(R.color.myColor))
                textView.setBackgroundResource(R.drawable.recivechat)

                var layoutParams = LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
                )
                layoutParams.gravity = Gravity.LEFT
                layoutParams.setMargins(10, 7, 10, 7)

                textView.layoutParams = layoutParams
                linearMessage.addView(textView)
            }
            counter++
        }
    }


    private fun sendMessage(
        sender: String,
        receiver: String,
        message: String,
        bannerID: Int,
        bannerTitle: String,
        bannerImage: String
    ) {

        //send message to server with this Key
        socket!!.emit(
            "data",
            "$sender,$receiver,$message,$bannerID,$bannerTitle,$bannerImage"
        )
        socket!!.emit("chat message", message)
    }

    //Listen to socket and show messages
    private var onConnect: Emitter.Listener? = Emitter.Listener { args ->
        handler!!.post(object : Runnable {
            override fun run() {
                val jsonObject = args[0] as JSONObject
                var message = ""
                try {
                    //get Key in server
                    message = jsonObject.getString("message")
                    val getFrom = jsonObject.getString("from")
                    //getFrom==1    sender
                    //getFrom==2     receiver
                    //user is sender
                    if (getFrom == "1") {
                        val textView = TextView(this@SendMessageActivity)
                        textView.text = message
                        textView.setTextColor(Color.WHITE)
                        textView.setBackgroundResource(R.drawable.sendchat)

                        //layout weight and height -> wrap content
                        val layoutParams = LinearLayout.LayoutParams(
                            LinearLayout.LayoutParams.WRAP_CONTENT,
                            LinearLayout.LayoutParams.WRAP_CONTENT
                        )
                        layoutParams.gravity = Gravity.RIGHT
                        layoutParams.setMargins(10, 7, 10, 7)

                        textView.layoutParams = layoutParams
                        linearMessage.addView(textView)
                    } else {

                        val textView = TextView(this@SendMessageActivity)
                        textView.text = message
                        textView.setTextColor(resources.getColor(R.color.myColor))
                        textView.setBackgroundResource(R.drawable.recivechat)

                        var layoutParams = LinearLayout.LayoutParams(
                            LinearLayout.LayoutParams.WRAP_CONTENT,
                            LinearLayout.LayoutParams.WRAP_CONTENT
                        )
                        layoutParams.gravity = Gravity.LEFT
                        layoutParams.setMargins(10, 7, 10, 7)

                        textView.layoutParams = layoutParams
                        linearMessage.addView(textView)
                    }

                } catch (e: JSONException) {
                    e.printStackTrace()
                    Log.d("GET MESSAGE", "ERROR")
                }
            }
        })
    }

    override fun onDestroy() {
        super.onDestroy()
        socket!!.disconnect()
    }
}