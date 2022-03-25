package com.example.divar.ui.favorite

import androidx.lifecycle.MutableLiveData
import com.example.divar.commom.MyViewModel
import com.example.divar.data.model.AdModel
import com.example.divar.data.repository.BannerDataRepository
import io.reactivex.CompletableObserver
import io.reactivex.SingleObserver
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import timber.log.Timber

class FavoriteViewModel(val bannerDataRepository: BannerDataRepository) : MyViewModel() {
    val favoritebannerLiveData = MutableLiveData<List<AdModel>>()

    init {
        //show ProgressBar before load item
        progressLiveData.value = true
        getFavorite()
    }

    fun getFavorite() {
        bannerDataRepository.getFavorite()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doFinally {
                //not show ProgressBar after load item
                progressLiveData.value = false
            }
            .subscribe(object : SingleObserver<List<AdModel>> {
                override fun onSubscribe(d: Disposable) {
                    compositeDisposable.add(d)
                }

                override fun onSuccess(t: List<AdModel>) {
                    favoritebannerLiveData.postValue(t)
                }

                override fun onError(e: Throwable) {
                    Timber.e(e)
                }
            })
    }

    fun refresh() {
        getFavorite()
    }

    fun deleteFavorite(banner: AdModel) {
        bannerDataRepository.deleteFromFavorites(banner)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : CompletableObserver {
                override fun onSubscribe(d: Disposable) {
                    compositeDisposable.add(d)
                }

                override fun onComplete() {
                    Timber.i("success delete from favorite")
                }

                override fun onError(e: Throwable) {
                    Timber.e(e)
                }
            })

    }

    fun addFavorite(banner: AdModel) {
        bannerDataRepository.addToFavorites(banner)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : CompletableObserver {
                override fun onSubscribe(d: Disposable) {
                    compositeDisposable.add(d)
                }

                override fun onComplete() {
                    Timber.i("success add to favorite")
                }

                override fun onError(e: Throwable) {
                    Timber.e(e)
                }
            })
    }
}