package com.example.divar.data.repository.source.local

import androidx.room.*
import com.example.divar.data.model.AdModel
import com.example.divar.data.repository.source.BannerDataSource
import io.reactivex.Completable
import io.reactivex.Single

@Dao
interface BannerLocalDataSource : BannerDataSource {
    override fun getAllBanner(city: String, category: String): Single<List<AdModel>> {
        TODO("Not yet implemented")
    }

    @Query("SELECT * FROM banner ")
    override fun getFavorite(): Single<List<AdModel>>

    //قبل از اضافه کردن چک میکنه ک اگه آیتمی با این ایدی از قبل موجود بود اونو جاگذاری قبلی کنه
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    override fun addToFavorites(banner: AdModel): Completable

    @Delete()
    override fun deleteFromFavorites(banner: AdModel): Completable
}