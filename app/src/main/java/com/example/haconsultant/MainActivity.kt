package com.example.haconsultant

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.haconsultant.fragment.home.HomeFragment
import com.example.haconsultant.fragment.home.HomeFragmentInteractor
import com.example.haconsultant.model.HomeCatalog
import com.example.haconsultant.model.Product

class MainActivity : AppCompatActivity(), HomeFragmentInteractor {

    private val homeFragment = HomeFragment()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportFragmentManager.beginTransaction().replace(R.id.navHostContainer, homeFragment).commit()
        //setActionBar(findViewById(R.layout.app_bar2))

        //startActivity(this, ScrollingActivity::class.java)
    }

    override fun onOpenItem(product: Product) {
        Toast.makeText(this,product.name,Toast.LENGTH_SHORT).show()
    }

    override fun onOpenCatalog(homeCatalog: HomeCatalog) {
        Toast.makeText(this,homeCatalog.name,Toast.LENGTH_SHORT).show()
    }

    override fun onOpenCameraQrCode() {
        Toast.makeText(this,"QrCode",Toast.LENGTH_SHORT).show()
    }

    override fun onOpenSerch() {
        Toast.makeText(this,"Open Serch", Toast.LENGTH_SHORT).show()
    }
}