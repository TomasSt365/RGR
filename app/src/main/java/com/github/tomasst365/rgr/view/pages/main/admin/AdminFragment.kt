package com.github.tomasst365.rgr.view.pages.main.admin

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.github.tomasst365.rgr.databinding.FragmentAdminBinding
import com.github.tomasst365.rgr.view.pages.login.LoginFragment.Companion.users
import com.github.tomasst365.rgr.view.pages.main.admin.usersListAdapter.UsersListAdapter

class AdminFragment: Fragment() {
    private var _binding: FragmentAdminBinding? = null
    private val binding get() = _binding!!

    companion object{
        fun newInstance(): AdminFragment {
            val args = Bundle()

            val fragment = AdminFragment()
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAdminBinding.inflate(layoutInflater, container, false)

        initAdapter()

        return binding.root
    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }

    private fun initAdapter() {
        val adapter = UsersListAdapter()
        binding.usersList.adapter = adapter
        adapter.setUsers(users)
    }


}