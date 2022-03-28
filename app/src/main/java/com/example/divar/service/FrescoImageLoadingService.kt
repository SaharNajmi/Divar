package com.example.divar.service

import com.example.divar.ui.view.MyImageView
import com.facebook.drawee.view.SimpleDraweeView

class FrescoImageLoadingService : ImageLoadingService {
    override fun load(imageView: MyImageView, imageUrl: String) {
        if (imageView is SimpleDraweeView)
            imageView.setImageURI(imageUrl)
        else
            throw IllegalStateException("imageView must be instance of SimpleDraweeView")
    }
}
