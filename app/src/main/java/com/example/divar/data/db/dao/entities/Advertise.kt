package com.example.divar.data.db.dao.entities

import android.os.Parcelable
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.bumptech.glide.Glide
import com.example.divar.R
import com.example.divar.common.Constants.BASE_URL
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Entity(tableName = "banner")
@Parcelize
data class Advertise(
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
            Glide.with(view.context)
                .load("$BASE_URL${imageUrl}")
                .error(R.drawable.ic_image)
                .into(view)
        }
    }
}