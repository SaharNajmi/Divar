package commom

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import feature.home.BannerViewModel

//دسترسی به کانستراکتور BannerViewModel داشته باشیم ViewModelProvider.Factory
class MainViewModelFactory : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return BannerViewModel() as T
    }
}
