package com.sitadigi.realestatemanager.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.ViewModelProvider
import com.sitadigi.realestatemanager.R
import com.sitadigi.realestatemanager.databinding.FragmentHomeBinding
import com.sitadigi.realestatemanager.ui.AddPropertyFragment
import com.sitadigi.realestatemanager.ui.DetailsPropertyFragment
import com.sitadigi.realestatemanager.ui.ListPropertyFragment


class HomeFragment : Fragment() {
   /* private var properties = listOf<Property>()
    private val viewModelJob = SupervisorJob()
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)
    private lateinit var propertyDao: PropertyDao*/
  //  private lateinit var listPropertyFragment : ListPropertyFragment
    //private lateinit var detailsPropertyFragment : DetailsPropertyFragment
     var mListPropertyFragment: ListPropertyFragment? = null
     var mAddPropertyFragment: AddPropertyFragment? = null
     var mDetailPropertyFragment: DetailsPropertyFragment? =null

    private var _binding: FragmentHomeBinding? = null
    val CONFIG = "CONFIG"
    val PHONE = "PHONE"
    val TABLET= "TABLET"

   // lateinit var mAddPropertyFragment: AddPropertyFragment
   // lateinit var mDetailPropertyFragment: DetailsPropertyFragment
   // lateinit var mListPropertyFragment: ListPropertyFragment
   // lateinit var mView: View


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

        homeViewModel.text.observe(viewLifecycleOwner) {
          //  textView.text = it
        }

     // configureAndShowListFragment(root)
      configureDispalyingFragmentListAndDetailAndAdd(root)


        return root
    }

    private fun configureAndShowListFragment(root: View) {
        mListPropertyFragment = ListPropertyFragment()
        // C - Add it to FrameLayout container
        activity?.supportFragmentManager?.beginTransaction()
            ?.add(R.id.framLayout_list_property, mListPropertyFragment!!)
           // ?.addToBackStack(null)
            ?.commit()

        val frameLayout: FrameLayout? = root.findViewById(R.id.framLayout_detail_or_add_property)

        if (frameLayout != null) {
            mAddPropertyFragment = AddPropertyFragment()
            // C - Add it to FrameLayout container
            activity?.supportFragmentManager?.beginTransaction()
                ?.add(R.id.framLayout_detail_or_add_property, mAddPropertyFragment!!)
                //?.addToBackStack(null)
                //?.addToBackStack(AddPropertyFragment::class.java.getSimpleName())

                ?.commit()
        }
    }
    private fun configureDispalyingFragmentListAndDetailAndAdd(root: View){
        val transaction: FragmentTransaction? = activity?.supportFragmentManager?.beginTransaction()
        //Initialise fragment to get framLayout_add_or_detail according Fragment displayed
        //Initialise fragment to get framLayout_add_or_detail according Fragment displayed
        val fragment: Fragment? = activity?.supportFragmentManager?.findFragmentById(R.id.framLayout_detail_or_add_property)
        if (fragment is AddPropertyFragment) {
            mAddPropertyFragment = fragment as AddPropertyFragment
        } else if(fragment != null)  {
            mDetailPropertyFragment = fragment as DetailsPropertyFragment
        }
        // Initialise fragment to get framLayout_list_meeting according Fragment displayed
        // Initialise fragment to get framLayout_list_meeting according Fragment displayed
        val fragment1: Fragment? = activity?.supportFragmentManager
            ?.findFragmentById(R.id.framLayout_list_property)
        if (fragment1 is ListPropertyFragment) {
            mListPropertyFragment = fragment1 as ListPropertyFragment
        } else if(fragment != null) {
            mDetailPropertyFragment = fragment1 as DetailsPropertyFragment
        }

        if (root.findViewById<View>(R.id.framLayout_detail_or_add_property) == null) {
            if (mListPropertyFragment == null && mDetailPropertyFragment == null) {
               mListPropertyFragment  = ListPropertyFragment()
                val bundle = Bundle()
                bundle.putString(CONFIG, PHONE)
                mListPropertyFragment!!.setArguments(bundle)
                transaction?.add(R.id.framLayout_list_property, mListPropertyFragment!!)
            }
        } else {
            if (mListPropertyFragment == null) {
                mListPropertyFragment = ListPropertyFragment()
                val bundle = Bundle()
                bundle.putString(CONFIG, TABLET)
                mListPropertyFragment!!.setArguments(bundle)
                transaction?.add(R.id.framLayout_list_property, mListPropertyFragment!!)
            }
            if (mAddPropertyFragment == null && mDetailPropertyFragment == null) {
                mAddPropertyFragment = AddPropertyFragment()
                val bundle = Bundle()
                bundle.putString(CONFIG, TABLET)
                mAddPropertyFragment!!.setArguments(bundle)
                transaction?.add(R.id.framLayout_detail_or_add_property, mAddPropertyFragment!!)
            }
        }
        transaction?.commit()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}