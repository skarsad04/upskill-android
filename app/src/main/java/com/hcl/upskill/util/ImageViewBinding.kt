package com.hcl.upskill.util

import android.widget.ProgressBar
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.google.android.material.imageview.ShapeableImageView

class ImageViewBinding {

        companion object {
            @BindingAdapter(value = ["loadUrl", "progressbar"], requireAll = true)
            @JvmStatic
            fun loadUrl(view: ShapeableImageView, url: String?,progressbar: ProgressBar?) {
                view.loadProfile(url,progressbar)
            }
        }
}