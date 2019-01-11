package pl.gmat.dynamicgallery.gallery

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import com.squareup.picasso.Callback
import kotlinx.android.synthetic.main.fragment_photo.*
import pl.gmat.dynamicgallery.DynamicGalleryApp
import pl.gmat.dynamicgallery.Photo
import pl.gmat.dynamicgallery.R

class PhotoFragment : Fragment() {

    companion object {

        private const val EXTRA_PHOTO = "EXTRA_PHOTO"
        private const val EXTRA_IS_ENTER_PHOTO = "EXTRA_IS_ENTER_PHOTO"

        fun newInstance(photo: Photo, isEnterPhoto: Boolean) = PhotoFragment().apply {
            arguments = bundleOf(EXTRA_PHOTO to photo, EXTRA_IS_ENTER_PHOTO to isEnterPhoto)
        }
    }

    private val picassoCallback = object : Callback {
        override fun onSuccess() = onImageLoaded()
        override fun onError(e: Exception?) = onImageLoaded()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View =
        inflater.inflate(R.layout.fragment_photo, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        postponeEnterTransition()
        val isEnterPhoto = arguments?.getBoolean(EXTRA_IS_ENTER_PHOTO, false) ?: false
        val picasso = (requireActivity().applicationContext as DynamicGalleryApp).picasso
        arguments?.getParcelable<Photo>(EXTRA_PHOTO)?.let {
            imageView.transitionName = it.url
            if (isEnterPhoto) {
                picasso.load(it.url)
                    .noFade()
                    .into(imageView, picassoCallback)
            } else {
                picasso.load(it.url)
                    .into(imageView)
            }
        }
    }

    private fun onImageLoaded() {
        activity?.supportStartPostponedEnterTransition()
    }
}