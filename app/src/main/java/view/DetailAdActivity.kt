package view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.divar.R

class DetailAdActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_ad)

        /*
        val id = intent.getIntExtra("id", 0)
        val productName = intent.getStringExtra("name")
        val productPrice = intent.getStringExtra("price")
        val productContent = intent.getStringExtra("content")
        val productImage = intent.getStringExtra("image")

        txt_name_product.text = productName
        txt_price_product.text = "${"قیمت: "}${productPrice}${"تومان"}"
        edt_content_product.setText(productContent)

        Glide.with(context)
            .load("${ApiClient.BASE_URL}${productImage}")
            .error(R.mipmap.ic_launcher)
            .placeholder(R.mipmap.ic_launcher_round)
            .into(image_product)*/
    }
}