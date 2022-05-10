package com.amjad.amjadgithubuser.ui.menu

import android.content.Intent
import android.content.res.Configuration
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.amjad.amjadgithubuser.R
import com.amjad.amjadgithubuser.data.local.FavoriteUser
import com.amjad.amjadgithubuser.data.model.ItemsItem
import com.amjad.amjadgithubuser.databinding.ActivityFavoriteBinding
import com.amjad.amjadgithubuser.ui.detail.DetailUserActivity
import com.amjad.amjadgithubuser.ui.main.ListUserAdapter

class FavoriteActivity : AppCompatActivity() {
    private lateinit var binding: ActivityFavoriteBinding
    private lateinit var adapter: ListUserAdapter
    private lateinit var viewModel: FavoriteViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavoriteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        adapter = ListUserAdapter()
        viewModel = ViewModelProvider(this)[FavoriteViewModel::class.java]
        binding.rvUser.setHasFixedSize(true)
        if (applicationContext.resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            binding.rvUser.layoutManager = GridLayoutManager(this, 2)
        } else {
            binding.rvUser.layoutManager = LinearLayoutManager(this)
        }
        binding.rvUser.adapter = adapter

        viewModel.getFavoriteUser().observe(this) {
            val list = mapList(it)
            adapter.setList(list)

        }

        adapter.setOnItemClickCallback(object : ListUserAdapter.OnItemClickCallback {
            override fun onItemClicked(data: ItemsItem) {
                showSelectedUser(data)
            }
        })
    }

    private fun mapList(users: List<FavoriteUser>): ArrayList<ItemsItem> {
        val listUser = ArrayList<ItemsItem>()
        for (user in users){
            val userMapped = ItemsItem(
                user.avatarUrl,
                user.id,
                user.login
            )
            listUser.add(userMapped)
        }
        return listUser
    }

    private fun showSelectedUser(user: ItemsItem) {
        val intent = Intent (this, DetailUserActivity::class.java)
        intent.putExtra(DetailUserActivity.EXTRA_USERNAME, user.login)
        intent.putExtra(DetailUserActivity.EXTRA_ID, user.id)
        intent.putExtra(DetailUserActivity.EXTRA_AVATAR, user.avatarUrl)
        startActivity(intent)
        Toast.makeText(this, user.login, Toast.LENGTH_SHORT).show()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.setting_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId){
            R.id.setting_menu -> {
                val i = Intent(this, SettingActivity::class.java)
                startActivity(i)
                true
            }
            else -> true
        }
    }
}

