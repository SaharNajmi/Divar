package com.example.divar.service

import com.example.divar.ui.view.MyImageView

interface ImageLoadingService {
    fun load(imageView: MyImageView, imageUrl: String)
}
