package com.example.haconsultant

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.example.haconsultant.fragment.basket.BasketFragment
import com.example.haconsultant.fragment.basket.BasketFragmentInteractor
import com.example.haconsultant.fragment.basket.BasketStatus
import com.example.haconsultant.fragment.basket.BasketViewModel
import com.example.haconsultant.fragment.catalog.SearchFragment
import com.example.haconsultant.fragment.catalog.SearchFragmentInteractor
import com.example.haconsultant.fragment.home.HomeFragment
import com.example.haconsultant.fragment.home.HomeFragmentInteractor
import com.example.haconsultant.fragment.product.ProductFragment
import com.example.haconsultant.fragment.product.ProductFragmentInteractor
import com.example.haconsultant.fragment.user.UserFragment
import com.example.haconsultant.fragment.user.UserFragmentInteractor
import com.example.haconsultant.model.HomeCatalog
import com.example.haconsultant.model.HomeData
import com.example.haconsultant.model.Product
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), HomeFragmentInteractor, SearchFragmentInteractor,
    UserFragmentInteractor, BasketFragmentInteractor,
    ProductFragmentInteractor {

    private val homeFragment = HomeFragment()
    private val searchFragment = SearchFragment()
    private val userFragment = UserFragment()
    private val basketFragment = BasketFragment()
    private var productFragment = ProductFragment()

    val basketViewModel: BasketViewModel by lazy {
        ViewModelProvider(this).get(BasketViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //setActionBar(findViewById(R.layout.app_bar2))
        bottomNavigation.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.navigation_search -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.navHostContainer, homeFragment).commit()
                    true
                }
                R.id.navigation_catalog -> {
                    true
                }
                R.id.navigation_basket -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.navHostContainer, basketFragment).commit()
                    true
                }
                R.id.navigation_user -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.navHostContainer, userFragment).commit()
                    true
                }
                else -> false
            }
        }
        supportFragmentManager.beginTransaction().replace(R.id.navHostContainer, homeFragment)
            .commit()
        //startActivity(this, ScrollingActivity::class.java)
    }

    override fun onHomeOpenItem(product: Product) {
        val flag = flagItemInBasket(product)
        productFragment = ProductFragment.newInstance(product, flag)
        supportFragmentManager.beginTransaction().addToBackStack(null).replace(
            R.id.navHostContainer,
            productFragment
        ).commit()
    }

    override fun onHomeOpenCatalog(homeCatalog: HomeCatalog) {
        Toast.makeText(this, homeCatalog.name, Toast.LENGTH_SHORT).show()
    }

    override fun onHomeOpenCameraQrCode() {
        Toast.makeText(this, "QrCode", Toast.LENGTH_SHORT).show()
    }

    override fun onHomeOpenSerch() {
        supportFragmentManager.beginTransaction().addToBackStack(null)
            .replace(R.id.navHostContainer, searchFragment).commit()
    }

    override fun onSearchOpenItem(product: Product) {
        val flag = flagItemInBasket(product)
        productFragment = ProductFragment.newInstance(product, flag)
        supportFragmentManager.beginTransaction().addToBackStack(null).replace(
            R.id.navHostContainer,
            productFragment
        ).commit()
    }

    override fun onSearchBack() {
        supportFragmentManager.popBackStack()
    }

    override fun onUserOpenCameraQrCode() {
        Toast.makeText(this, "QrCode", Toast.LENGTH_SHORT).show()
    }

    override fun onBasketOpenItem(product: Product) {
        val flag = flagItemInBasket(product)
        productFragment = ProductFragment.newInstance(product, flag)
        supportFragmentManager.beginTransaction().addToBackStack(null).replace(
            R.id.navHostContainer,
            productFragment
        ).commit()
    }

    override fun onBasketCheckout() {
        Toast.makeText(this, "Checkout", Toast.LENGTH_SHORT).show()
    }

    override fun addShopping(product: Product) {
        val flag = flagItemInBasket(product)
        if (flag) {
            Toast.makeText(this, "Продукт уже в карзине", Toast.LENGTH_SHORT).show()
        } else {
            basketViewModel.addProduct(product)
            basketViewModel.setStatus(BasketStatus.Filled)
            Toast.makeText(this, product.name, Toast.LENGTH_SHORT).show()
        }
        productFragment.productInBasket()
    }

    override fun openAllDescription() {
        Toast.makeText(this, "AllDescription", Toast.LENGTH_SHORT).show()
    }

    override fun openAllFeature() {
        Toast.makeText(this, "AllFeature", Toast.LENGTH_SHORT).show()
    }

    override fun openAllSubjects() {
        Toast.makeText(this, "AllSubjects", Toast.LENGTH_SHORT).show()
    }

    override fun onProductBack() {
        Toast.makeText(this, "Back", Toast.LENGTH_SHORT).show()
        supportFragmentManager.popBackStack()
    }

    override fun onProductOpenItem(product: Product) {
        val flag = flagItemInBasket(product)
        productFragment = ProductFragment.newInstance(product, flag)
        supportFragmentManager.beginTransaction().addToBackStack(null).replace(
            R.id.navHostContainer,
            productFragment
        ).commit()
    }

    private fun flagItemInBasket(product: Product): Boolean {
        var flagBasket = false
        basketViewModel.basketList.value?.forEach {
            if (product.codeVendor === it.product.codeVendor) {
                flagBasket = true
                return flagBasket
            }
        }
        return flagBasket
    }
}