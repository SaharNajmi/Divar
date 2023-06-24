package com.example.divar.common

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.divar.R
import io.reactivex.disposables.CompositeDisposable


abstract class MyViewModel : ViewModel() {
    val compositeDisposable = CompositeDisposable()
    val progress = MutableLiveData<Boolean>()

    override fun onCleared() {
        compositeDisposable.clear()
        super.onCleared()
    }
}

abstract class MyFragment : Fragment(), MyProgressBarView {
    override val rootView: CoordinatorLayout?
        get() = view as CoordinatorLayout
    override val viewContext: Context?
        get() = context
}

abstract class MyActivity : AppCompatActivity(), MyProgressBarView {
    override val rootView: CoordinatorLayout?
        get() = window.decorView.rootView as CoordinatorLayout?
    override val viewContext: Context?
        get() = this
}

interface MyProgressBarView {
    val rootView: CoordinatorLayout?
    val viewContext: Context?

    fun setProgress(mustShow: Boolean) {
        //rootView!=null
        rootView?.let {
            viewContext?.let { context ->
                var loadView = it.findViewById<View>(R.id.loadingView)
                if (loadView == null && mustShow) {
                    loadView =
                        LayoutInflater.from(context).inflate(R.layout.view_loading, it, false)
                    it.addView(loadView)
                }
                loadView?.visibility = if (mustShow) View.VISIBLE else View.GONE
            }
        }
    }
}
