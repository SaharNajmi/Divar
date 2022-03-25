package com.example.divar.commom

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
    val progressLiveData = MutableLiveData<Boolean>()

    //ویو مدل به اتفاقات چرخه حیات اکتیویتی کاری  نداره فقط یک متد onCleared داره زمانی صدا زده میشه که اکتیوتی کلا finished یا از یبن بره- زمانی که ویو از بین بره صدا زده میشه
    // باید داخل متد onCleared کل درخواست هایی که به سرور فرستادیم کنسل کنیم و ویو توی حافطه نگهداری نشه چون ویو از بین رفته و نبیاد اینستنسی ازش ساخته بشه
    override fun onCleared() {
        //رتروفیت تمام رکوست های این ویو مدل را کنسل می کند
        compositeDisposable.clear()
        super.onCleared()
    }
}

abstract class MyFragment : Fragment(), MyProgressBarView {
    //پروگرس بار را داخل فرگمنت نشون بده
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

//برای همه ویو ها- هم اکتیویتی هم فرگمنت این اینترفیس رو ایپلمنت کنند
interface MyProgressBarView {
    //روت ویو هایی که قراره پروگرس نشان بده باید CoordinatorLayout باشه تا بتونیم لودینگ ویو(پروگرس بار) بهش اضافه کنیم
    val rootView: CoordinatorLayout?
    val viewContext: Context?

    //قبل از اینکه آیتم ها لود بشن پروگرس بار لود بشه-> لودینگ ویو را داخل روت ویو(فرگمنت یا اکتیویتی) اضافه کنه
    fun setProgress(mustShow: Boolean) {
        //اگر rootView خالی نبود
        rootView?.let {
            viewContext?.let { context ->
                // چک کن که روت ویو قبلا داخل CoordinatorLayout ست شده
                var loadView = it.findViewById<View>(R.id.loadingView)
                //اگر loadView بهCoordinatorLayout اضافه نشده بود آن را اضافه میکند
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
