package ui.message

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
import commom.*
import data.model.ChatList
import io.socket.client.IO
import io.socket.client.Socket
import io.socket.emitter.Emitter
import kotlinx.android.synthetic.main.activity_send_message.*
import org.json.JSONException
import org.json.JSONObject
import org.koin.android.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf
import ui.auth.UserViewModel
import java.net.URISyntaxException

class SendMessageActivity : AppCompatActivity() {

    var socket: Socket? = null
    var handler: Handler? = null
    lateinit var from: String
    lateinit var to: String
    var bannerID: Int = 0
    lateinit var bannerTitle: String
    lateinit var bannerImage: String
    val userViewModel: UserViewModel by viewModel()
    lateinit var chatList: ArrayList<ChatList>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_send_message)

        //گرفتن مقادیر موقعی که از صفحه جزیات یا از لیست چت ها وارد این صفحه(صفحه ارسال پیام) میشیم
        from = intent.getStringExtra("SENDER")
        to = intent.getStringExtra("RECEIVER")
        bannerImage = intent.getStringExtra("BANNER_IMAGE")
        bannerTitle = intent.getStringExtra("BANNER_TITLE")
        bannerID = intent.getIntExtra("BANNER_ID", 0)

        //view model message
        val messageViewModel: MessageViewModel by viewModel { parametersOf(from, bannerID) }


        //show all messages
        messageViewModel.userMessage.observe(this) {
            chatList = it as ArrayList<ChatList>

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
            //این سوکت به کدوم آدرس وصل شه-برقراری اتصال به سرور
            socket = IO.socket("http://192.168.1.102:3000")

        } catch (e: URISyntaxException) {
            e.printStackTrace()
            Log.d("fail", "Failed to connect")
        }

        socket!!.connect()

        //on  ما به اطلاعات سوکت گوش میدیم - سوکت به ما پیام میده
        //emit سوکت به اطلاعات ما گوش میده - ما به سوکت پیام میدیم
        socket!!.on("chat message", onConnect)

        /* هر سوکتی که به سورو وصل بشه یک id میگیرد با هر کانکنت و دیس کانکت عوض میشه
        پس ذخیره کردن ایدی فایده ندارد پس باید با اسم سوکت کار کنیم socket nick name*/
        // socket!!.emit("nickname", from)

        //send message
        btn_send.setOnClickListener {
            if (edt_message.text.toString().trim() != "") {

                //جای ارسال کننده و فرستنده عوض بشه در غیر این صورت همیشه فرستنده ثابت می ماند.
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
        for (item in counter until chatList.size) {
            //اگر فرستنده خودم باشم
            if (chatList[counter].sender == userViewModel.phoneNumber) {

                val textView = TextView(this@SendMessageActivity)

                //show message in textView
                textView.text = chatList[counter].message

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
                textView.text = chatList[counter].message
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
        //ارسال پیام به سمت سرور  با این کلید
        socket!!.emit(
            "data",
            sender + "," + receiver + "," + message + "," + bannerID + "," + bannerTitle + "," + bannerImage
        )
        socket!!.emit("chat message", message)
    }

    //گوش بده هر موقع پیامی بیاد از طرف سوکت ببین پیغامی میاد یا نه اگه اومد اونو نشون بده
    private var onConnect: Emitter.Listener? = Emitter.Listener { args ->
        handler!!.post(object : Runnable {
            override fun run() {
                val jsonObject = args[0] as JSONObject
                var message = ""
                try {
                    //این کلید message را در سمت سرور در جیسان میگیرد
                    message = jsonObject.getString("message")
                    val getFrom = jsonObject.getString("from")
                    //getFrom==1    sender
                    //getFrom==2     receiver
                    //اگر پیام ارسالی خودم باشد
                    if (getFrom == "1") {
                        val textView = TextView(this@SendMessageActivity)
                        textView.text = message
                        textView.setTextColor(Color.WHITE)
                        textView.setBackgroundResource(R.drawable.sendchat)

                        //از لحاظ عرضی و از لحاظ ارتفاع به اندازه خودش جا بگیره
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


    //وقتی برنامه بسته بشه یا بیایم بیرون
    override fun onDestroy() {
        super.onDestroy()
        socket!!.disconnect()
    }
}