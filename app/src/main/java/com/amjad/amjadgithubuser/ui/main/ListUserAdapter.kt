package com.amjad.amjadgithubuser.ui.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.amjad.amjadgithubuser.data.model.ItemsItem
import com.amjad.amjadgithubuser.databinding.UserRowBinding
import com.bumptech.glide.Glide

class ListUserAdapter: RecyclerView.Adapter<ListUserAdapter.ListViewHolder>() {
    private lateinit var onItemClickCallback: OnItemClickCallback
    private var list = ArrayList<ItemsItem>()

    fun setList(users: ArrayList<ItemsItem>){
        val oldList = list
        val diffResult: DiffUtil.DiffResult = DiffUtil.calculateDiff(
            ItemDiffCallback(
                oldList,
                users
            )
        )
        list = users
        diffResult.dispatchUpdatesTo(this)
    }

    class ItemDiffCallback(
        private var oldItemList: ArrayList<ItemsItem>,
        private var newItemList: ArrayList<ItemsItem>
    ): DiffUtil.Callback(){
        override fun getOldListSize(): Int {
            return oldItemList.size
        }

        override fun getNewListSize(): Int {
            return newItemList.size
        }

        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return (oldItemList[oldItemPosition].login == newItemList[newItemPosition].login)
        }

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldItemList[oldItemPosition] == newItemList[newItemPosition]
        }

    }

    inner class ListViewHolder(private val binding: UserRowBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(user: ItemsItem){
            Glide.with(itemView)
                .load(user.avatarUrl)
                .circleCrop()
                .into(binding.imgItemPhoto)
            binding.tvUserName.text = user.login
            binding.root.setOnClickListener {
                onItemClickCallback.onItemClicked(user)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val view = UserRowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ListViewHolder(view)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        holder.bind(list[position])
    }

    override fun getItemCount(): Int = list.size

    interface OnItemClickCallback {
        fun onItemClicked(data: ItemsItem)
    }

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }



}