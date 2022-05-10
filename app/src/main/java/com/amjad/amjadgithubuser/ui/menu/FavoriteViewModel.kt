package com.amjad.amjadgithubuser.ui.menu

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.amjad.amjadgithubuser.data.local.FavoriteUser
import com.amjad.amjadgithubuser.data.local.FavoriteUserDao
import com.amjad.amjadgithubuser.data.local.FavoriteUserDatabase

class FavoriteViewModel(application: Application): AndroidViewModel(application) {
    private var favDao: FavoriteUserDao

    init {
        val favDb = FavoriteUserDatabase.getDatabase(application)
        favDao = favDb.favoriteUserDao()
    }

    fun getFavoriteUser(): LiveData<List<FavoriteUser>>{
        return favDao.getFavoriteUser()
    }
}