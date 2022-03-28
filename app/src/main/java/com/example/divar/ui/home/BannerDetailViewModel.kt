package com.example.divar.ui.home

import android.os.Bundle
import androidx.lifecycle.MutableLiveData
import com.example.divar.common.Constants.EXTRA_KEY_DATA
import com.example.divar.common.MyViewModel
import com.example.divar.data.db.dao.entities.Advertise

class BannerDetailViewModel(val bundle: Bundle) : MyViewModel() {
    val banners = MutableLiveData<Advertise>()

    init {
        banners.value = bundle.getParcelable(EXTRA_KEY_DATA)
    }
}