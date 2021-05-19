package view

import android.content.Context
import android.content.SharedPreferences
import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.util.Log
import android.view.Gravity
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.divar.R
import io.socket.client.IO
import io.socket.client.Socket
import io.socket.emitter.Emitter
import kotlinx.android.synthetic.main.activity_send_message.*
import org.json.JSONException
import org.json.JSONObject
import java.net.URISyntaxException

class SendMessageActivity : AppCompatActivity() {

    private lateinit var prefFrom: SharedPreferences
    private lateinit var prefTo: SharedPreferences
    var socket: Socket? = null
    var handler: Handler? = null
    var from = ""
    var to = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_send_message)


        /*===========================get phone number myself and phone number to send message ====================*/
        prefFrom = getSharedPreferences("pref", Context.MODE_PRIVATE)
        from = prefFrom.getString("tell", "").toString()

        prefTo = getSharedPreferences("PHONE_NAME", Context.MODE_PRIVATE)
        to = prefTo.getString("to", "")!!
        Toast.makeText(this, "from: " + from + " to: " + to, Toast.LENGTH_LONG).show()

        /*========================socket.io==============================*/
        handler = Handler()

        try {
            //creating socket instance
            //این سوکت به کدوم آدرس وصل شه-برقراری اتصال به سرور
            socket = IO.socket("http://192.168.1.103:3000")

        } catch (e: URISyntaxException) {
            e.printStackTrace()
            Log.d("fail", "Failed to connect")
        }

        /* هر سوکتی که به سورو وصل بشه یک id میگیرد با هر کانکنت و دیس کانکت عوض میشه
       پس ذخیره کردن ایدی فایده ندارد پس باید با اسم سوکت کار کنیم socket nick name*/
        socket!!.emit("nickname", from)
        // socket!!.emit("nickname", to)


        socket!!.connect()

        socket!!.on("chat message", onConnect)
        socket!!.on("online", saveMessage)

        btn_send.setOnClickListener {
            if (edt_message.text.toString().trim() != "") {
                sendMessage(edt_message.text.toString().trim())
                edt_message.setText("")
            }
        }

    }

    //وقتی برنامه بسته بشه یا بیایم بیرون
    override fun onDestroy() {
        super.onDestroy()
        socket!!.disconnect()

    }

    private fun sendMessage(message: String) {
        //ارسال پیام به سمت سرور  با این کلید
        socket!!.emit("chat message", message)
    }

    //گوش بده هر موقع پیامی بیاد از طرف سوکت ببین پیغامی میاد یا نه اگه اومد اونو نشون بده
    private var onConnect: Emitter.Listener? = Emitter.Listener { args ->
        handler!!.post(object : Runnable {
            override fun run() {
                val jsonObject = args[0] as JSONObject
                var message = ""
                var userId = ""
                try {
                    //این کلید message را در سمت سرور در جیسان میگیرد
                    message = jsonObject.getString("message")
                   // userId = jsonObject.getString("from")

                    userId = "09187171026"

                    //اگر پیام ارسالی خودم باشد
                    if (userId == from) {

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
                        //textView.setTextColor(Color.BLUE)
                        textView.setTextColor(resources.getColor(R.color.myColor))
                        textView.setBackgroundResource(R.drawable.recivechat)

                        //از لحاظ عرضی و از لحاظ ارتفاع به اندازه خودش جا بگیره
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



    //گوش بده هر موقع پیامی بیاد از طرف سوکت ببین پیغامی میاد یا نه اگه اومد اونو نشون بده
    private var saveMessage: Emitter.Listener? = Emitter.Listener { args ->
        handler!!.post(object : Runnable {
            override fun run() {
                val jsonObject = args[0] as JSONObject
                var message = ""
                var userId = ""
                try {
                    //این کلید message را در سمت سرور در جیسان میگیرد
                    message = jsonObject.getString("message")
                    // userId = jsonObject.getString("from")

                    userId = "09187171026"

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


                } catch (e: JSONException) {
                    e.printStackTrace()
                    Log.d("GET MESSAGE", "ERROR")
                }
            }
        })
    }
}