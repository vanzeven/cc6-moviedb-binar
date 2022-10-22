package com.example.moviedb.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "account")
data class AccountEntity (
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,
    var username: String? = "",
    var email: String? = "",
    var password: String?
) : Parcelable