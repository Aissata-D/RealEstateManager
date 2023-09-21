package com.sitadigi.realestatemanager.ui.login

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.sitadigi.realestatemanager.databinding.ActivityLoginBinding

import com.sitadigi.realestatemanager.R

class LoginActivity : AppCompatActivity() {

    private lateinit var loginViewModel: LoginViewModel
    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)


        val factory = LoginViewModelFactory(this)

        loginViewModel = ViewModelProvider(this, factory).get(LoginViewModel::class.java)

        //return binding.root
        if (savedInstanceState == null) {
            addFragmentToActivity(LoginFragment())
        }
    }

    private fun addFragmentToActivity(fragment: Fragment?){

        if (fragment == null) return
        val fm = supportFragmentManager
        val transaction = fm.beginTransaction()
        transaction.add(R.id.framLayout_main_login, fragment)
        transaction.commit()

    }
    fun showFragment(fragment: Fragment){
        val fram = supportFragmentManager.beginTransaction()
        fram.add(R.id.framLayout_main_login,fragment)
        fram.commit()
    }
}