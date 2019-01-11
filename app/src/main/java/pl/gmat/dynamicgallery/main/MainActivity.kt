package pl.gmat.dynamicgallery.main

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityOptionsCompat
import androidx.core.view.doOnPreDraw
import androidx.recyclerview.widget.GridLayoutManager
import kotlinx.android.synthetic.main.activity_main.*
import pl.gmat.dynamicgallery.DynamicGalleryApp
import pl.gmat.dynamicgallery.GallerySharedElementCallback
import pl.gmat.dynamicgallery.PhotosRepository
import pl.gmat.dynamicgallery.R
import pl.gmat.dynamicgallery.gallery.GalleryActivity

class MainActivity : AppCompatActivity() {

    private lateinit var photoAdapter: PhotoAdapter
    private lateinit var gridLayoutManager: GridLayoutManager
    private val repository = PhotosRepository()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        photoAdapter = PhotoAdapter(::onItemClick, (applicationContext as DynamicGalleryApp).picasso)
        gridLayoutManager = GridLayoutManager(this@MainActivity, 2)
        recyclerView.apply {
            layoutManager = gridLayoutManager
            adapter = photoAdapter
        }
        photoAdapter.submitList(repository.photos)
    }

    private fun onItemClick(position: Int) {
        val view = gridLayoutManager.findViewByPosition(position) as ImageView
        val options = ActivityOptionsCompat.makeSceneTransitionAnimation(this, view, repository.photos[position].url)
        startActivity(GalleryActivity.prepareIntent(this, position), options.toBundle())
    }

    override fun onActivityReenter(resultCode: Int, data: Intent?) {
        data?.getIntExtra(GalleryActivity.EXTRA_POSITION, 0)?.let { position ->
            val sharedElementCallback = GallerySharedElementCallback()
            setExitSharedElementCallback(sharedElementCallback)
            supportPostponeEnterTransition()
            recyclerView.scrollToPosition(position)
            recyclerView.doOnPreDraw {
                sharedElementCallback.sharedElement = gridLayoutManager.findViewByPosition(position) as ImageView
                supportStartPostponedEnterTransition()
                setExitSharedElementCallback(null as GallerySharedElementCallback?)
            }
        }
    }
}
