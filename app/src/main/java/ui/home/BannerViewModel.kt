package ui.home

import androidx.lifecycle.MutableLiveData
import commom.MyViewModel
import data.model.AdModel
import data.repository.BannerDataRepository
import io.reactivex.SingleObserver
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import timber.log.Timber

class BannerViewModel(
    val bannerDataRepository: BannerDataRepository,
    var city: String,
    var cate: String
) : MyViewModel() {

    //get Banner
    val bannerLiveData = MutableLiveData<List<AdModel>>()

    init {
        progressLiveData.value = true
        getBanner()
    }

    fun getBanner() {
        bannerDataRepository.getAllBanner(city, cate)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doFinally { progressLiveData.value = false }
            .subscribe(object : SingleObserver<List<AdModel>> {
                override fun onSubscribe(d: Disposable) {
                    compositeDisposable.add(d)
                }

                override fun onSuccess(t: List<AdModel>) {
                    bannerLiveData.value = t
                }

                override fun onError(e: Throwable) {
                    Timber.e(e)
                }
            })
    }

    fun changeCity(city: String) {
        this.city = city
        getBanner()
    }

    fun chaneCategory(category: String) {
        this.cate = category
        getBanner()
    }

    fun refresh() {
        getBanner()
    }


}