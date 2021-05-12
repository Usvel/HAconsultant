package com.example.haconsultant.fragment.purchase

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import com.example.haconsultant.R
import com.example.haconsultant.fragment.home.ProductAdapter
import com.example.haconsultant.fragment.product.ProductFragment
import com.example.haconsultant.fragment.search.SearchFragmentInteractor
import com.example.haconsultant.fragment.search.SearchItemDecoration
import com.example.haconsultant.model.HomeData
import com.example.haconsultant.model.Orders
import com.example.haconsultant.model.Product
import com.google.gson.Gson
import kotlinx.android.synthetic.main.fragment_all_product.*
import kotlinx.android.synthetic.main.fragment_all_product.allProductRecycler
import kotlinx.android.synthetic.main.fragment_purchase.*


class PurchaseFragment : Fragment() {

    private var fragmentInteractor: SearchFragmentInteractor? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is SearchFragmentInteractor) {
            fragmentInteractor = context
        }
    }

    companion object {
        private const val ARG_MESSAGE_PURCHASE = "purchase"

        fun newInstance(orders: Orders): PurchaseFragment {
            val fragment = PurchaseFragment()
            val arguments = Bundle()
            arguments.putSerializable(ARG_MESSAGE_PURCHASE, orders)
            fragment.arguments = arguments
            return fragment
        }
    }

    private var recievedOrders: Orders? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_purchase, container, false)
        recievedOrders = savedInstanceState?.getSerializable(ARG_MESSAGE_PURCHASE) as Orders?
        return view
    }

    private var searchProductAdapter: ProductAdapter? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setSerchRecycler()
        if (recievedOrders != null) {
            when (recievedOrders!!.status) {
                0 -> {
                    purchaseStatus.text = "Обрабатывается"
                }
                1 -> {
                    purchaseStatus.text = "Принят"
                }
                2 -> {
                    purchaseStatus.text = "Забрали"
                }
                3 -> {
                    purchaseStatus.text = "Отклонен"
                }
            }

        }
        purchaseBtnBack.setOnClickListener {
            fragmentInteractor?.onSearchBack()
        }
    }

    private fun setSerchRecycler() {
        searchProductAdapter = ProductAdapter(searchFragmentInteractor = fragmentInteractor)
        purchaseRecycler.adapter = searchProductAdapter
        purchaseRecycler.layoutManager =
            GridLayoutManager(context, 2, GridLayoutManager.VERTICAL, false);
        purchaseRecycler.addItemDecoration(SearchItemDecoration())
        purchaseRecycler.setNestedScrollingEnabled(false)

        searchProductAdapter?.items = HomeData.getProduct()
    }

}