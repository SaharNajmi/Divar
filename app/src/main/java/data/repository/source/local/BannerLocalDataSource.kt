package data.repository.source.local

import data.model.AdModel
import data.repository.source.BannerDataSource
import io.reactivex.Single

class BannerLocalDataSource : BannerDataSource {
    override fun getAllBanner(city: String, category: String): Single<List<AdModel>> {
        TODO("Not yet implemented")
    }
}