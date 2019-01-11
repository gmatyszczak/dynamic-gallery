package pl.gmat.dynamicgallery.gallery

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_gallery.*
import kotlinx.android.synthetic.main.fragment_photo.*
import pl.gmat.dynamicgallery.GallerySharedElementCallback
import pl.gmat.dynamicgallery.PhotosRepository
import pl.gmat.dynamicgallery.R

class GalleryActivity : AppCompatActivity() {

    companion object {

        const val EXTRA_POSITION = "EXTRA_POSITION"

        fun prepareIntent(context: Context, position: Int) = Intent(context, GalleryActivity::class.java).apply {
            putExtra(EXTRA_POSITION, position)
        }
    }

    private lateinit var photoFragmentAdapter: PhotoFragmentAdapter
    private val photosRepository = PhotosRepository()
    private val gallerySharedElementCallback = GallerySharedElementCallback()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_gallery)
        setEnterSharedElementCallback(gallerySharedElementCallback)
        supportPostponeEnterTransition()
        val startPosition = intent.getIntExtra(EXTRA_POSITION, 0)
        photoFragmentAdapter = PhotoFragmentAdapter(supportFragmentManager, photosRepository.photos, startPosition)
        viewPager.apply {
            adapter = photoFragmentAdapter
            currentItem = startPosition
        }
    }

    override fun onBackPressed() {
        gallerySharedElementCallback.sharedElement = photoFragmentAdapter.getFragment(viewPager.currentItem).imageView
        setResult(RESULT_OK, Intent().putExtra(EXTRA_POSITION, viewPager.currentItem))
        supportFinishAfterTransition()
    }
}