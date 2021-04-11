package com.example.haconsultant.fragment.basket

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.haconsultant.R
import com.example.haconsultant.fragment.catalog.SearchFragmentInteractor
import com.example.haconsultant.fragment.catalog.SearhViewModel
import com.example.haconsultant.fragment.home.ProductAdapter
import com.example.haconsultant.model.HomeData
import com.example.haconsultant.model.Product
import kotlinx.android.synthetic.main.fragment_basket.*
import kotlinx.android.synthetic.main.fragment_home.*

class BasketFragment : Fragment() {

    private var fragmentInteractor: BasketFragmentInteractor? = null

    val viewModel: BasketViewModel by lazy {
        ViewModelProvider(requireActivity()).get(BasketViewModel::class.java)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is BasketFragmentInteractor) {
            fragmentInteractor = context
        }
    }

    private var basketProductAdapter: BasketProductAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_basket, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setBasketRecycler()
    }

    fun setBasketRecycler() {
        basketProductAdapter = BasketProductAdapter(fragmentInteractor)
        viewModel.basketList.observe(viewLifecycleOwner, Observer {
            it.let {
                basketProductAdapter?.items = it
            }
        })
        basketRecycler.adapter = basketProductAdapter
        basketClear.setOnClickListener {
            viewModel.basketList.value?.clear()
            basketProductAdapter?.notifyDataSetChanged()
        }
        basketBtnСheckout.setOnClickListener {
            fragmentInteractor?.onBasketCheckout()
        }
    }

    override fun onDestroy() {
        fragmentInteractor = null
        super.onDestroy()
    }
}