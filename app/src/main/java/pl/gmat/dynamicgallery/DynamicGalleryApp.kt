package pl.gmat.dynamicgallery

import android.app.Application
import com.squareup.picasso.LruCache
import com.squareup.picasso.Picasso

class DynamicGalleryApp : Application() {

    lateinit var picasso: Picasso
    lateinit var picassoCache: LruCache

    override fun onCreate() {
        super.onCreate()
        picassoCache = LruCache(this)
        picasso = Picasso.Builder(this).memoryCache(picassoCache).build()
    }
}