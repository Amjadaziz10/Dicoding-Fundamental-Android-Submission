package com.amjad.amjadgithubuser.data.local

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query


@Dao
interface FavoriteUserDao {
    @Insert
    suspend fun insertToFavorite(favoriteUser: FavoriteUser)

    @Query("Select * From favorite_user")
    fun getFavoriteUser(): LiveData<List<FavoriteUser>>

    @Query("Select count(*) From favorite_user Where favorite_user.id = :id")
    suspend fun checkUser(id: Int): Int

    @Query("Delete from favorite_user Where favorite_user.id = :id")
    suspend fun removeUserFromFavorite(id: Int): Int
}