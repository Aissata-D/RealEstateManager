package com.sitadigi.realestatemanager.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.sitadigi.realestatemanager.dao.PropertyDao
import com.sitadigi.realestatemanager.database.UserDatabase
import com.sitadigi.realestatemanager.databinding.FragmentHomeBinding
import com.sitadigi.realestatemanager.model.Property
import com.sitadigi.realestatemanager.ui.PropertyRecyclerviewAdapter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch

//import fr.sitadigi.realestatemanager.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {
    private var properties = listOf<Property>()
    private val viewModelJob = SupervisorJob()
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)
    private lateinit var propertyDao: PropertyDao

    private var _binding: FragmentHomeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    lateinit var recyclerView: RecyclerView

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?): View {
        val homeViewModel =
                ViewModelProvider(this).get(HomeViewModel::class.java)

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root
         recyclerView = binding.recyclerview
        recyclerView.layoutManager = LinearLayoutManager(this.context)

        if(this.context !=null) {
            propertyDao = UserDatabase.getInstance(this.context)?.propertyDao!!
        }
        //val textView: TextView = binding.textHome
        homeViewModel.text.observe(viewLifecycleOwner) {
          //  textView.text = it
        }


        initRecyclerView()

        return root
    }
    private fun initRecyclerView(){
        // set up the RecyclerView
        // set up the RecyclerView

        uiScope.launch {
            properties = propertyDao.getAllProperty()


        val adapter = PropertyRecyclerviewAdapter(properties)
        // adapter.setClickListener(this)
        recyclerView.adapter = adapter
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}