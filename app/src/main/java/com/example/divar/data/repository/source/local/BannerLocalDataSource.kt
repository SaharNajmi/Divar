package com.example.divar.data.repository.source.local

import androidx.room.*
import com.example.divar.data.db.dao.entities.Advertise
import com.example.divar.data.repository.source.BannerDataSource
import io.reactivex.Completable
import io.reactivex.Single

@Dao
interface BannerLocalDataSource : BannerDataSource {
    override fun getBanners(city: String, category: String): Single<List<Advertise>> {
        TODO("Not yet implemented")
    }

    @Query("SELECT * FROM banner ")
    override fun getFavorites(): Single<List<Advertise>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    override fun addToFavorites(banner: Advertise): Completable

    @Delete()
    override fun deleteFromFavorites(banner: Advertise): Completable
}