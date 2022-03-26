package com.example.divar.data.repository.source

import com.example.divar.data.db.dao.entities.Advertise
import io.reactivex.Completable
import io.reactivex.Single

interface BannerDataSource {
    fun getBanners(city: String, category: String): Single<List<Advertise>>

    fun getFavorites(): Single<List<Advertise>>

    fun addToFavorites(banner: Advertise): Completable

    fun deleteFromFavorites(banner: Advertise): Completable
}