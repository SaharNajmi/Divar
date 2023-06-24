package com.example.divar.ui.favorite

import androidx.lifecycle.MutableLiveData
import com.example.divar.common.MyViewModel
import com.example.divar.data.db.dao.entities.Advertise
import com.example.divar.data.repository.BannerRepository
import io.reactivex.CompletableObserver
import io.reactivex.SingleObserver
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import timber.log.Timber

class FavoriteViewModel(private val bannerRepository: BannerRepository) : MyViewModel() {
    val banners = MutableLiveData<List<Advertise>>()

    init {
        progress.value = true
        getFavorite()
    }

    fun getFavorite() {
        bannerRepository.getFavorites()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doFinally {
                progress.value = false
            }
            .subscribe(object : SingleObserver<List<Advertise>> {
                override fun onSubscribe(d: Disposable) {
                    compositeDisposable.add(d)
                }

                override fun onSuccess(t: List<Advertise>) {
                    banners.postValue(t)
                }

                override fun onError(e: Throwable) {
                    Timber.e(e)
                }
            })
    }

    fun deleteFavorite(banner: Advertise) {
        bannerRepository.deleteFromFavorites(banner)
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

    fun addFavorite(banner: Advertise) {
        bannerRepository.addToFavorites(banner)
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