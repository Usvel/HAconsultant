package com.example.haconsultant.fragment.catalog

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.example.haconsultant.R
import com.example.haconsultant.fragment.home.HomeFragmentInteractor
import com.example.haconsultant.fragment.home.HomeViewModel
import com.example.haconsultant.fragment.home.ProductAdapter
import com.example.haconsultant.model.HomeData
import kotlinx.android.synthetic.main.fragment_search.*

class SearchFragment : Fragment() {

    private var fragmentInteractor: SearchFragmentInteractor? = null

    val viewModel: SearhViewModel by lazy {
        ViewModelProvider(requireActivity()).get(SearhViewModel::class.java)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is SearchFragmentInteractor) {
            fragmentInteractor = context
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_search, container, false)
    }

    private var searchProductAdapter: ProductAdapter? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setSerchRecycler()
        searchBtnBack.setOnClickListener {
            fragmentInteractor?.onSearchBack()
        }
        searchBtnSearchProduct.setOnClickListener {
            searchTextSearchProduct.isVisible = true
            searchTitle.isVisible = false
            searchBtnSearchProduct.isVisible = false
        }
        searchAllProduct.setOnClickListener{
            searchAllProduct.background = context?.getDrawable(R.drawable.search_text_corners_shape)
            searchInStore.background = context?.getDrawable(R.drawable.search_text_corners_shape_gray)
            searchInStock.background = context?.getDrawable(R.drawable.search_text_corners_shape_gray)
        }
        searchInStore.setOnClickListener{
            searchAllProduct.background = context?.getDrawable(R.drawable.search_text_corners_shape_gray)
            searchInStore.background = context?.getDrawable(R.drawable.search_text_corners_shape)
            searchInStock.background = context?.getDrawable(R.drawable.search_text_corners_shape_gray)
        }
        searchInStock.setOnClickListener{
            searchAllProduct.background = context?.getDrawable(R.drawable.search_text_corners_shape_gray)
            searchInStore.background = context?.getDrawable(R.drawable.search_text_corners_shape_gray)
            searchInStock.background = context?.getDrawable(R.drawable.search_text_corners_shape)
        }
    }

    fun setSerchRecycler() {
        searchProductAdapter = ProductAdapter(searchFragmentInteractor = fragmentInteractor)
        searchRecycler.adapter = searchProductAdapter
        searchRecycler.layoutManager = GridLayoutManager(context, 2,GridLayoutManager.VERTICAL,false);
        searchRecycler.addItemDecoration(SearchItemDecoration())
        searchRecycler.setNestedScrollingEnabled(false);
        viewModel.searchList.observe(viewLifecycleOwner, Observer {
            it.let {
                searchProductAdapter?.items = it
            }
        })
    }

    override fun onDestroy() {
        fragmentInteractor = null
        searchProductAdapter = null
        super.onDestroy()
    }
}