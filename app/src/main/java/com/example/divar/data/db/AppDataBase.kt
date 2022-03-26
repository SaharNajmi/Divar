package com.example.divar.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.divar.data.db.dao.entities.Advertise
import com.example.divar.data.repository.source.local.BannerLocalDataSource

@Database(entities = [Advertise::class], version = 1, exportSchema = false)
abstract class AppDataBase : RoomDatabase() {
    abstract fun bannerDao(): BannerLocalDataSource
}