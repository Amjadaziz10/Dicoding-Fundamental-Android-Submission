package com.amjad.amjadgithubuser.ui.detail

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2
import com.amjad.amjadgithubuser.R
import com.amjad.amjadgithubuser.databinding.ActivityDetailUserBinding
import com.amjad.amjadgithubuser.ui.menu.SettingActivity
import com.bumptech.glide.Glide
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DetailUserActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailUserBinding
    private lateinit var viewModel: DetailUserViewModel



    @SuppressLint("InflateParams")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailUserBinding.inflate(layoutInflater)
        setContentView(binding.root)


        val user = intent.getStringExtra(EXTRA_USERNAME)
        val id = intent.getIntExtra(EXTRA_ID, 0)
        val avatarUrl = intent.getStringExtra(EXTRA_AVATAR)
        val bundle = Bundle()
        bundle.putString(EXTRA_USERNAME, user)

        viewModel = ViewModelProvider(this)[DetailUserViewModel::class.java]
        if (user != null) {
            viewModel.setDetailUsers(user)
        }
        viewModel.getDetailUsers().observe(this){
            Glide.with(this)
                .load(it.avatarUrl)
                .circleCrop()
                .into(binding.detailPhoto)
            binding.detailUsername.text = it.login
            binding.detailFullname.text = it.name
            binding.detailCompany.text = it.company
            binding.detailLocation.text = it.location
            binding.detailFollower.text = it.followers
            binding.detailFollowing.text = it.following
            binding.detailRepository.text = it.publicRepos
        }

        var isChecked = false
        CoroutineScope(Dispatchers.IO).launch {
            val count = viewModel.checkUser(id)
            withContext(Dispatchers.Main){
                isChecked = if(count > 0){
                    binding.fabFavorite.setImageDrawable(ContextCompat.getDrawable(applicationContext, R.drawable.ic_favorite_red))
                    true
                }else{
                    binding.fabFavorite.setImageDrawable(ContextCompat.getDrawable(applicationContext, R.drawable.ic_favorite_grey))
                    false
                }
            }
        }

        binding.fabFavorite.setOnClickListener{
            isChecked = !isChecked
            if (isChecked){
                if (user != null) {
                    if (avatarUrl != null) {
                        viewModel.insertToFavorite(avatarUrl, id, user)
                    }
                    binding.fabFavorite.setImageDrawable(ContextCompat.getDrawable(applicationContext, R.drawable.ic_favorite_red))
                    Toast.makeText(this, "$user Added To Favorite", Toast.LENGTH_SHORT).show()
                }
            }else{
                viewModel.removeUserFromFavorite(id)
                binding.fabFavorite.setImageDrawable(ContextCompat.getDrawable(applicationContext, R.drawable.ic_favorite_grey))
                Toast.makeText(this, "$user Removed From Favorite", Toast.LENGTH_SHORT).show()
            }

        }


        val sectionsPagerAdapter = SectionsPagerAdapter(this, bundle)
        val viewPager: ViewPager2 = findViewById(R.id.view_Pager)
        viewPager.adapter = sectionsPagerAdapter
        val tabs: TabLayout = findViewById(R.id.tabLayout)
        TabLayoutMediator(tabs,viewPager){tab, position ->
            tab.text = resources.getString(TAB_TITLES[position])
        }.attach()

        for (i in 0 until binding.tabLayout.tabCount) {
            val tv = LayoutInflater.from(this)
                .inflate(R.layout.custom_tab, null) as TextView
            binding.tabLayout.getTabAt(i)!!.customView = tv
        }

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

    companion object{
        const val EXTRA_ID = "extra_id"
        const val EXTRA_USERNAME = "extra_username"
        const val EXTRA_AVATAR = "extra_avatar"
        @StringRes
        private val TAB_TITLES = intArrayOf(
            R.string.tab_text_1,
            R.string.tab_text_2
        )
    }
}