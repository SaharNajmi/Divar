package com.example.divar.ui.home

import android.os.Bundle
import androidx.lifecycle.MutableLiveData
import com.example.divar.commom.EXTRA_KEY_DATA
import com.example.divar.commom.MyViewModel
import com.example.divar.data.model.AdModel

class BannerDetailViewModel(val bundle: Bundle) : MyViewModel() {
    val bannerLiveData = MutableLiveData<AdModel>()

    init {
        bannerLiveData.value = bundle.getParcelable(EXTRA_KEY_DATA)
    }
}