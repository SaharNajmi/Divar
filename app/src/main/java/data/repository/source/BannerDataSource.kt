package data.repository.source

import data.model.AdModel
import io.reactivex.Single

interface BannerDataSource {
    fun getAllBanner(city: String, category: String): Single<List<AdModel>>
}