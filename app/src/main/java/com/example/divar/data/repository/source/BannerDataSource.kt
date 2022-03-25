package com.example.divar.data.repository.source

import com.example.divar.data.model.AdModel
import io.reactivex.Completable
import io.reactivex.Single

interface BannerDataSource {
    fun getAllBanner(city: String, category: String): Single<List<AdModel>>

    fun getFavorite(): Single<List<AdModel>>

    fun addToFavorites(banner: AdModel): Completable

    fun deleteFromFavorites(banner: AdModel): Completable
}