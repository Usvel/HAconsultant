package com.example.haconsultant

import android.os.Bundle
import android.util.Log
import android.util.TypedValue
import android.widget.Toast
import androidx.annotation.Dimension
import androidx.annotation.Px
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.haconsultant.firebase.CatalogRepository
import com.example.haconsultant.firebase.api.CatalogApiImpl
import com.example.haconsultant.firebase.api.TypeSort
import com.example.haconsultant.fragment.BackStackLiveData
import com.example.haconsultant.fragment.StatusCamera
import com.example.haconsultant.fragment.StatusFragment
import com.example.haconsultant.fragment.additional.AddirionalFragmentInteractor
import com.example.haconsultant.fragment.additional.AdditionalFragment
import com.example.haconsultant.fragment.all.AllProductFragment
import com.example.haconsultant.fragment.all.AllProductViewModel
import com.example.haconsultant.fragment.basket.BasketFragmentInteractor
import com.example.haconsultant.fragment.basket.BasketStatus
import com.example.haconsultant.fragment.basket.BasketViewModel
import com.example.haconsultant.fragment.camera.CameraFragment
import com.example.haconsultant.fragment.camera.CameraFragmentInteractor
import com.example.haconsultant.fragment.catalog.CatalogFragment
import com.example.haconsultant.fragment.catalog.CatalogFragmentInteractor
import com.example.haconsultant.fragment.catalog.CatalogViewModel
import com.example.haconsultant.fragment.filter.*
import com.example.haconsultant.fragment.history.HistoryFragment
import com.example.haconsultant.fragment.history.HistoryFragmentInteractor
import com.example.haconsultant.fragment.home.HomeFragmentInteractor
import com.example.haconsultant.fragment.home.HomeViewModel
import com.example.haconsultant.fragment.home.Status
import com.example.haconsultant.fragment.product.ProductFragment
import com.example.haconsultant.fragment.product.ProductFragmentInteractor
import com.example.haconsultant.fragment.purchase.PurchaseFragment
import com.example.haconsultant.fragment.search.SearchFragment
import com.example.haconsultant.fragment.search.SearchFragmentInteractor
import com.example.haconsultant.fragment.search.SearhViewModel
import com.example.haconsultant.fragment.user.SetingsFragment
import com.example.haconsultant.fragment.user.SetingsFragmentInteractor
import com.example.haconsultant.fragment.user.UserFragmentInteractor
import com.example.haconsultant.fragment.user.UserViewModel
import com.example.haconsultant.model.*
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import io.reactivex.Flowable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*


