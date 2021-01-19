package view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.divar.R
import kotlinx.android.synthetic.main.activity_category.*

class CategoryActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_category)

        val category = intent.getStringExtra("category")
        val title = intent.getStringExtra("titleCate")
        txt_cate.text = title
    }
}