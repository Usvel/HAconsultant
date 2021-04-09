package com.example.haconsultant

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.haconsultant.fragment.catalog.SearchFragment
import com.example.haconsultant.fragment.catalog.SearchFragmentInteractor
import com.example.haconsultant.fragment.home.HomeFragment
import com.example.haconsultant.fragment.home.HomeFragmentInteractor
import com.example.haconsultant.model.HomeCatalog
import com.example.haconsultant.model.Product

class MainActivity : AppCompatActivity(), HomeFragmentInteractor, SearchFragmentInteractor {

    private val homeFragment = HomeFragment()
    private val searchFragment = SearchFragment()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportFragmentManager.beginTransaction().replace(R.id.navHostContainer, homeFragment).commit()
        //setActionBar(findViewById(R.layout.app_bar2))

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
}