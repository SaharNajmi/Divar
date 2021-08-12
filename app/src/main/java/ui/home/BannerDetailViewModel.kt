package ui.home

import android.os.Bundle
import androidx.lifecycle.MutableLiveData
import commom.EXTRA_KEY_DATA
import commom.MyViewModel
import data.model.AdModel

class BannerDetailViewModel(val bundle: Bundle) : MyViewModel() {
    val bannerLiveData = MutableLiveData<AdModel>()

    init {
        bannerLiveData.value = bundle.getParcelable(EXTRA_KEY_DATA)
    }
}