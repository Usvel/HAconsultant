package com.example.haconsultant

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.haconsultant.fragment.catalog.SearchFragment
import com.example.haconsultant.fragment.catalog.SearchFragmentInteractor
import com.example.haconsultant.fragment.home.HomeFragment
import com.example.haconsultant.fragment.home.HomeFragmentInteractor
import com.example.haconsultant.fragment.user.UserFragment
import com.example.haconsultant.fragment.user.UserFragmentInteractor
import com.example.haconsultant.model.HomeCatalog
import com.example.haconsultant.model.Product
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), HomeFragmentInteractor, SearchFragmentInteractor, UserFragmentInteractor {

    private val homeFragment = HomeFragment()
    private val searchFragment = SearchFragment()
    private val userFragment = UserFragment()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportFragmentManager.beginTransaction().replace(R.id.navHostContainer, homeFragment).commit()
        //setActionBar(findViewById(R.layout.app_bar2))
        bottomNavigation.setOnNavigationItemSelectedListener { item ->
            when (item.itemId){
                R.id.navigation_search ->{
                    supportFragmentManager.beginTransaction().replace(R.id.navHostContainer, homeFragment).commit()
                    true
                }
                R.id.navigation_catalog -> {
                    true
                }
                R.id.navigation_basket -> {
                    true
                }
                R.id.navigation_user -> {
                    supportFragmentManager.beginTransaction().replace(R.id.navHostContainer,userFragment).commit()
                    true
                }
                else -> false
            }
        }
        //startActivity(this, ScrollingActivity::class.java)
    }

    override fun onHomeOpenItem(product: Product) {
        Toast.makeText(this,product.name,Toast.LENGTH_SHORT).show()
    }

    override fun onHomeOpenCatalog(homeCatalog: HomeCatalog) {
        Toast.makeText(this,homeCatalog.name,Toast.LENGTH_SHORT).show()
    }

    override fun onHomeOpenCameraQrCode() {
        Toast.makeText(this,"QrCode",Toast.LENGTH_SHORT).show()
    }

    override fun onHomeOpenSerch() {
        supportFragmentManager.beginTransaction().addToBackStack(null).replace(R.id.navHostContainer, searchFragment).commit()
    }

    override fun onSearchOpenItem(product: Product) {
        Toast.makeText(this,product.name,Toast.LENGTH_SHORT).show()
    }

    override fun onSearchBack() {
        supportFragmentManager.popBackStack()
    }

    override fun onUserOpenCameraQrCode() {
        Toast.makeText(this,"QrCode",Toast.LENGTH_SHORT).show()
    }
}