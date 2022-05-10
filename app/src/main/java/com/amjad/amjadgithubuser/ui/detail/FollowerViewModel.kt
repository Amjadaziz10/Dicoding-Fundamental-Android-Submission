package com.amjad.amjadgithubuser.ui.detail

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.amjad.amjadgithubuser.api.ApiConfig
import com.amjad.amjadgithubuser.data.model.ItemsItem
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FollowerViewModel : ViewModel() {
    val listFollowers = MutableLiveData<ArrayList<ItemsItem>>()

    fun setFollower(username: String){
        val client = ApiConfig.getApiService().getFollowers(username)
        client.enqueue(object : Callback<ArrayList<ItemsItem>> {
            override fun onResponse(
                call: Call<ArrayList<ItemsItem>>,
                response: Response<ArrayList<ItemsItem>>
            ){
                if(response.isSuccessful){
                    listFollowers.postValue(response.body())
                }
            }

            override fun onFailure(call: Call<ArrayList<ItemsItem>>, t: Throwable) {
                t.message?.let { Log.d("onFailure", it) }
            }
        })
    }
    fun getFollower(): LiveData<ArrayList<ItemsItem>> {
        return listFollowers
    }
}