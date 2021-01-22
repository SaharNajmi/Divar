package viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import api.ApiClient
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.observers.DisposableSingleObserver
import io.reactivex.rxjava3.schedulers.Schedulers
import model.AdModel

class BannerViewModel : ViewModel() {

    //MutableLiveData
    private var listMutableLiveData = MutableLiveData<ArrayList<AdModel>>()

    //مدیریت درخواست رکوست به سمت سرور compositeDisposable
    private val compositeDisposable = CompositeDisposable()
    lateinit var api: ApiClient

    fun getListMutableLiveData(city: String, cate: String): MutableLiveData<ArrayList<AdModel>> {
        listMutableLiveData = MutableLiveData()
        api = ApiClient()
        compositeDisposable.add(
            api.getAllBanners(city, cate)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<ArrayList<AdModel>>() {
                    override fun onSuccess(banner: ArrayList<AdModel>?) {
                        listMutableLiveData.value = banner
                    }

                    override fun onError(e: Throwable?) {
                        Log.d("error live data!!!", e.toString())
                    }
                })
        )
        return listMutableLiveData
    }

    override fun onCleared() {
        compositeDisposable.clear()
        super.onCleared()
    }

}