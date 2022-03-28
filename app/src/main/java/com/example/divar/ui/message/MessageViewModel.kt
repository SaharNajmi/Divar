package com.example.divar.ui.message

import androidx.lifecycle.MutableLiveData
import com.example.divar.common.MyViewModel
import com.example.divar.data.model.Chat
import com.example.divar.data.repository.UserRepository
import io.reactivex.SingleObserver
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import timber.log.Timber

class MessageViewModel(
    private val userRepository: UserRepository,
    private val myPhone: String,
    private val bannerId: Int
) : MyViewModel() {

    val userMessage = MutableLiveData<List<Chat>>()


    init {
        progress.value = true
        getMessage()
    }

    fun getMessage() {
        userRepository.getMessage(myPhone, bannerId)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doFinally { progress.value = false }
            .subscribe(object : SingleObserver<List<Chat>> {
                override fun onSubscribe(d: Disposable) {
                    compositeDisposable.add(d)
                }

                override fun onSuccess(t: List<Chat>) {
                    userMessage.value = t
                }

                override fun onError(e: Throwable) {
                    Timber.e(e)
                }

            })
    }
}