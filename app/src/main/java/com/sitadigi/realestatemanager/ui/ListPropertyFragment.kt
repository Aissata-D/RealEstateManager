package com.sitadigi.realestatemanager.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.sitadigi.realestatemanager.R
import com.sitadigi.realestatemanager.dao.PropertyDao
import com.sitadigi.realestatemanager.database.UserDatabase
import com.sitadigi.realestatemanager.model.Property
import com.sitadigi.realestatemanager.utils.PropertyRecyclerViewCustom
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [ListPropertyFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ListPropertyFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private var properties = listOf<Property>()
    private val viewModelJob = SupervisorJob()
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)
    private lateinit var propertyDao: PropertyDao
    lateinit var recyclerView: RecyclerView
    lateinit var custom: PropertyRecyclerViewCustom

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
        val v = inflater.inflate(R.layout.fragment_list_property, container, false)

        recyclerView = v.findViewById(R.id.recyclerview)
        recyclerView.layoutManager = LinearLayoutManager(this.context)

        if(this.context !=null) {
            propertyDao = UserDatabase.getInstance(this.context)?.propertyDao!!
            custom = PropertyRecyclerViewCustom(this.requireContext(),null,0)

        }
        initRecyclerView()

        return v
    }

    private fun initRecyclerView(){
        // set up the RecyclerView
        // set up the RecyclerView

        uiScope.launch {
            properties = propertyDao.getAllProperty()


            val adapter = PropertyRecyclerviewAdapter(properties,custom)
            // adapter.setClickListener(this)
            recyclerView.adapter = adapter
        }
    }


    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment ListPropertyFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
                ListPropertyFragment().apply {
                    arguments = Bundle().apply {
                        putString(ARG_PARAM1, param1)
                        putString(ARG_PARAM2, param2)
                    }
                }
    }
}