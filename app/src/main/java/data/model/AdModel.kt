package data.model

import android.os.Parcelable
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import com.squareup.picasso.Picasso
import commom.BASE_URL
import kotlinx.android.parcel.Parcelize

@Entity(tableName = "banner")
//Parcelize برای اینکه بتونیم مقادیر را ارسال کنیم
@Parcelize
data class AdModel(
    @PrimaryKey(autoGenerate = false)
    @SerializedName("id")
    @Expose
    var id: Int = 0,
    @SerializedName("title")
    @Expose
    var title: String = "",

    @SerializedName("description")
    @Expose
    var description: String = "",

    @SerializedName("price")
    @Expose
    var price: String = "",

    @SerializedName("userID")
    @Expose
    var userID: Int = 0,

    @SerializedName("tell")
    @Expose
    var tell: String = "",
    @SerializedName("city")
    @Expose
    var city: String = "",

    @SerializedName("category")
    @Expose
    var category: String = "",

    @SerializedName("img1")
    @Expose
    var img1: String = "",

    @SerializedName("img2")
    @Expose
    var img2: String = "",

    @SerializedName("img3")
    @Expose
    var img3: String = "",

    @SerializedName("date")
    @Expose
    var date: String = "",

    @SerializedName("status")
    @Expose
    var status: String = "",

    var favorite: Boolean = false
) : Parcelable {
    companion object {
        @BindingAdapter("android:loadImage")
        @JvmStatic
        fun loadImage(view: ImageView, imageUrl: String) {
            Picasso.with(view.context)
                .load("$BASE_URL${imageUrl}")
                .into(view)
        }
    }
}