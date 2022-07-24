package com.sitadigi.realestatemanager.ui.login

import android.os.Bundle
import androidx.fragment.app.Fragment
//import android.app.Fragment
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
 * Use the [RegisterFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class RegisterFragment : Fragment() {
    lateinit var etEmail: EditText
    lateinit var etPassword : EditText
    lateinit var etName : EditText
    lateinit var etPhoneNumber : EditText
    lateinit var btnRegister : Button
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
        val view = inflater.inflate(R.layout.fragment_register, container, false)
        etEmail = view.findViewById(R.id.user_email) as EditText
        etPassword = view.findViewById(R.id.user_password) as EditText
        etName = view.findViewById(R.id.user_name) as EditText
        etPhoneNumber = view.findViewById(R.id.user_phone_number) as EditText
        btnRegister = view.findViewById(R.id.user_resgister) as Button

        val context = this.activity?.applicationContext

        //val dao = context?.let { UserDatabase.getInstance(it).userDatabaseDao }

        //val repository = dao?.let { LoginRepository(it) }

        //val factory = repository?.let { this.activity?.let { it1 -> LoginViewModelFactory(it, it1) } }

        //loginViewModel = factory?.let { ViewModelProvider(this, it).get(LoginViewModel::class.java) }!!
        loginViewModel = ViewModelProvider(requireActivity()).get(LoginViewModel::class.java)
        loginUtils = LoginUtils(loginViewModel,this.activity)
        btnRegister.setOnClickListener{
            clickOnRegisterButton()
        }

        return view
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment RegisterFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
                RegisterFragment().apply {
                    arguments = Bundle().apply {
                        putString(ARG_PARAM1, param1)
                        putString(ARG_PARAM2, param2)
                    }
                }
    }

    fun clickOnRegisterButton() {
        val userEmail : String = etEmail.text.toString()
        val userName : String = etName.text.toString()
        val userPhone : String = etPhoneNumber.text.toString()
        val userPassword : String = etPassword.text.toString()

        loginUtils.clickOnRegisterButton(userEmail,userPassword,userName,userPhone)
    }
}