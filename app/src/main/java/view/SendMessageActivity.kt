package view

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.divar.R

class SendMessageActivity : AppCompatActivity() {

    private lateinit var prefFrom: SharedPreferences
    private lateinit var prefTo: SharedPreferences
    var from = ""
    var to = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_send_message)

        prefFrom = getSharedPreferences("pref", Context.MODE_PRIVATE)
        from = prefFrom.getString("tell", "").toString()


        prefTo = getSharedPreferences("PHONE_NAME", Context.MODE_PRIVATE)
        to = prefTo.getString("to", "")!!
        Toast.makeText(this, "from: " + from + " to: " + to, Toast.LENGTH_LONG).show()


    }
}