package com.example.haconsultant

import android.Manifest
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.haconsultant.fragment.BackStackLiveData
import com.example.haconsultant.fragment.StatusCamera
import com.example.haconsultant.fragment.StatusFragment
import com.example.haconsultant.fragment.basket.BasketFragmentInteractor
import com.example.haconsultant.fragment.basket.BasketStatus
import com.example.haconsultant.fragment.basket.BasketViewModel
import com.example.haconsultant.fragment.camera.CameraFragment
import com.example.haconsultant.fragment.camera.CameraFragmentInteractor
import com.example.haconsultant.fragment.catalog.CatalogFragment
import com.example.haconsultant.fragment.catalog.CatalogFragmentInteractor
import com.example.haconsultant.fragment.home.HomeFragment
import com.example.haconsultant.fragment.search.SearchFragment
import com.example.haconsultant.fragment.search.SearchFragmentInteractor
import com.example.haconsultant.fragment.home.HomeFragmentInteractor
import com.example.haconsultant.fragment.product.ProductFragment
import com.example.haconsultant.fragment.product.ProductFragmentInteractor
import com.example.haconsultant.fragment.user.UserFragmentInteractor
import com.example.haconsultant.model.Catalog
import com.example.haconsultant.model.HomeCatalog
import com.example.haconsultant.model.HomeData
import com.example.haconsultant.model.Product
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), HomeFragmentInteractor, SearchFragmentInteractor,
    UserFragmentInteractor, BasketFragmentInteractor, CatalogFragmentInteractor,
    CameraFragmentInteractor,
    ProductFragmentInteractor {

//    private val homeFragment = HomeFragment()
//    private val searchFragment = SearchFragment()
//    private val userFragment = UserFragment()
//    private val basketFragment = BasketFragment()
//    private var productFragment = ProductFragment()

    val basketViewModel: BasketViewModel by lazy {
        ViewModelProvider(this).get(BasketViewModel::class.java)
    }

    val backStackLiveData: BackStackLiveData by lazy {
        ViewModelProvider(this).get(BackStackLiveData::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //setActionBar(findViewById(R.layout.app_bar2))
        bottomNavigation.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.navigation_search -> {
                    if (backStackLiveData.statusFragment.value == StatusFragment.Search) {
                        setStarFragment()
                    } else {
                        backStackLiveData.setStatus(StatusFragment.Search)
                    }
                    true
                }
                R.id.navigation_catalog -> {
                    if (backStackLiveData.statusFragment.value == StatusFragment.Catalog) {
                        setStarFragment()
                    } else {
                        backStackLiveData.setStatus(StatusFragment.Catalog)
                    }
                    // backStackLiveData.setStatus(StatusFragment.Catalog)
                    true
                }
                R.id.navigation_basket -> {
                    if (backStackLiveData.statusFragment.value == StatusFragment.Basket) {
                        setStarFragment()
                    } else {
                        backStackLiveData.setStatus(StatusFragment.Basket)
                    }
//                    supportFragmentManager.beginTransaction()
//                        .replace(R.id.navHostContainer, basketFragment).commit()
                    true
                }
                R.id.navigation_user -> {
                    if (backStackLiveData.statusFragment.value == StatusFragment.User) {
                        setStarFragment()
                    } else {
                        backStackLiveData.setStatus(StatusFragment.User)
                    }
//                    supportFragmentManager.beginTransaction()
//                        .replace(R.id.navHostContainer, userFragment).commit()
                    true
                }
                else -> false
            }
        }

        backStackLiveData.startQueueFragment()
        backStackLiveData.statusFragment.observe(this, Observer {
            it.let {
                supportFragmentManager.beginTransaction()
                    .replace(R.id.navHostContainer, backStackLiveData.lastQeueFragment()!!)
                    .commit()
            }
        })
        //supportFragmentManager.get
        //startActivity(this, ScrollingActivity::class.java)

        backStackLiveData.statusCamera.observe(this, androidx.lifecycle.Observer {
            it.let {
                when (it) {
                    StatusCamera.CameraOn -> {
                        bottomNavigation.isVisible = false
                    }
                    StatusCamera.CameraOff -> {
                        bottomNavigation.isVisible = true
                    }
                }
            }
        })
    }

    private fun backFragment() {
        backStackLiveData.removeQueueFragment()
        supportFragmentManager.beginTransaction()
            .replace(R.id.navHostContainer, backStackLiveData.lastQeueFragment()!!).commit()
    }

    private fun addFragment(fragment: Fragment) {
        backStackLiveData.addQueueFragment(fragment)
        supportFragmentManager.beginTransaction().replace(
            R.id.navHostContainer,
            backStackLiveData.lastQeueFragment()!!
        ).commit()
    }

    private fun setStarFragment() {
        backStackLiveData.clearQueueFragment()
        supportFragmentManager.beginTransaction()
            .replace(R.id.navHostContainer, backStackLiveData.lastQeueFragment()!!)
            .commit()
    }

    override fun onHomeOpenItem(product: Product) {
        val productFragment = ProductFragment.newInstance(product)
        addFragment(productFragment)
    }

    override fun onHomeOpenCatalog(homeCatalog: HomeCatalog) {
        Toast.makeText(this, homeCatalog.name, Toast.LENGTH_SHORT).show()
    }

    override fun onHomeOpenCameraQrCode() {
        addFragment(CameraFragment())
        Toast.makeText(this, "QrCode", Toast.LENGTH_SHORT).show()
    }

    override fun onHomeOpenSerch() {
        addFragment(SearchFragment())
    }

    override fun onSearchOpenItem(product: Product) {
        val productFragment = ProductFragment.newInstance(product)
        addFragment(productFragment)
    }

    override fun onSearchBack() {
        backFragment()
    }

    override fun onUserOpenCameraQrCode() {
        Toast.makeText(this, "QrCode", Toast.LENGTH_SHORT).show()
    }

    override fun onBasketOpenItem(product: Product) {
        val productFragment = ProductFragment.newInstance(product)
        addFragment(productFragment)
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
        (backStackLiveData.lastQeueFragment()!! as ProductFragment).productInBasket()
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
        backFragment()
    }

    override fun onProductOpenItem(product: Product) {
        val productFragment = ProductFragment.newInstance(product)
        addFragment(productFragment)
    }

    override fun statusProductr(product: Product): Boolean {
        return flagItemInBasket(product)
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

    override fun onCatalogOpenNext(catalog: Catalog) {
        if (catalog.listCatalog == null) {
            addFragment(SearchFragment())
        } else {
            val catalogFragment = CatalogFragment.newInstance(catalog)
            addFragment(catalogFragment)
        }
    }

    override fun onCatalogBack() {
        backFragment()
    }

    override fun onCameraBack() {
        backFragment()
    }

    override fun onCameraOk(string: String) {
        backFragment()
        AlertDialog.Builder(this).setTitle("Qr-Code").setMessage(string).show()
    }

    override fun onBackPressed() {
//        AlertDialog.Builder(this).apply {
//            setTitle("Подтверждение")
//            setMessage("Вы уверены, что хотите выйти из программы?")
//
//            setPositiveButton("Таки да") { _, _ ->
//                finish()
//            }
//
//            setNegativeButton("Нет") { _, _ ->
//                // if user press no, then return the activity
//                Toast.makeText(
//                    this@MainActivity, "Thank you",
//                    Toast.LENGTH_LONG
//                ).show()
//            }
//            setCancelable(true)
//        }.create().show()
//
        backStackLiveData.removeQueueFragment()
        if (backStackLiveData.lastQeueFragment() == null) {
            finish()
        } else {
            supportFragmentManager.beginTransaction()
                .replace(R.id.navHostContainer, backStackLiveData.lastQeueFragment()!!).commit()
        }
    }
}