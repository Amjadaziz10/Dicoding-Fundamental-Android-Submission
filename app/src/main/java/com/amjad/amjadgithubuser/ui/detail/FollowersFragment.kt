package com.amjad.amjadgithubuser.ui.detail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.amjad.amjadgithubuser.data.model.ItemsItem
import com.amjad.amjadgithubuser.databinding.FragmentFollowBinding
import com.amjad.amjadgithubuser.ui.main.ListUserAdapter

class FollowersFragment : Fragment() {
    private var _binding: FragmentFollowBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: FollowerViewModel
    private lateinit var adapter: ListUserAdapter
    private lateinit var username: String

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val arg = arguments
        username = arg?.getString(DetailUserActivity.EXTRA_USERNAME).toString()

        adapter = ListUserAdapter()
        binding.rvUserFollow.setHasFixedSize(true)
        binding.rvUserFollow.layoutManager = LinearLayoutManager(activity)
        binding.rvUserFollow.adapter = adapter
        showLoading(true)
        viewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory())[FollowerViewModel::class.java]
        viewModel.setFollower(username)
        viewModel.getFollower().observe(viewLifecycleOwner) {
            if (it != null) {
                adapter.setList(it)
                showLoading(false)
            }
        }
        adapter.setOnItemClickCallback(object : ListUserAdapter.OnItemClickCallback {
            override fun onItemClicked(data: ItemsItem) {
                Toast.makeText(activity, data.login, Toast.LENGTH_SHORT).show()
            }
        })
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFollowBinding.inflate(inflater, container, false)
        return binding.root
    }

    private fun showLoading(state: Boolean) {
        if (state) {
            binding.pBar.visibility = View.VISIBLE
        } else {
            binding.pBar.visibility = View.INVISIBLE
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}