class MainActivity : AppCompatActivity(), HomeFragmentInteractor, SearchFragmentInteractor,
    UserFragmentInteractor, BasketFragmentInteractor, CatalogFragmentInteractor,
    CameraFragmentInteractor,
    ProductFragmentInteractor,
    AddirionalFragmentInteractor,
    SearchFilterFragmentInteractor,
    IteamFilterFragmentInteractor,
    IteamPriceFragmentInteractor,
    SetingsFragmentInteractor, HistoryFragmentInteractor {

    private val compositeDisposable = CompositeDisposable()
    private val catalogRepository by lazy { CatalogRepository(CatalogApiImpl(context = this)) }

//    private val homeFragment = HomeFragment()
//    private val searchFragment = SearchFragment()
//    private val userFragment = UserFragment()
//    private val basketFragment = BasketFragment()
//    private var productFragment = ProductFragment()

    val userViewModel: UserViewModel by lazy {
        ViewModelProvider(this).get(UserViewModel::class.java)
    }

    val allProductViewModel: AllProductViewModel by lazy {
        ViewModelProvider(this).get(AllProductViewModel::class.java)
    }

    val basketViewModel: BasketViewModel by lazy {
        ViewModelProvider(this).get(BasketViewModel::class.java)
    }

    val backStackLiveData: BackStackLiveData by lazy {
        ViewModelProvider(this).get(BackStackLiveData::class.java)
    }

    val homeModel: HomeViewModel by lazy {
        ViewModelProvider(this).get(HomeViewModel::class.java)
    }

    val catalogViewModel: CatalogViewModel by lazy {
        ViewModelProvider(this).get(CatalogViewModel::class.java)
    }

    val searhViewModel: SearhViewModel by lazy {
        ViewModelProvider(this).get(SearhViewModel::class.java)
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
                        catalogViewModel.setStartCatalog()
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
        var s = Stack<String>()
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

        catalogRepository.loadProductr()
        loadCatalog()
        loadHomeNew()
    }

    private fun loadCatalog() {
        val disposable = catalogRepository.startCatalog().subscribeOn(Schedulers.io())
            .subscribeOn(AndroidSchedulers.mainThread()).subscribe({
                catalogViewModel.setCatalog(it)
                val listHomeCatalog: MutableList<HomeCatalog> = arrayListOf()
                it.listName?.forEachIndexed { index, s ->
                    listHomeCatalog.add(
                        HomeCatalog(
                            s,
                            it.listFeature?.get(index)?.get("imageUrl").toString()
                        )
                    )
                }
                Log.d("catalog", listHomeCatalog.toString())
                homeModel.setCatalogList(listHomeCatalog)
            }, {
                AlertDialog.Builder(this)
                    .setTitle("???????????? ????????????????")
                    .setMessage(it.message)
                    .setPositiveButton("????") { dialog, id ->
                        dialog.cancel()
                    }.create()
                    .show()
            })
        compositeDisposable.add(disposable)
    }

    override fun loadAllProduct() {
        val disposable = catalogRepository.loadAllProduct().subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread()).subscribe({
                allProductViewModel.setList(it)
            }, {
                AlertDialog.Builder(this)
                    .setTitle("???????????? ????????????????")
                    .setMessage(it.message)
                    .setPositiveButton("????") { dialog, id ->
                        dialog.cancel()
                    }.create()
                    .show()
            })
    }

    private fun loadHomeNew() {
        val mutableList = mutableListOf<Product>()
        val disposable = catalogRepository.loadHomeNewsIteam()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                mutableList.add(it)
                homeModel.setNewList(mutableList)
                homeModel.setStatusNew(Status.Success)
            }, {
                AlertDialog.Builder(this)
                    .setTitle("???????????? ????????????????")
                    .setMessage(it.message)
                    .setPositiveButton("????") { dialog, id ->
                        dialog.cancel()
                    }.create()
                    .show()
                homeModel.setStatusNew(Status.Failure)
            })
        compositeDisposable.add(disposable)

