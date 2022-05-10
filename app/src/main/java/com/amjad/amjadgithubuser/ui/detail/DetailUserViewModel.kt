package com.amjad.amjadgithubuser.ui.detail

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.amjad.amjadgithubuser.api.ApiConfig
import com.amjad.amjadgithubuser.data.local.FavoriteUser
import com.amjad.amjadgithubuser.data.local.FavoriteUserDao
import com.amjad.amjadgithubuser.data.local.FavoriteUserDatabase
import com.amjad.amjadgithubuser.data.model.DetailUserResponse
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailUserViewModel(application: Application) : AndroidViewModel(application) {
    val user = MutableLiveData<DetailUserResponse>()

    private var favDao: FavoriteUserDao

    init {
        val favDb = FavoriteUserDatabase.getDatabase(application)
        favDao = favDb.favoriteUserDao()
    }

    fun setDetailUsers(username: String){
        val client = ApiConfig.getApiService().getDetailUser(username)
        client.enqueue(object : Callback<DetailUserResponse> {
            override fun onResponse(
                call: Call<DetailUserResponse>,
                response: Response<DetailUserResponse>
            ){
                if(response.isSuccessful){
                    user.postValue(response.body())
                }
            }

            override fun onFailure(call: Call<DetailUserResponse>, t: Throwable) {
                t.message?.let { Log.d("onFailure", it) }
            }

        })
    }
    fun getDetailUsers(): LiveData<DetailUserResponse> {
        return user
    }

    fun insertToFavorite(
        avatarUrl: String,
        id:Int,
        username: String
    ) {
        CoroutineScope(Dispatchers.IO).launch {
            val user = FavoriteUser(avatarUrl, id, username)
            favDao.insertToFavorite(user)
        }
    }

    suspend fun checkUser(id: Int) = favDao.checkUser(id)

    fun removeUserFromFavorite(id: Int){
        CoroutineScope(Dispatchers.IO).launch {
            favDao.removeUserFromFavorite(id)
        }
    }


}