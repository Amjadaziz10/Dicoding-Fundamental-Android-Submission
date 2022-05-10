package com.amjad.amjadgithubuser.data.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

data class UserResponse(

	@field:SerializedName("items")
	val items: List<ItemsItem>
)

@Parcelize
data class ItemsItem(
    @field:SerializedName("avatar_url")
    val avatarUrl: String?,

    @field:SerializedName("id")
    val id: Int,

    @field:SerializedName("login")
    val login: String?
): Parcelable
