package com.amjad.amjadgithubuser.ui.main

import android.content.Intent
import android.content.res.Configuration
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.KeyEvent
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.amjad.amjadgithubuser.R
import com.amjad.amjadgithubuser.ui.detail.DetailUserActivity
import com.amjad.amjadgithubuser.data.model.ItemsItem
import com.amjad.amjadgithubuser.databinding.ActivityMainBinding
import com.amjad.amjadgithubuser.ui.menu.FavoriteActivity
import com.amjad.amjadgithubuser.ui.menu.SettingActivity

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: UserViewModel
    private lateinit var adapter: ListUserAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        adapter = ListUserAdapter()
        viewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory())[UserViewModel::class.java]
        binding.rvUser.setHasFixedSize(true)
        if (applicationContext.resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            binding.rvUser.layoutManager = GridLayoutManager(this, 2)
        } else {
            binding.rvUser.layoutManager = LinearLayoutManager(this)
        }
        binding.rvUser.adapter = adapter
        binding.etSearch.setOnKeyListener{_, keyCode, event ->
            if(event.action == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_ENTER){
                searchUser()
                return@setOnKeyListener true
            }
            return@setOnKeyListener false
        }
        viewModel.getSearchUsers().observe(this) {
            adapter.setList(it)
            showLoading(false)
        }
        adapter.setOnItemClickCallback(object : ListUserAdapter.OnItemClickCallback {
            override fun onItemClicked(data: ItemsItem) {
                showSelectedUser(data)
           }
        })
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.option_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId){
            R.id.favorite_menu -> {
                val i = Intent(this, FavoriteActivity::class.java)
                startActivity(i)
                true
            }
            R.id.setting_menu -> {
                val i = Intent(this, SettingActivity::class.java)
                startActivity(i)
                true
            }
            else -> true
        }
    }

    private fun showLoading(state: Boolean){
        if (state){
            binding.pBar.visibility = View.VISIBLE
        }else{
            binding.pBar.visibility = View.INVISIBLE
        }
    }
    private fun searchUser(){
        val query = binding.etSearch.text.toString()
        if(query.isEmpty()) return
        showLoading(true)
        viewModel.setSearchUsers(query)
    }

    private fun showSelectedUser(user: ItemsItem) {
        val intent = Intent (this@MainActivity, DetailUserActivity::class.java)
        intent.putExtra(DetailUserActivity.EXTRA_AVATAR, user.avatarUrl)
        intent.putExtra(DetailUserActivity.EXTRA_ID, user.id)
        intent.putExtra(DetailUserActivity.EXTRA_USERNAME, user.login)
        startActivity(intent)
        Toast.makeText(this, user.login + " Selected" , Toast.LENGTH_SHORT).show()
    }


}

