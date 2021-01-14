package model

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.squareup.picasso.Picasso

data class DetailModel(
    var title: String,
    var price: String,
    var description: String,
    var date: String
) {
    companion object {
        @BindingAdapter("android:loadImageURL")
        @JvmStatic
        fun loadImage(view: ImageView, imageUrl: String) {
            Picasso.with(view.context)
                .load(imageUrl)
                .into(view)
        }
    }

    constructor(
        title: String,
        price: String,
        description: String,
        date: String,
        tell: String,
        img1: String,
        img2: String,
        img3: String
    ) : this(title, price, description, date)
}