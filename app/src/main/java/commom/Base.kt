package commom

import androidx.lifecycle.ViewModel
import io.reactivex.disposables.CompositeDisposable


abstract class MyViewModel : ViewModel() {
    val compositeDisposable = CompositeDisposable()

    //ویو مدل به اتفاقات چرخه حیات اکتیویتی کاری  نداره فقط یک متد onCleared داره زمانی صدا زده میشه که اکتیوتی کلا finished یا از یبن بره- زمانی که ویو از بین بره صدا زده میشه
    // باید داخل متد onCleared کل درخواست هایی که به سرور فرستادیم کنسل کنیم و ویو توی حافطه نگهداری نشه چون ویو از بین رفته و نبیاد اینستنسی ازش ساخته بشه
    override fun onCleared() {
        //رتروفیت تمام رکوست های این ویو مدل را کنسل می کند
        compositeDisposable.clear()
        super.onCleared()
    }
}