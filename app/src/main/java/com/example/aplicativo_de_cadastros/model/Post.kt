package com.example.aplicativo_de_cadastros.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Post(
    val id: Int = 0,
    val title: String,
    val body: String
) : Parcelable
