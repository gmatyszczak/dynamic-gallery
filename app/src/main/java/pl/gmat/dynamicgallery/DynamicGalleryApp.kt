package pl.gmat.dynamicgallery

import android.app.Application
import com.squareup.leakcanary.LeakCanary
import com.squareup.picasso.LruCache
import com.squareup.picasso.Picasso

class DynamicGalleryApp : Application() {

    lateinit var picasso: Picasso
    lateinit var picassoCache: LruCache

    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.ENABLE_LEAK_CANARY) {
            if (LeakCanary.isInAnalyzerProcess(this)) return
            LeakCanary.install(this)
        }
        picassoCache = LruCache(this)
        picasso = Picasso.Builder(this).memoryCache(picassoCache).build()
    }
}