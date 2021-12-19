package com.github.tomasst365.rgr.view.pages.main.user

import android.annotation.SuppressLint
import android.os.Bundle
import android.text.InputType
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.github.tomasst365.rgr.R
import com.github.tomasst365.rgr.data.User
import com.github.tomasst365.rgr.databinding.FramgentUserBinding
import com.github.tomasst365.rgr.view.pages.login.LoginFragment
import com.github.tomasst365.rgr.view.pages.login.LoginFragment.Companion.users
import com.google.android.material.snackbar.Snackbar

class UserFragment : Fragment(), View.OnClickListener {
    private var _binding: FramgentUserBinding? = null
    private val binding get() = _binding!!

    private lateinit var currentUser: User
    private var currentPosition: Int = -1

    companion object {

        fun newInstance(position: Int): UserFragment {
            val args = Bundle()

            val fragment = UserFragment()
            fragment.arguments = args
            fragment.currentPosition = position
            fragment.currentUser = users.getUser(position)
            return fragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FramgentUserBinding.inflate(layoutInflater, container, false)

        initView()

        return binding.root
    }

    private fun initView() {
        binding.saveButton.setOnClickListener(this)
        binding.showPasswordCheck.setOnClickListener(this)
        binding.passwordEdit.inputType = LoginFragment.TYPE_TEXT_VARIATION_PASSWORD
    }

    @SuppressLint("ShowToast")
    override fun onClick(v: View?) {
        when (v) {
            binding.saveButton -> {
                currentUser.password = binding.passwordEdit.text.toString()
                users.editUser(currentPosition, currentUser)
                val msg = resources.getString(R.string.savedMsg)
                Snackbar.make(binding.root, msg, Snackbar.LENGTH_SHORT).show()
            }

            binding.showPasswordCheck -> {
                binding.passwordEdit.inputType =
                    if (binding.showPasswordCheck.isChecked) {
                        InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
                    } else {
                        LoginFragment.TYPE_TEXT_VARIATION_PASSWORD
                    }
            }
        }
    }
}