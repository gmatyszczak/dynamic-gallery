package pl.gmat.dynamicgallery.main

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_photo.*
import pl.gmat.dynamicgallery.Photo
import pl.gmat.dynamicgallery.R

private val diffCallback = object : DiffUtil.ItemCallback<Photo>() {

    override fun areItemsTheSame(oldItem: Photo, newItem: Photo) = oldItem === newItem

    override fun areContentsTheSame(oldItem: Photo, newItem: Photo) = oldItem == newItem
}

class PhotoAdapter(private val onItemClick: (Int) -> Unit,
                   private val picasso: Picasso)
    : ListAdapter<Photo, PhotoViewHolder>(diffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = PhotoViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.item_photo, parent, false),
        onItemClick,
        picasso
    )

    override fun onBindViewHolder(holder: PhotoViewHolder, position: Int) = holder.bind(getItem(position))
}

class PhotoViewHolder(override val containerView: View,
                      private val onItemClick: (Int) -> Unit,
                      private val picasso: Picasso)
    : RecyclerView.ViewHolder(containerView), LayoutContainer {

    fun bind(photo: Photo) {
        imageView.transitionName = photo.url
        picasso.load(photo.url).into(imageView)
        itemView.setOnClickListener { onItemClick(adapterPosition) }
    }
}