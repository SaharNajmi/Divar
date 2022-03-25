package com.example.divar.data.db.RoomDatabase

import io.reactivex.Completable
import io.reactivex.Single

interface FavRepository {
    fun getAllFav(): Single<List<FavoriteEntity>>
    fun add(fav: FavoriteEntity): Completable
    fun delete(fav: FavoriteEntity): Completable
}