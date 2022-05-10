package com.amjad.amjadgithubuser.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable


@Entity(tableName = "favorite_user")
data class FavoriteUser(
    val avatarUrl: String,

    @PrimaryKey
    val id: Int,

    val login: String
): Serializable