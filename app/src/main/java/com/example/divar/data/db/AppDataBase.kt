package com.example.divar.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.divar.data.model.AdModel
import com.example.divar.data.repository.source.local.BannerLocalDataSource

//اضافه کردن موجودیت های دیتابیس
//abstract به خاطر اینکه  روم قراره متد ها رو ایمپلمنت کنه

@Database(entities = [AdModel::class], version = 1, exportSchema = false)
abstract class AppDataBase : RoomDatabase() {
    abstract fun bannerDao(): BannerLocalDataSource
}