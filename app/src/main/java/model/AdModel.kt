package model

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import com.squareup.picasso.Picasso

class AdModel {
    companion object {
        @BindingAdapter("android:loadImage")
        @JvmStatic
        fun loadImage(view: ImageView, imageUrl: String) {
            Picasso.with(view.context)
                .load(imageUrl)
                .into(view)
        }
    }
    @SerializedName("id")
    @Expose
    var id: Int = 0

    @SerializedName("title")
    @Expose
    var title: String = ""

    @SerializedName("description")
    @Expose
    var description: String = ""

    @SerializedName("price")
    @Expose
    var price: String = ""

    @SerializedName("tell")
    @Expose
    var tell: String = ""

    @SerializedName("city")
    @Expose
    var city: String = ""

    @SerializedName("category")
    @Expose
    var category: String = ""

    @SerializedName("img1")
    @Expose
    var img1: String = ""

    @SerializedName("img2")
    @Expose
    var img2: String = ""

    @SerializedName("img3")
    @Expose
    var img3: String = ""

    @SerializedName("date")
    @Expose
    var date: String = ""

    @SerializedName("status")
    @Expose
    var status: String = ""

}