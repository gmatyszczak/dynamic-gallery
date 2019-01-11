package pl.gmat.dynamicgallery

import android.view.View
import android.widget.ImageView
import androidx.core.app.SharedElementCallback

class GallerySharedElementCallback : SharedElementCallback() {

    var sharedElement: ImageView? = null

    override fun onMapSharedElements(names: MutableList<String>, sharedElements: MutableMap<String, View>) {
        sharedElement?.let {
            val transitionName = it.transitionName
            if (transitionName != null) {
                names.clear()
                names.add(transitionName)
                sharedElements.clear()
                sharedElements[transitionName] = it
            }
        }
    }
}