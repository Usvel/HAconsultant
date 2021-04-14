package com.example.haconsultant.fragment.product

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager.widget.ViewPager
import androidx.viewpager2.widget.ViewPager2
import com.example.haconsultant.R
import com.example.haconsultant.fragment.home.HomeCatalogAdapter
import com.example.haconsultant.fragment.home.HomeFragmentInteractor
import com.example.haconsultant.fragment.home.ProductAdapter
import com.example.haconsultant.model.HomeData
import com.example.haconsultant.model.Product
import kotlinx.android.synthetic.main.fragment_product.*

class ProductFragment : Fragment() {
//    val viewModel: ProductViewModel by lazy {
//        ViewModelProvider(requireActivity()).get(ProductViewModel::class.java)
//    }

    companion object {
        private const val ARG_MESSAGE_PRODUCT = "product"
        private const val ARG_MESSAGE_STATUS = "product_status"

        fun newInstance(product: Product, statusBasket: Boolean): ProductFragment {
            val fragment = ProductFragment()
            val arguments = Bundle()
            arguments.putParcelable(ARG_MESSAGE_PRODUCT, product)
            arguments.putBoolean(ARG_MESSAGE_STATUS, statusBasket)
            fragment.arguments = arguments
            return fragment
        }
    }

    private var recievedProduct: Product? = null
    private var fragmentInteractor: ProductFragmentInteractor? = null
    private var statusBasketProduct: Boolean? = null

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
        recievedProduct = requireArguments().getParcelable<Product>(ARG_MESSAGE_PRODUCT)
        statusBasketProduct = requireArguments().getBoolean(ARG_MESSAGE_STATUS, false)
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
            productPrise.text = recievedProduct!!.prices.toString()
            productCodeVendor.text = recievedProduct!!.codeVendor
            productRatingBar.rating = recievedProduct!!.evaluation
            productSizeReviews.text = recievedProduct!!.sizeReviews.toString()
        }

        if (statusBasketProduct != null) {
            if (statusBasketProduct!!) {
                productInBasket()
            }
        }

        productBtnInBasket.setOnClickListener {
            //viewModel.product.value?.let { it1 -> fragmentInteractor?.addShopping(it1) }
            recievedProduct?.let { it1 -> fragmentInteractor?.addShopping(it1) }
        }
        productBtnAllDescription.setOnClickListener {
            fragmentInteractor?.openAllDescription()
        }

        productBtnAllСharacteristics.setOnClickListener {
            fragmentInteractor?.openAllFeature()
        }

        productBtnAllSubjects.setOnClickListener {
            fragmentInteractor?.openAllSubjects()
        }
        productBtnBack.setOnClickListener {
            fragmentInteractor?.onProductBack()
        }
    }

    private fun setPagerAdapter() {
        val adapter = ProductPagerAdapter()
        adapter.items = HomeData.getLinsPage()
        productViewPager.adapter = adapter
        productIndicator.setViewPager(productViewPager)
    }

    private fun setProductAdapter() {
        productAdapter = ProductAdapter(productFragmentInteractor = fragmentInteractor)
        productAdapter?.items = HomeData.getProduct()
        productRecycler.adapter = productAdapter
    }

    override fun onDestroy() {
        fragmentInteractor = null
        productAdapter = null
        super.onDestroy()
    }

    fun productInBasket(){
        productBtnInBasket.isEnabled = false
        productBtnInBasket.setBackgroundColor(Color.GRAY)
        productBtnInBasket.text = "Товар в корзине"
    }
}