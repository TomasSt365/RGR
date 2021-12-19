package com.github.tomasst365.rgr.view.pages.login

import android.annotation.SuppressLint
import android.os.Bundle
import android.text.InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.github.tomasst365.rgr.R
import com.github.tomasst365.rgr.data.User.Companion.INVALID_PASS_LIMIT
import com.github.tomasst365.rgr.data.Users
import com.github.tomasst365.rgr.data.UsersImpl
import com.github.tomasst365.rgr.data.UsersResponse
import com.github.tomasst365.rgr.databinding.FragmentLoginBinding
import com.github.tomasst365.rgr.timer.Timer
import com.github.tomasst365.rgr.timer.TimerListener
import com.github.tomasst365.rgr.view.Navigation
import com.github.tomasst365.rgr.view.pages.main.admin.AdminFragment
import com.github.tomasst365.rgr.view.pages.main.user.UserFragment

class LoginFragment : Fragment(), View.OnClickListener, TimerListener {
    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!

    private val navigation: Navigation by lazy {
        Navigation(requireActivity().supportFragmentManager)
    }

    private var isLoginCorrect = false

    companion object {
        const val TYPE_TEXT_VARIATION_PASSWORD = 0x00000081//скрытая переменная из класса InputType
        const val BLOCKING_TIME = 30 //seconds

        val users = UsersImpl().init(object : UsersResponse {
            @SuppressLint("NotifyDataSetChanged")
            override fun initialized(users: Users) {
            }

        }) as UsersImpl

        fun newInstance(): LoginFragment {
            val args = Bundle()

            val fragment = LoginFragment()
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLoginBinding.inflate(layoutInflater, container, false)

        initView()

        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun onClick(view: View?) {
        when (view) {
            binding.loginButton -> {
                val login = binding.loginEdit.text.toString()
                val password = binding.passwordEdit.text.toString()

                login(login, password)
            }
            binding.showPasswordCheck -> {
                binding.passwordEdit.inputType =
                    if (binding.showPasswordCheck.isChecked) {
                        TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
                    } else {
                        TYPE_TEXT_VARIATION_PASSWORD
                    }
            }
        }
    }

    /**Init work*/

    private fun initView() {
        binding.passwordEdit.inputType = TYPE_TEXT_VARIATION_PASSWORD
        binding.loginButton.setOnClickListener(this)
        binding.showPasswordCheck.setOnClickListener(this)
    }

    /**Login and verification work*/

    private fun login(login: String?, password: String?) {
        searchUser(login, password)
    }

    private fun searchUser(login: String?, password: String?) {
        var errText: String? = null

        for (position in 0 until users.size()) {
            errText = verification(position, login, password)
            if (isLoginCorrect) {
                break
            }
        }
        if (errText != null) {
            binding.errText.text = errText
        }
    }

    private fun verification(position: Int, login: String?, password: String?): String? {
        return when (users.getUser(position).login) {
            login -> {
                isLoginCorrect = true
                passwordVerification(position, password)

            }
            else -> {
                isLoginCorrect = false
                resources.getString(R.string.wrongLogin)
            }
        }

    }

    private fun passwordVerification(position: Int, password: String?): String? {
        return when (users.getUser(position).password) {
            password -> {
                startMainPage(position)
                null
            }
            else -> {
                with(users.getUser(position)) {
                    invalidPasswordCounter++
                    if (invalidPasswordCounter == INVALID_PASS_LIMIT) {
                        val timer = Timer(this@LoginFragment, binding.timer)
                        timer.setOnTimerListener(this@LoginFragment)
                        timer.start(BLOCKING_TIME)
                        invalidPasswordCounter = 0
                    }

                }

                resources.getString(R.string.wrongPassword)
            }
        }
    }

    override fun onTimeStart() {
        binding.loginButton.visibility = View.GONE
    }

    override fun onTimeOver() {
        binding.loginButton.visibility = View.VISIBLE
    }

    private fun startMainPage(position: Int) {
        val fragment = if (users.getUser(position).isAdmin) {
            AdminFragment.newInstance()
        } else {
            UserFragment.newInstance(position)
        }

        navigation.replaceFragment(
            fragment = fragment,
            containerId = R.id.mainContainer,
            addToBackStack = true
        )
    }

}