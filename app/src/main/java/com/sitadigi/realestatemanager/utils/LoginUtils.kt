package com.sitadigi.realestatemanager.utils

import android.content.Intent
import android.util.Log
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import com.sitadigi.realestatemanager.ui.MainActivity
import com.sitadigi.realestatemanager.R
import com.sitadigi.realestatemanager.model.UserEntity
import com.sitadigi.realestatemanager.ui.login.LoginViewModel
import com.sitadigi.realestatemanager.ui.login.RegisterFragment
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch



class LoginUtils (val loginViewModel : LoginViewModel, val activity: FragmentActivity?){

    private val viewModelJob = SupervisorJob()
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    // A deplacer
    private fun replaceWithRegisterFragment(fragment: Fragment? ){

        if (fragment == null) return else{
            val fm = activity?.supportFragmentManager//activity.fragmentManager
            val transaction = fm?.beginTransaction()
            transaction?.replace(R.id.framLayout_main_login, fragment)
            transaction?.commit()
        }

    }
    //Function triggered When the Login Button is Clicked, Via Binding.
    fun clickOnLoginButton(userEmail: String?, userPassword: String?) {

        if (userEmail != null && userPassword != null) {
            if (userEmail.isEmpty() || userPassword.isEmpty()) {
                // errorToast fill all fields
                Toast.makeText(activity?.applicationContext  ,
                        "Fill all fields", Toast.LENGTH_SHORT).show()

            } else {
                uiScope.launch {
                    //Verify if userEmail exist in database
                    val userToLogged = loginViewModel.getUserEmailAddress(userEmail)
                    if(userToLogged != null) {
                        val passwordToVerify = userToLogged.userPassword
                        if(passwordToVerify == userPassword){
                            val intent = Intent(activity, MainActivity::class.java)
                            intent.putExtra("USER_EMAIL",userEmail)
                            Log.e("TAG", "onCreateLoggin vers Main: email:"+userEmail )

                            activity?.startActivity(intent)
                        }else{
                            // write a valid password
                            Toast.makeText(activity?.applicationContext,
                                    "Give a correct Password", Toast.LENGTH_SHORT).show()
                        }
                    }else{
                        //Go to register fragment with(userEmail and userPassword)
                        replaceWithRegisterFragment(RegisterFragment())
                        Toast.makeText(activity?.applicationContext,
                                "New user? Register you", Toast.LENGTH_SHORT).show()

                    }
                }
            }
        }
    }

    fun clickOnRegisterButton(userEmail: String?,userPassword: String?, userName: String?,
                              userPhone: String?) {
        if (userEmail != null && userPassword != null && userName != null && userPhone != null) {
            if (userEmail.isEmpty() || userPassword.isEmpty() ||userName.isEmpty()
                    || userPhone.isEmpty()) {
                // fill all field
                Toast.makeText(activity?.applicationContext,
                        "Fill all fields", Toast.LENGTH_SHORT).show()
            } else {
                uiScope.launch {
                    // Check that email not exist in database
                    if(loginViewModel.getUserEmailAddress(userEmail)!=null){
                        Toast.makeText(activity?.applicationContext,
                                "This Email is already register ", Toast.LENGTH_SHORT).show()
                    }else {
                        loginViewModel.createUser(UserEntity(userEmail, userPassword, userName, userPhone))
                        // open application
                        if(loginViewModel.getUserEmailAddress(userEmail)!=null) {
                            Toast.makeText(activity?.applicationContext,
                                    "Registration Success ", Toast.LENGTH_SHORT).show()
                            val intent = Intent(activity, MainActivity::class.java)
                            intent.putExtra("USER_EMAIL",userEmail)
                            activity?.startActivity(intent)
                        }

                    }
                }
            }
        }

    }
}