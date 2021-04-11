package com.example.haconsultant.fragment.basket

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.haconsultant.R
import com.example.haconsultant.model.BasketItem
import kotlinx.android.synthetic.main.fragment_basket.*
import java.text.DecimalFormat

class BasketFragment : Fragment(), BasketAdapterInteractor {

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
        basketClear.setOnClickListener {
            viewModel.basketList.value?.clear()
            sizeInZero()
            basketProductAdapter?.notifyDataSetChanged()
        }
        basketBtnСheckout.setOnClickListener {
            fragmentInteractor?.onBasketCheckout()
        }
        viewModel.weight.observe(viewLifecycleOwner, Observer {
            it.let {
                val format = DecimalFormat("####.00")

                bascketWeight.text = "${format.format(it)} кг"
            }
        })
        viewModel.price.observe(viewLifecycleOwner, Observer {
            it.let {
                basketPrice.text = "$it ₽"
            }
        })
    }

    fun setBasketRecycler() {
        basketProductAdapter = BasketProductAdapter(
            basketFragmentInteractor = fragmentInteractor,
            basketAdapterInteractor = this
        )
        viewModel.basketList.observe(viewLifecycleOwner, Observer {
            it.let {
                basketProductAdapter?.items = it
                var price = 0
                var weight = 0F
                for (i in it) {
                    price += i.product.prices
                    weight += i.product.weight
                }
                viewModel.setItemsValue(price, weight)
            }
        })
        basketRecycler.adapter = basketProductAdapter
    }

    override fun onDestroy() {
        fragmentInteractor = null
        super.onDestroy()
    }

    override fun sizeInZero() {
        viewModel.itmeZero()
        basketClear.isVisible = false
        bascketNotEmpty.isVisible = false
        bascketEmpty.isVisible = true
    }

    override fun minusPrice(item: BasketItem) {
        viewModel.minusItem(item.product)
    }
}