package com.amjad.amjadgithubuser.api

import com.amjad.amjadgithubuser.BuildConfig
import com.amjad.amjadgithubuser.data.model.DetailUserResponse
import com.amjad.amjadgithubuser.data.model.ItemsItem
import com.amjad.amjadgithubuser.data.model.UserResponse
import retrofit2.Call
import retrofit2.http.*

interface ApiService {
    @GET("search/users")
    @Headers("Authorization: token " + BuildConfig.GITHUB_TOKEN)
    fun getUser(
        @Query("q") query: String
    ): Call<UserResponse>

    @GET("users/{username}")
    @Headers("Authorization: token " + BuildConfig.GITHUB_TOKEN)
    fun getDetailUser(
        @Path("username") username: String
    ): Call<DetailUserResponse>

    @GET("users/{username}/followers")
    @Headers("Authorization: token " + BuildConfig.GITHUB_TOKEN)
    fun getFollowers(
        @Path("username") username: String
    ): Call<ArrayList<ItemsItem>>

    @GET("users/{username}/following")
    @Headers("Authorization: token " + BuildConfig.GITHUB_TOKEN)
    fun getFollowing(
        @Path("username") username: String
    ): Call<ArrayList<ItemsItem>>
}