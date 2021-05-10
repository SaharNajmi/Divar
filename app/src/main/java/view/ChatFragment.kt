package view

import android.content.Context
import android.content.SharedPreferences
import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.core.view.marginRight
import androidx.fragment.app.Fragment
import com.example.divar.R
import io.socket.client.IO
import io.socket.client.Socket
import io.socket.emitter.Emitter
import kotlinx.android.synthetic.main.fragment_chat.*
import org.json.JSONException
import org.json.JSONObject
import java.net.URISyntaxException


class ChatFragment : Fragment() {

    var socket: Socket? = null
    var handler: Handler? = null
    private lateinit var pref: SharedPreferences
    var from = ""
    var to = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_chat, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        pref = requireActivity().getSharedPreferences("pref", Context.MODE_PRIVATE)
        from= pref.getString("tell", "").toString()

        Toast.makeText(requireContext(),from,Toast.LENGTH_LONG).show()

        to = "3"

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
        socket!!.emit("nickname", to)

        socket!!.connect()

        socket!!.on("chat message", onConnect)


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
                    //    userId = jsonObject.getString("from")
                    userId = "10"

                    if (userId != from) {

                        val textView = TextView(requireContext())
                        textView.text = message
                        textView.setTextColor(Color.WHITE)
                        textView.setBackgroundResource(R.drawable.sendchat)

                        //از لحاظ عرضی و از لحاظ ارتفاع هم به اندازه خودش جا بگیره
                        var layoutParams = LinearLayout.LayoutParams(
                            LinearLayout.LayoutParams.WRAP_CONTENT,
                            LinearLayout.LayoutParams.WRAP_CONTENT
                        )
                        layoutParams.gravity = Gravity.RIGHT
                        layoutParams.setMargins(10,7,10,7)

                        textView.layoutParams = layoutParams
                        linearMessage.addView(textView)
                    } else {

                        val textView = TextView(requireContext())
                        textView.text = message
                        //textView.setTextColor(Color.BLUE)
                        textView.setTextColor(resources.getColor(R.color.myColor))
                        textView.setBackgroundResource(R.drawable.recivechat)

                        //از لحاظ عرضی کل صفحه رو بگیر از لحاظ ارتفاع هم به اندازه خودش جا بگیره
                        var layoutParams = LinearLayout.LayoutParams(
                            LinearLayout.LayoutParams.WRAP_CONTENT,
                            LinearLayout.LayoutParams.WRAP_CONTENT
                        )
                        layoutParams.gravity = Gravity.LEFT
                        layoutParams.setMargins(10,7,10,7)

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
}