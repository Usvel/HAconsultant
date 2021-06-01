package com.example.haconsultant.fragment.all

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doOnTextChanged
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.example.haconsultant.R
import com.example.haconsultant.fragment.filter.IteamFilterAdapter
import com.example.haconsultant.fragment.home.ProductAdapter
import com.example.haconsultant.fragment.search.SearchFragmentInteractor
import com.example.haconsultant.fragment.search.SearchItemDecoration
import com.example.haconsultant.fragment.search.SearhViewModel
import com.example.haconsultant.model.HomeData
import com.example.haconsultant.model.Product
import kotlinx.android.synthetic.main.fragment_all_product.*
import kotlinx.android.synthetic.main.fragment_search.*
import kotlinx.android.synthetic.main.fragment_search.searchRecycler
import kotlinx.android.synthetic.main.fragment_search_filter.*

class AllProductFragment : Fragment() {

    val viewModel: AllProductViewModel by lazy {
        ViewModelProvider(requireActivity()).get(AllProductViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_all_product, container, false)
    }

    private var fragmentInteractor: SearchFragmentInteractor? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is SearchFragmentInteractor) {
            fragmentInteractor = context
        }
    }

    private var searchProductAdapter: ProductAdapter? = null
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setSerchRecycler()
        fragmentInteractor?.loadAllProduct()
        allProductBtnBack.setOnClickListener {
            fragmentInteractor?.onSearchBack()
        }
        allProductTextSearchProduct.doOnTextChanged { text, start, before, count ->
            searchText(text.toString())
        }
    }


    private fun setSerchRecycler() {
        searchProductAdapter = ProductAdapter(searchFragmentInteractor = fragmentInteractor)
        allProductRecycler.adapter = searchProductAdapter
        allProductRecycler.layoutManager =
            GridLayoutManager(context, 2, GridLayoutManager.VERTICAL, false);
        allProductRecycler.addItemDecoration(SearchItemDecoration())
        allProductRecycler.setNestedScrollingEnabled(false)
        viewModel.searchList.observe(viewLifecycleOwner, Observer {
            searchProductAdapter?.items = it
            if ((!allProductTextSearchProduct.text.isEmpty())) {
                searchText(allProductTextSearchProduct.text.toString())
            }
        })
    }

    private fun searchText(text: String) {
        val list = arrayListOf<Product>()
        viewModel.searchList.value?.forEach {
            if ((it.name.toLowerCase()
                    .contains(text.toLowerCase())) || (it.codeVendor.contains(text))
            ) {
                list.add(it)
            }
        }
        searchProductAdapter?.items = list
        searchProductAdapter?.notifyDataSetChanged()
    }

    override fun onDestroy() {
        searchProductAdapter = null
        fragmentInteractor = null
        super.onDestroy()
    }
}