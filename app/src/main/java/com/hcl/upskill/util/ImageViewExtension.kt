package com.hcl.upskill.util

import android.graphics.drawable.Drawable
import android.view.View
import android.widget.ProgressBar
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.DecodeFormat
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.Target
import com.google.android.material.imageview.ShapeableImageView
import com.hcl.upskill.R

fun ShapeableImageView.loadProfile(url: Any?,progressBar: ProgressBar?) {
    progressBar?.visibility= View.VISIBLE
    Glide.with(context).load(url).listener (object : RequestListener<Drawable> {

        override fun onResourceReady(resource: Drawable?, model: Any?, target: Target<Drawable>?, dataSource: DataSource?, isFirstResource: Boolean): Boolean {
            progressBar?.visibility= View.GONE
            return false
        }
        override fun onLoadFailed(e: GlideException?, model: Any?, target: Target<Drawable>?, isFirstResource: Boolean): Boolean {
            progressBar?.visibility= View.GONE
            return false
        }



    }).apply(RequestOptions().format(DecodeFormat.DEFAULT).diskCacheStrategy(DiskCacheStrategy.NONE)).placeholder(
        R.drawable.user_thumb).into(this)


}