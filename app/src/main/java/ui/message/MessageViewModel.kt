package ui.message

import androidx.lifecycle.MutableLiveData
import commom.MyViewModel
import data.model.ChatList
import data.repository.UserDataRepository
import io.reactivex.SingleObserver
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import timber.log.Timber

class MessageViewModel(
    val userDataRepository: UserDataRepository,
    val myPhone: String,
    val bannerId: Int
) : MyViewModel() {

    val userMessage = MutableLiveData<List<ChatList>>()


    init {
        getMessage()
    }

    fun getMessage() {
        userDataRepository.getMessage(myPhone, bannerId)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : SingleObserver<List<ChatList>> {
                override fun onSubscribe(d: Disposable) {
                    compositeDisposable.add(d)
                }

                override fun onSuccess(t: List<ChatList>) {
                    userMessage.value = t
                }

                override fun onError(e: Throwable) {
                    Timber.e(e)
                }

            })
    }
}