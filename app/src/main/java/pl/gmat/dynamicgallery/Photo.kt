package pl.gmat.dynamicgallery

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Photo(val id: Int,
                 val url: String) : Parcelable