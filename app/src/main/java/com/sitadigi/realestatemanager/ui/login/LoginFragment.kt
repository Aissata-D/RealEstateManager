package com.sitadigi.realestatemanager.ui.login

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.lifecycle.ViewModelProvider
import com.sitadigi.realestatemanager.R
import com.sitadigi.realestatemanager.utils.LoginUtils

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [LoginFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class LoginFragment : Fragment() {
    lateinit var etEmail: EditText
    lateinit var etPassword : EditText
    lateinit var btnLogin : Button
    private lateinit var loginViewModel: LoginViewModel
    private lateinit var loginUtils: LoginUtils

    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view  = inflater.inflate(R.layout.fragment_login, container, false);
        etEmail = view.findViewById(R.id.useremail) as EditText
        etPassword = view.findViewById(R.id.userpassword) as EditText
        btnLogin = view.findViewById(R.id.userlogin)

        loginViewModel = ViewModelProvider(requireActivity()).get(LoginViewModel::class.java)
        loginUtils = LoginUtils(loginViewModel,this.activity)

        btnLogin.setOnClickListener{
            clickOnLoginButton()

           // }else{
                //Toast.makeText(this.context, "NONONO", Toast.LENGTH_SHORT).show()


          //  }
            // Do some work here
        }
        return view
    }


    /*companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment LoginFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
                LoginFragment().apply {
                    arguments = Bundle().apply {
                        putString(ARG_PARAM1, param1)
                        putString(ARG_PARAM2, param2)
                    }
                }
    }*/
    fun clickOnLoginButton() {
        val userEmail : String? = etEmail.text.toString()
        val userPassword : String? = etPassword.text.toString()
       loginUtils.clickOnLoginButton(userEmail,userPassword)

    }
    private fun replaceWithRegisterFragment(fragment: Fragment?){

        if (fragment == null) return
        val fm = activity?.supportFragmentManager
        val transaction = fm?.beginTransaction()
        transaction?.replace(R.id.framLayout_main_login, fragment)
        transaction?.commit()

    }
}