//        val newDisposable = catalogRepository.loadHomeNewsIteam()
//            .subscribeOn(Schedulers.io())
//            .observeOn(AndroidSchedulers.mainThread())
//            .subscribe({ news ->
//                Log.d("RX", news.toString())
//            },
//                { throwable ->
//                    AlertDialog.Builder(this)
//                        .setTitle("???????????? ????????????????")
//                        .setMessage(throwable.toString())
//                        .setPositiveButton("????") { dialog, id ->
//                            dialog.cancel()
//                        }.create()
//                        .show()
//                })
//        compositeDisposable.add(newDisposable)
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
        homeModel.addLastProduct(product)
    }

    override fun onHomeOpenCatalog(homeCatalog: HomeCatalog) {
        if (homeCatalog.name != "??????????????") {
            //???????????? ????????????
            val dialog = AlertDialog.Builder(this)
                .setView(layoutInflater.inflate(R.layout.custom_dialog, null)).setCancelable(false)
                .show()
            dialog.window?.setLayout(dpToPx(100), dpToPx(100))
            dialog.show()

            var catalogFirestore: CatalogFirestore? = null
            val catalog = homeCatalog.name
            if (!catalogViewModel._stackCatalogFirestore.value?.isEmpty()!!) {
                catalogFirestore =
                    catalogViewModel._stackCatalogFirestore.value?.get(0)
            } else {
                catalogFirestore = catalogViewModel.catalogFirestore.value
            }

            val index = catalogFirestore?.listName?.indexOf(catalog)
            val path = catalogFirestore?.pashlist?.get(index!!)
            val id = catalogFirestore?.nameBd?.get(index!!)
            val featureMap = mutableMapOf<String, Any>()
            catalogFirestore?.listFeature?.get(index!!)?.forEach {
                Log.d("catalog", "??????????")
                featureMap.put(it.key, it.value)
            }

            val disposable =
                catalogRepository.nextCatalog(path!!, catalog, id!!, featureMap)
                    .subscribeOn(Schedulers.io())
                    .subscribeOn(AndroidSchedulers.mainThread()).subscribe({
                        dialog.hide()
                        val feature = Feature(
                            typeSort = TypeSort("evaluation", "desc"),
                            nameCatalog = it.name
                        )
                        searhViewModel.feature.value = feature
                        searhViewModel.mapFilter.value = featureMap
                        featureCatalog(feature)
                        openHomeCatalog()
                        //addFragment(SearchFragment())
                    }, {
                        dialog.hide()
                        AlertDialog.Builder(this)
                            .setTitle("???????????? ????????????????")
                            .setMessage(it.message)
                            .setPositiveButton("????") { dialog, id ->
                                dialog.cancel()
                            }.show()
                    })
            compositeDisposable.add(disposable)
        } else {
            openHomeCatalogStart()
        }
        Toast.makeText(this, homeCatalog.name, Toast.LENGTH_SHORT).show()
    }

    private fun openHomeCatalogStart() {
        bottomNavigation.menu.findItem(R.id.navigation_catalog).isChecked = true
        backStackLiveData.setStatus(StatusFragment.Catalog)
        while (!(backStackLiveData.lastQeueFragment() is CatalogFragment)) {
            backFragment()
        }
        catalogViewModel.setStartCatalog()
    }

    private fun openHomeCatalog() {
        bottomNavigation.menu.findItem(R.id.navigation_catalog).isChecked = true
        backStackLiveData.setStatus(StatusFragment.Catalog)
        if (backStackLiveData.lastQeueFragment() is CatalogFragment) {
            addFragment(SearchFragment())
        } else {
            while (!(backStackLiveData.lastQeueFragment() is CatalogFragment)) {
                backFragment()
            }
            addFragment(SearchFragment())
        }
    }

    override fun onHomeOpenCameraQrCode() {
        addFragment(CameraFragment())
        Toast.makeText(this, "QrCode", Toast.LENGTH_SHORT).show()
    }

    override fun onHomeOpenSerch() {
        addFragment(AllProductFragment())
    }

    override fun onSearchOpenItem(product: Product) {
        val productFragment = ProductFragment.newInstance(product)
        addFragment(productFragment)
        homeModel.addLastProduct(product)
    }

    override fun onSearchBack() {
        backFragment()
    }

    override fun onUserOpenCameraQrCode() {
        addFragment(CameraFragment())
        Toast.makeText(this, "QrCode", Toast.LENGTH_SHORT).show()
    }

    override fun openSetings() {
        addFragment(SetingsFragment())
    }

    override fun openHistory() {
        addFragment(HistoryFragment())
    }

    override fun onBasketOpenItem(product: Product) {
        val productFragment = ProductFragment.newInstance(product)
        addFragment(productFragment)
        homeModel.addLastProduct(product)
    }

    override fun onBasketCheckout() {
        val list = basketViewModel.basketList.value
        val gson = Gson().toJson(list)

        val postType = object : TypeToken<List<BasketItem>>() {}.type
        val listBasket: List<BasketItem> = Gson().fromJson(gson, postType)

        val listProduct = arrayListOf<Product>()
        listBasket.forEach {
            listProduct.add(it.product)
        }

        Log.d("proverca", listBasket.size.toString())

        val currentDate = Date()
        if (userViewModel.id.value != "00000000") {
            val disposable = catalogRepository.setOrder(
                userViewModel.id.value.toString(),
                currentDate.toString(),
                gson
            ).subscribeOn(Schedulers.io())
                .subscribeOn(AndroidSchedulers.mainThread()).subscribe({
                    basketViewModel.basketToZero()
                    addFragment(PurchaseFragment.newInstance(it))
                }, {
                    AlertDialog.Builder(this).setTitle("???????????? ????????").setMessage(it.message).show()
                })
            compositeDisposable.add(disposable)
        } else {
            AlertDialog.Builder(this).setTitle("??????????????").setMessage("???????????????????????? ??????????").show()
        }
        Log.d("basket", gson.toString() + "\n" + currentDate)
        Toast.makeText(this, "Checkout", Toast.LENGTH_SHORT).show()
    }

    override fun addShopping(product: Product) {
        val flag = flagItemInBasket(product)
        if (flag) {
            Toast.makeText(this, "?????????????? ?????? ?? ??????????????", Toast.LENGTH_SHORT).show()
        } else {
            basketViewModel.addProduct(product)
            basketViewModel.setStatus(BasketStatus.Filled)
            Toast.makeText(this, product.name, Toast.LENGTH_SHORT).show()
        }
        (backStackLiveData.lastQeueFragment()!! as ProductFragment).productInBasket()
    }

    override fun openAllDescription(text: String) {
        val addirional = AdditionalFragment.newInstance(text, "????????????????")
        addFragment(addirional)
    }

    override fun openAllFeature(text: String) {
        val addirional = AdditionalFragment.newInstance(text, "????????????????????????????")
        addFragment(addirional)
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
        homeModel.addLastProduct(product)
    }

    override fun statusProductr(product: Product): Boolean {
        return flagItemInBasket(product)
    }

    override fun loadListProduct(list: List<String>): Flowable<Product> {
        return catalogRepository.loadListProduct(list)
    }

    private fun flagItemInBasket(product: Product): Boolean {
        var flagBasket = false
        basketViewModel.basketList.value?.forEach {
            if (product.codeVendor.equals(it.product.codeVendor)) {
                flagBasket = true
                return flagBasket
            }
        }
        return flagBasket
    }

    override fun onCatalogOpenNext(catalog: String) {
        val dialog = AlertDialog.Builder(this)
            .setView(layoutInflater.inflate(R.layout.custom_dialog, null)).setCancelable(false)
            .show()
        dialog.window?.setLayout(dpToPx(100), dpToPx(100))
        dialog.show()
        val catalogFirestore = catalogViewModel.catalogFirestore.value
        val index = catalogFirestore?.listName?.indexOf(catalog)
        val path = catalogFirestore?.pashlist?.get(index!!)
        val id = catalogFirestore?.nameBd?.get(index!!)
        val featureMap = mutableMapOf<String, Any>()
        catalogFirestore?.feature?.forEach {
            Log.d("catalog", "??????????")
            featureMap.put(it.key, it.value)
        }

        catalogFirestore?.listFeature?.get(index!!)?.forEach {
            Log.d("catalog", "??????????-2")
            featureMap.put(it.key, it.value)
        }
        val disposable =
            catalogRepository.nextCatalog(path!!, catalog, id!!, featureMap)
                .subscribeOn(Schedulers.io())
                .subscribeOn(AndroidSchedulers.mainThread()).subscribe({
                    dialog.hide()
                    if ((it.listName != null) && (!it.listName?.isEmpty()!!)) {
                        catalogViewModel.setCatalog(it)
                    } else {
                        val feature = Feature(
                            typeSort = TypeSort("evaluation", "desc"),
                            nameCatalog = it.name
                        )
                        searhViewModel.feature.value = feature
                        searhViewModel.mapFilter.value = featureMap
                        featureCatalog(feature)
                        addFragment(SearchFragment())
                    }
                }, {
                    dialog.hide()
                    AlertDialog.Builder(this)
                        .setTitle("???????????? ????????????????")
                        .setMessage(it.message)
                        .setPositiveButton("????") { dialog, id ->
                            dialog.cancel()
                        }.show()
                })
        compositeDisposable.add(disposable)
//        if (catalog.listCatalog == null) {
//            addFragment(SearchFragment())
//        } else {
//            val catalogFragment = CatalogFragment.newInstance(catalog)
//            addFragment(catalogFragment)
//        }
    }

    override fun featureCatalog(feature: Feature) {
        val dialog = AlertDialog.Builder(this)
            .setView(layoutInflater.inflate(R.layout.custom_dialog, null)).setCancelable(false)
            .show()
        dialog.window?.setLayout(dpToPx(100), dpToPx(100))
        dialog.show()
        Log.d("Catalog-123", feature.toString())
        val disposable = catalogRepository.feature(
            priceMin = feature.priceMin,
            priceMax = feature.priceMax,
            manufacturer = feature.manufacturer,
            typeSort = feature.typeSort,
            map = feature.map,
            nameCatalog = feature.nameCatalog
        ).subscribeOn(Schedulers.io())
            .subscribeOn(AndroidSchedulers.mainThread()).subscribe({
                searhViewModel.setList(it)
                dialog.hide()
            }, {
                dialog.hide()
                AlertDialog.Builder(this)
                    .setTitle("???????????? ????????????????")
                    .setMessage(it.message)
                    .setPositiveButton("????") { dialog, id ->
                        dialog.cancel()
                    }.show()
            })
        compositeDisposable.add(disposable)
    }

    override fun onSearchOpenFilter() {
        addFragment(SearchFilter())
    }

    override fun onCatalogBack() {
        catalogViewModel.backCatalog()
    }

    override fun onCameraBack() {
        backFragment()
    }

    override fun onCameraOk(codeVendor: String?, name: String?) {
        when (backStackLiveData.statusFragment.value) {
            StatusFragment.Search -> {
                backStackLiveData.removeQueueFragment()
                if (codeVendor != null) {
                    val layoutInflater = this.layoutInflater
                    val dialog = AlertDialog.Builder(this)
                        .setView(layoutInflater.inflate(R.layout.custom_dialog, null))
                        .setCancelable(false).show()
                    dialog.window?.setLayout(dpToPx(100), dpToPx(100))
                    dialog.show()

                    val disposable =
                        catalogRepository.openCodeProduct(codeVendor).subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe({ product ->
                                val productFragment = ProductFragment.newInstance(product)
                                addFragment(productFragment)
                                homeModel.addLastProduct(product)
                                dialog.dismiss()
                            }, {
                                dialog.dismiss()
                                supportFragmentManager.beginTransaction()
                                    .replace(
                                        R.id.navHostContainer,
                                        backStackLiveData.lastQeueFragment()!!
                                    ).commit()
                                AlertDialog.Builder(this).setTitle("????????????")
                                    .setMessage(it.message).show()
                            })
                    compositeDisposable.add(disposable)
                } else {
                    supportFragmentManager.beginTransaction()
                        .replace(
                            R.id.navHostContainer,
                            backStackLiveData.lastQeueFragment()!!
                        ).commit()
                    AlertDialog.Builder(this).setTitle("Qr-Code")
                        .setMessage("Qr-code ???? ????????????????????????").show()
                }
            }
            StatusFragment.User -> {
                backFragment()
                val dialog = AlertDialog.Builder(this)
                    .setView(layoutInflater.inflate(R.layout.custom_dialog, null))
                    .setCancelable(false).show()
                dialog.window?.setLayout(dpToPx(100), dpToPx(100))
                dialog.show()
                if (name != null) {
                    catalogRepository.getUser(name).subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe({
                            userViewModel.setUser(it.name, it.id)
                            dialog.hide()
                        }, {
                            AlertDialog.Builder(this).setTitle("????????????")
                                .setMessage(it.message).show()
                        })
                } else {
                    AlertDialog.Builder(this).setTitle("Qr-Code").setMessage("Qr-code ???? ????????????????")
                        .show()
                }
            }
            else -> {
                AlertDialog.Builder(this).setTitle("Qr-Code").setMessage("???? ???????????????????????? ????????")
                    .show()
            }
        }

    }


    override fun onBackPressed() {
//        AlertDialog.Builder(this).apply {
//            setTitle("??????????????????????????")
//            setMessage("???? ??????????????, ?????? ???????????? ?????????? ???? ???????????????????")
//
//            setPositiveButton("???????? ????") { _, _ ->
//                finish()
//            }
//
//            setNegativeButton("??????") { _, _ ->
//                // if user press no, then return the activity
//                Toast.makeText(
//                    this@MainActivity, "Thank you",
//                    Toast.LENGTH_LONG
//                ).show()
//            }
//            setCancelable(true)
//        }.create().show()
//
        if ((backStackLiveData.statusFragment.value == StatusFragment.Catalog) && (backStackLiveData.lastQeueFragment() is CatalogFragment)) {
            if (catalogViewModel._stackCatalogFirestore.value?.size != 0) {
                catalogViewModel.backCatalog()
            } else {
                finish()
            }
        } else {
            backStackLiveData.removeQueueFragment()
            if (backStackLiveData.lastQeueFragment() == null) {
                finish()
            } else {
                supportFragmentManager.beginTransaction()
                    .replace(R.id.navHostContainer, backStackLiveData.lastQeueFragment()!!).commit()
            }
        }
    }

    override fun onStop() {
        compositeDisposable.clear()
        super.onStop()
    }

    @Px
    private fun dpToPx(@Dimension(unit = Dimension.DP) dp: Int): Int {
        val resources = resources
        val displayMetrics = resources.displayMetrics
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp.toFloat(), displayMetrics)
            .toInt()
    }

    override fun onAdditionalBack() {
        backFragment()
    }

    override fun onSearchFilterBack() {
        backFragment()
    }

    override fun openSearchFilter(name: String) {
        val fragment = IteamFilter.newInstance(name)
        addFragment(fragment)
    }

    override fun openPriceFilter() {
        addFragment(IteamPriceFragment())
    }

    override fun onAddMap(key: String, value: Any) {
        searhViewModel.feature.value?.map?.put(key, value)
        featureCatalog(searhViewModel.feature.value!!)
    }

    override fun onRemoveMap(key: String) {
        searhViewModel.feature.value?.map?.remove(key)
        featureCatalog(searhViewModel.feature.value!!)
    }

    override fun onIteamFilterBack() {
        backFragment()
    }

    override fun onSearchPriceBack() {
        backFragment()
    }

    override fun onSearchAddPrice(min: Int?, max: Int?) {
        searhViewModel.feature.value?.priceMin = min
        searhViewModel.feature.value?.priceMax = max
        featureCatalog(searhViewModel.feature.value!!)
        backFragment()
    }

    override fun onSettingsAddName(string: String) {
        val disposable = catalogRepository.setNameUser(User(string, userViewModel.id.value!!))
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                userViewModel.setUser(it, userViewModel.id.value!!)
                backFragment()
            }, {
                AlertDialog.Builder(this).setTitle("????????????????????????").setMessage("???????????? ????????").show()
                backFragment()
            })
        compositeDisposable.add(disposable)
    }

    override fun loadHistoryFragment(): Single<List<Orders>> {
        return catalogRepository.loadHistory(userViewModel.id.value!!)
    }

    override fun openOrder(orders: Orders) {
        val fragment = PurchaseFragment.newInstance(orders)
        addFragment(fragment)
    }
}