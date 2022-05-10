package com.amjad.amjadgithubuser.ui.main

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.amjad.amjadgithubuser.api.ApiConfig
import com.amjad.amjadgithubuser.data.model.ItemsItem
import com.amjad.amjadgithubuser.data.model.UserResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UserViewModel : ViewModel() {
    val listUsers = MutableLiveData<ArrayList<ItemsItem>>()

    fun setSearchUsers(query: String){
        val client = ApiConfig.getApiService().getUser(query)
        client.enqueue(object : Callback<UserResponse>{
            override fun onResponse(
                call: Call<UserResponse>,
                response: Response<UserResponse>
            ){
                if(response.isSuccessful){
                    listUsers.postValue(response.body()?.items as ArrayList<ItemsItem>?)
                }
            }

            override fun onFailure(call: Call<UserResponse>, t: Throwable) {
                t.message?.let { Log.d("onFailure", it) }
            }
        })
    }
    fun getSearchUsers(): LiveData<ArrayList<ItemsItem>>{
        return listUsers
    }

}