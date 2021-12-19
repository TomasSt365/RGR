package com.github.tomasst365.rgr.view.pages.main.admin.usersListAdapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.github.tomasst365.rgr.data.Users
import com.github.tomasst365.rgr.databinding.UserItemBinding

class UsersListAdapter :
    RecyclerView.Adapter<UsersListAdapter.ViewHolder>() {

    private var users: Users? = null

    @SuppressLint("NotifyDataSetChanged")
    fun setUsers(users: Users) {
        this.users = users
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemBinding: UserItemBinding = UserItemBinding.inflate(LayoutInflater.from(parent.context))

        return ViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.itemBinding.login.text = users!!.getUser(position).login
        holder.itemBinding.password.text = users!!.getUser(position).password
    }

    override fun getItemCount(): Int {
        return users!!.size()
    }

    inner class ViewHolder(val itemBinding: UserItemBinding) :
        RecyclerView.ViewHolder(itemBinding.root)
}