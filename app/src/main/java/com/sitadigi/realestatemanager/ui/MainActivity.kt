package com.sitadigi.realestatemanager.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.navigation.NavigationView
import com.google.android.material.snackbar.Snackbar
import com.sitadigi.realestatemanager.R
import com.sitadigi.realestatemanager.databinding.ActivityMainBinding

//import fr.sitadigi.realestatemanager.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() , AddPropertyFragment.interfaceClicOnButtonAddImage{
    override fun OnButtonClickedListener(view: View?) {
//
        Log.e("TAG", "OnButtonClickedListener:MAinActivity " )
    }

    //private lateinit var  imgSearch : ImageView
    //private lateinit var  imgEdit : ImageView
    //private lateinit var  imgAdd : ImageView
    var userEmail = ""
    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding
    lateinit var mAddPropertyFragment: AddPropertyFragment


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        val bundle = intent.extras

        if (bundle != null) {
            val s = bundle["USER_EMAIL"] as String?
            if (s != null) {
                userEmail = s
                Log.e("TAG", "onCreateMAIN: email: $userEmail, bundle : $bundle" )

            }
        }
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.appBarMain.toolbar)

        binding.appBarMain.iconSearch.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
        }
        binding.appBarMain.iconEdit.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
        }
        binding.appBarMain.iconAdd.setOnClickListener {
        /* val intentAddProperty = Intent(this, AddPropertyActivity::class.java)
                    //.apply {
                //putExtra(EXTRA_MESSAGE, message) }
            intentAddProperty.putExtra("USER_EMAIL",userEmail)
            Log.e("TAG", "onCreateMAIN vers Add: email "+userEmail )
            startActivity(intentAddProperty)*/
            val frameLayout: FrameLayout? = findViewById(R.id.framLayout_detail_or_add_property)
            val bundle1 = Bundle()
            if (frameLayout != null) {
                mAddPropertyFragment = AddPropertyFragment()

                bundle1.putString("USER_EMAIL",userEmail)
                mAddPropertyFragment.setArguments(bundle)
                //  Add it to FrameLayout container

                supportFragmentManager.beginTransaction()
                    .replace(R.id.framLayout_detail_or_add_property, mAddPropertyFragment)
                    .addToBackStack(null)
               // .addToBackStack(AddPropertyFragment::class.java.getSimpleName())
                    .commit()
            }else{
                mAddPropertyFragment = AddPropertyFragment()

                bundle1.putString("USER_EMAIL",userEmail)
                mAddPropertyFragment.setArguments(bundle)
                //  Add it to FrameLayout container
                supportFragmentManager.beginTransaction()
                    //.remove()
                    .replace(R.id.framLayout_list_property, mAddPropertyFragment)
                   // .addToBackStack(null)
                   // .addToBackStack(AddPropertyFragment::class.java.getSimpleName())
                    .commit()

            }
        }

        binding.appBarMain.fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
        }
        val drawerLayout: DrawerLayout = binding.drawerLayout
        val navView: NavigationView = binding.navView
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        appBarConfiguration = AppBarConfiguration(setOf(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_logout), drawerLayout)
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)



    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        addPropertyUtils.checkActivityResult(requestCode,resultCode, data)


        /* if (requestCode == AUTOCOMPLETE_REQUEST_CODE) {
             when (resultCode) {
                 Activity.RESULT_OK -> {
                     data?.let {
                         val place = Autocomplete.getPlaceFromIntent(data)
                         propertyLocality =  place.addressComponents.asList().get(2).name
                         Log.e("TAG", "Place: ${place.name}, ${place.id}," +
                                 place.addressComponents.asList().get(2).name)
                         tvAddress.text=place.address
                     }
                 }
                 AutocompleteActivity.RESULT_ERROR -> {
                     // TODO: Handle the error.
                     data?.let {
                         val status = Autocomplete.getStatusFromIntent(data)
                         Log.e("TAG ERROR", status.statusMessage ?: "")
                     }
                 }
                 Activity.RESULT_CANCELED -> {
                     Log.e("TAG", "onActivityResult: CANCELED" )
                     // The user canceled the operation.
                 }
             }
             return
         }*/
        super.onActivityResult(requestCode, resultCode, data)
    }

}