package com.example.divar.ui.home

import androidx.lifecycle.MutableLiveData
import com.example.divar.common.MyViewModel
import com.example.divar.data.db.dao.entities.Advertise
import com.example.divar.data.repository.BannerRepository
import io.reactivex.SingleObserver
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import timber.log.Timber

class BannerViewModel(
    private val bannerRepository: BannerRepository,
    var city: String,
    var cate: String
) : MyViewModel() {

    val banners = MutableLiveData<List<Advertise>>()

    init {
        progress.value = true
        getBanners()
    }

    private fun getBanners() {
        bannerRepository.getBanners(city, cate)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doFinally { progress.value = false }
            .subscribe(object : SingleObserver<List<Advertise>> {
                override fun onSubscribe(d: Disposable) {
                    compositeDisposable.add(d)
                }

                override fun onSuccess(t: List<Advertise>) {
                    banners.value = t
                }

                override fun onError(e: Throwable) {
                    Timber.e(e)
                }
            })
    }

    fun changeCity(city: String) {
        this.city = city
        getBanners()
    }

    fun chaneCategory(category: String) {
        this.cate = category
        getBanners()
    }

    fun refresh() {
        getBanners()
    }
}