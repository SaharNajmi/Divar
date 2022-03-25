package com.example.divar.data.db.RoomDatabase

import android.content.Context
import androidx.lifecycle.LiveData

class FavoriteRepository {
    var favoriteDao: FavoriteDao

    constructor(_data: FavoriteDao) {
        favoriteDao = _data
    }

    private fun initializeDB(context: Context): FavoriteRoomDB? {
        return FavoriteRoomDB.getAppDatabase(context)
    }

    //در  لیست علاقه مندی در دیتابیس چه جود داشت چه نداشت در هز صورت نمایش بده- دریافت لیست های آپدیت
    fun refreshListFavorite() {}

    fun getAllFav(): LiveData<List<FavoriteEntity>> = favoriteDao.getAllFavorite()


    fun insertFav(favorite: FavoriteEntity) = favoriteDao.addFavorite(favorite)


    fun deleteFav(favorite: FavoriteEntity) = favoriteDao.deleteFavorite(favorite)
}