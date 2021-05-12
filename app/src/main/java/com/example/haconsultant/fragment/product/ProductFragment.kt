package com.example.haconsultant.fragment.product

import android.app.AlertDialog
import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.haconsultant.R
import com.example.haconsultant.fragment.home.ProductAdapter
import com.example.haconsultant.model.Product
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_product.*
import kotlinx.android.synthetic.main.fragment_product.view.*
import java.lang.StringBuilder
import java.util.ArrayList
import java.util.concurrent.TimeUnit

class ProductFragment : Fragment() {
    //    val viewModel: ProductViewModel by lazy {
//        ViewModelProvider(requireActivity()).get(ProductViewModel::class.java)
//    }
    private var positionScroll = 0

    private val compositeDisposable = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        retainInstance = true;
    }

    companion object {
        private const val ARG_MESSAGE_PRODUCT = "product"

        fun newInstance(product: Product): ProductFragment {
            val fragment = ProductFragment()
            val arguments = Bundle()
            arguments.putSerializable(ARG_MESSAGE_PRODUCT, product)
            fragment.arguments = arguments
            return fragment
        }
    }

    private var recievedProduct: Product? = null
    private var fragmentInteractor: ProductFragmentInteractor? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is ProductFragmentInteractor) {
            fragmentInteractor = context
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_product, container, false)
        recievedProduct = requireArguments().getSerializable(ARG_MESSAGE_PRODUCT) as Product?
        return view
    }

    private var productAdapter: ProductAdapter? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setPagerAdapter()
        setProductAdapter()
//        viewModel.product.observe(viewLifecycleOwner, Observer {
//            it.let { product ->
//                productName.text = product.name
//                productPrise.text = product.prices.toString()
//                productCodeVendor.text = product.codeVendor
//                productRatingBar.rating = product.evaluation
//                productSizeReviews.text = product.sizeReviews.toString()
//            }
//        })

        if (recievedProduct != null) {
            productName.text = recievedProduct!!.name
            productPrise.text = "${recievedProduct!!.prices} ₽"
            productCodeVendor.text = recievedProduct!!.codeVendor
            productRatingBar.rating = recievedProduct!!.evaluation
            productSizeReviews.text = "Отзовы ${recievedProduct!!.sizeReviews}"
            productDescription.text = recievedProduct!!.description
            if (productAdapter?.items?.size == 0) {
                val disposable = fragmentInteractor?.loadListProduct(recievedProduct!!.listProduct)
                    ?.subscribeOn(Schedulers.io())
                    ?.observeOn(AndroidSchedulers.mainThread())
                    ?.subscribe({
                        addIteamAdapter(it)
                        if (productAdapter?.items?.size == 1) {
                            productScrollView.post {
                                productScrollView.scrollTo(0, positionScroll)
                            }
                        }
                        // productScrollView.scrollY = positionScroll
                    }, {
                        AlertDialog.Builder(context).setTitle("Ошибка загрузки")
                            .setMessage(it.message)
                            .show()
                        productScrollView.post {
                            productScrollView.smoothScrollTo(0, positionScroll)
                        }
                    })
                compositeDisposable.add(disposable!!)
            }
            val text = StringBuilder()
            text.appendLine("Вес: ${recievedProduct?.weight} кг")
            recievedProduct?.characteristics?.forEach {
                text.appendLine("${it.key}: ${it.value}")
                Log.d("TEXT", text.toString())
            }
            productCharacteristics.text = text
        }

        if (fragmentInteractor?.statusProductr(product = recievedProduct!!) == true) {
            productInBasket()
        }

        productBtnInBasket.setOnClickListener {
            //viewModel.product.value?.let { it1 -> fragmentInteractor?.addShopping(it1) }
            recievedProduct?.let { it1 -> fragmentInteractor?.addShopping(it1) }
        }
        productBtnAllDescription.setOnClickListener {
            fragmentInteractor?.openAllDescription(productDescription.text.toString())
        }

        productBtnAllСharacteristics.setOnClickListener {
            fragmentInteractor?.openAllFeature(productCharacteristics.text.toString())
        }

        productBtnAllSubjects.setOnClickListener {
            fragmentInteractor?.openAllSubjects()
        }
        productBtnBack.setOnClickListener {
            fragmentInteractor?.onProductBack()
        }
        productScrollView.scrollTo(0, positionScroll)
    }

    private fun setPagerAdapter() {
        val adapter = ProductPagerAdapter()
        recievedProduct.let {
            adapter.items = it?.listImage!!
        }
        productViewPager.adapter = adapter
        productIndicator.setViewPager(productViewPager)
    }

    private fun addIteamAdapter(product: Product) {
        (productAdapter?.items as MutableList).add(product)
        productAdapter?.notifyItemInserted(productAdapter?.items!!.size)
    }

    private fun setProductAdapter() {
        productAdapter = ProductAdapter(productFragmentInteractor = fragmentInteractor)
        productAdapter?.items = ArrayList()
        productRecycler.adapter = productAdapter
    }

    override fun onStop() {
        positionScroll = productScrollView.getScrollY()
        super.onStop()
    }

    override fun onDestroy() {
        fragmentInteractor = null
        productAdapter = null
        compositeDisposable.clear()
        super.onDestroy()
    }


    fun productInBasket() {
        productBtnInBasket.isEnabled = false
        productBtnInBasket.setBackgroundColor(Color.GRAY)
        productBtnInBasket.text = "Товар в корзине"
    }
}