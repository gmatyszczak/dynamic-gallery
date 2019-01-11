package pl.gmat.dynamicgallery.gallery

import android.util.SparseArray
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import pl.gmat.dynamicgallery.Photo

class PhotoFragmentAdapter(fragmentManager: FragmentManager,
                           private val items: List<Photo>,
                           private var startPosition: Int)
    : FragmentStatePagerAdapter(fragmentManager) {

    private val fragments: SparseArray<PhotoFragment> = SparseArray()

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val fragment = super.instantiateItem(container, position) as PhotoFragment
        fragments.put(position, fragment)
        return fragment
    }

    override fun destroyItem(container: ViewGroup, position: Int, fragment: Any) {
        fragments.remove(position)
        super.destroyItem(container, position, fragment)
    }

    fun getFragment(position: Int) = fragments.get(position)

    override fun getItem(position: Int): PhotoFragment {
        val isEnterPhoto = position == startPosition
        if (isEnterPhoto) {
            startPosition = -1
        }
        return PhotoFragment.newInstance(items[position], isEnterPhoto)
    }

    override fun getCount() = items.size
}