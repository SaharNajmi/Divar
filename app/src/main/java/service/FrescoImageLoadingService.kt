package service

import com.facebook.drawee.view.SimpleDraweeView
import ui.view.MyImageView

class FrescoImageLoadingService : ImageLoadingService {
    override fun load(imageView: MyImageView, imageUrl: String) {
        if (imageView is SimpleDraweeView)
            imageView.setImageURI(imageUrl)
        else
            throw IllegalStateException("imageView must be instance of SimpleDraweeView")
    }
}
