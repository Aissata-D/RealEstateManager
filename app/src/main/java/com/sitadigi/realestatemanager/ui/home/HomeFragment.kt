package com.sitadigi.realestatemanager.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.sitadigi.realestatemanager.R
import com.sitadigi.realestatemanager.databinding.FragmentHomeBinding
import com.sitadigi.realestatemanager.ui.DetailsPropertyFragment
import com.sitadigi.realestatemanager.ui.ListPropertyFragment

class HomeFragment : Fragment() {
   /* private var properties = listOf<Property>()
    private val viewModelJob = SupervisorJob()
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)
    private lateinit var propertyDao: PropertyDao*/
    private lateinit var listPropertyFragment : ListPropertyFragment
    private lateinit var detailsPropertyFragment : DetailsPropertyFragment

    private var _binding: FragmentHomeBinding? = null


    private val binding get() = _binding!!

  /* override fun onCreate(savedInstanceState: Bundle?) {
       super.onCreate(savedInstanceState)

   }*/
    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?): View {
        val homeViewModel =
                ViewModelProvider(this).get(HomeViewModel::class.java)

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
                val root: View = binding.root
        //val root= inflater.inflate(com.sitadigi.realestatemanager.R.layout.fragment_home, container, false)

        homeViewModel.text.observe(viewLifecycleOwner) {
          //  textView.text = it
        }

        configureAndShowMainFragment()
       configureAndShowDetailFragment()

        return root
    }

    private fun configureAndShowMainFragment() {
        // A - Get FragmentManager (Support) and Try to find existing instance of fragment in FrameLayout container
      //  listPropertyFragment = (getFragmentManager()?.findFragmentById(R.id.framLayout_list_property)
        //        as? ListPropertyFragment)!!

       // if (listPropertyFragment == null) {
            // B - Create new main fragment
            listPropertyFragment = ListPropertyFragment()
            // C - Add it to FrameLayout container
            getFragmentManager()?.beginTransaction()
                    ?.add(R.id.framLayout_list_property, listPropertyFragment)
                    ?.commit()
        //}
    }
    private fun configureAndShowDetailFragment() {
        // A - Get FragmentManager (Support) and Try to find existing instance of fragment in FrameLayout container
       // detailsPropertyFragment = getFragmentManager()?.findFragmentById(R.id.framLayout_detail_property)
         //      as DetailsPropertyFragment
        //if (detailsPropertyFragment == null) {
            // B - Create new main fragment
        if(getFragmentManager()?.findFragmentById(R.id.framLayout_detail_property)!= null) {
            detailsPropertyFragment = DetailsPropertyFragment()
            // C - Add it to FrameLayout container
            getFragmentManager()?.beginTransaction()
                    ?.add(R.id.framLayout_detail_property, detailsPropertyFragment)
                    ?.commit()
            //}
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}