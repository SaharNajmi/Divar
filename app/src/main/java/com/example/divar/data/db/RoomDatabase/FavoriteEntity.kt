package com.example.divar.data.db.RoomDatabase

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.bumptech.glide.Glide
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


@Entity(tableName = "RoomDataBase")
data class FavoriteEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Int = 0,
    @SerializedName("userID")
    @Expose
    var userID: Int = 0,
    @ColumnInfo(name = "isFavorite")
    var isFavorite: Boolean = false,
    @ColumnInfo(name = "title")
    var title: String = "",
    @ColumnInfo(name = "description")
    var description: String = "",
    @ColumnInfo(name = "price")
    var price: String = "",
    @ColumnInfo(name = "city")
    var city: String = "",
    @ColumnInfo(name = "category")
    var category: String = "",
    @ColumnInfo(name = "date")
    var date: String = "",
    @ColumnInfo(name = "img1")
    var img1: String = "",
    @ColumnInfo(name = "img2")
    var img2: String = "",
    @ColumnInfo(name = "img3")
    var img3: String = ""
) {
    companion object {
        @BindingAdapter("android:loadImageURL")
        @JvmStatic
        fun loadImage(view: ImageView, imageUrl: String) {
            Glide.with(view.context)
                .load(imageUrl)
                .into(view)
        }
    }

    constructor(id: Int, isFavorite: Boolean) : this(
        0,
        0,
        false,
        "",
        "",
        "",
        "",
        "",
        "",
        "",
        "",
        ""
    ) {
        this.id = id
        this.isFavorite = isFavorite
    }

}