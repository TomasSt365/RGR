package com.github.tomasst365.rgr.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.github.tomasst365.rgr.R
import com.github.tomasst365.rgr.databinding.ActivityMainBinding
import com.github.tomasst365.rgr.view.pages.login.LoginFragment

class MainActivity : AppCompatActivity() {
    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding!!

    private val navigation by lazy {
        Navigation(supportFragmentManager)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        _binding = ActivityMainBinding.inflate(layoutInflater)

        navigation.addFragment(
            containerId = binding.mainContainer.id,
            fragment = LoginFragment.newInstance(),
            addToBackStack = false
        )
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}