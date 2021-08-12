package commom

import androidx.lifecycle.ViewModel
import io.reactivex.disposables.CompositeDisposable


abstract class MyViewModel : ViewModel() {
    val compositeDisposable = CompositeDisposable()
    override fun onCleared() {
        //رتروفیت تمام رکوست های این ویو مدل را کنسل می کند
        compositeDisposable.clear()
        super.onCleared()
    }
}