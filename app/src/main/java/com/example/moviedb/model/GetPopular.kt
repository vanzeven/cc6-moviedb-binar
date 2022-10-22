package com.example.moviedb.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class GetPopular(
    @SerializedName("results")
    val movies : List<GetPopularItem>
) : Parcelable {
    constructor() : this(mutableListOf())
}