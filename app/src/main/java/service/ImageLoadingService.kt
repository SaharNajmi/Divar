package service

import ui.view.MyImageView

interface ImageLoadingService {
    fun load(imageView: MyImageView, imageUrl: String)
}
