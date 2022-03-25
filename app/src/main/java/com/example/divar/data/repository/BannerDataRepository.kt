package com.example.divar.data.repository

import com.example.divar.data.model.AdModel
import com.example.divar.data.repository.source.BannerDataSource
import com.example.divar.data.repository.source.local.BannerLocalDataSource
import com.example.divar.data.repository.source.remote.BannerRemoteDataSource
import io.reactivex.Completable
import io.reactivex.Single

class BannerDataRepository(
    val bannerLocalDataSource: BannerLocalDataSource,
    val bannerRemoteDataSource: BannerRemoteDataSource
) : BannerDataSource {
    //موقعی که لیست از سرور گرفته شد باید بررسی کنیم که چه آیتم هایی را در جدول علاقه مندی ها داریم(با گرفتن ایدی آنها)
    //  سپس بررسی میکنیم محصولاتی که از سرور اومدن کدومشون توی ایدی علاقه مندی ها وجود داره
    override fun getAllBanner(city: String, category: String): Single<List<AdModel>> =
        bannerLocalDataSource.getFavorite().flatMap { fav ->
            bannerRemoteDataSource.getAllBanner(city, category).doOnSuccess {
                //ایدی تمامی علاقه مندی ها را میگیریم
                //map موقعی ک بخایم فقط تعداد خاصی از موجودیت ها را بگیریم: مثلا فقط ای دی ها بگیریم
                val favIds = fav.map {
                    //آرایه ای از لیست علاقه مندی ها
                    it.id
                }
                //برای هر کدوم از آیتم هایی که از سرور اومده چک کنه کنه که ایا این ایدی در لیست عاقه مندی ها است اگه بود یعنی در لیست علاقه مندی ها است
                it.forEach { banner ->
                    if (favIds.contains(banner.id))
                        banner.favorite = true
                }
            }
        }

    override fun getFavorite(): Single<List<AdModel>> = bannerLocalDataSource.getFavorite()

    override fun addToFavorites(banner: AdModel): Completable =
        bannerLocalDataSource.addToFavorites(banner)

    override fun deleteFromFavorites(banner: AdModel): Completable =
        bannerLocalDataSource.deleteFromFavorites(banner)
}