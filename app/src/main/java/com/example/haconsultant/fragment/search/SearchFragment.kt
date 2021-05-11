package com.example.haconsultant.fragment.search

import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.core.widget.doOnTextChanged
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.example.haconsultant.R
import com.example.haconsultant.firebase.api.TypeSort
import com.example.haconsultant.fragment.home.ProductAdapter
import com.example.haconsultant.model.Product
import kotlinx.android.synthetic.main.fragment_product.*
import kotlinx.android.synthetic.main.fragment_search.*

class SearchFragment : Fragment(), PopupMenu.OnMenuItemClickListener {

    private var fragmentInteractor: SearchFragmentInteractor? = null

    var positionScroll = 0

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
        searchScrollView.scrollTo(0, positionScroll)
        setSerchRecycler()
        searchBtnBack.setOnClickListener {
            fragmentInteractor?.onSearchBack()
        }
        searchBtnSearchProduct.setOnClickListener {
            searchTextSearchProduct.isVisible = true
            searchTitle.isVisible = false
            searchBtnSearchProduct.isVisible = false
        }
        viewModel.statePlace.observe(viewLifecycleOwner, Observer {
            when (it) {
                StatePlace.All -> {
                    searchAllProduct.background =
                        context?.getDrawable(R.drawable.search_text_corners_shape)
                    searchInStore.background =
                        context?.getDrawable(R.drawable.search_text_corners_shape_gray)
                    searchInStock.background =
                        context?.getDrawable(R.drawable.search_text_corners_shape_gray)
                }
                StatePlace.InStore -> {
                    searchAllProduct.background =
                        context?.getDrawable(R.drawable.search_text_corners_shape_gray)
                    searchInStore.background =
                        context?.getDrawable(R.drawable.search_text_corners_shape)
                    searchInStock.background =
                        context?.getDrawable(R.drawable.search_text_corners_shape_gray)
                }
                StatePlace.Warehouse -> {
                    searchAllProduct.background =
                        context?.getDrawable(R.drawable.search_text_corners_shape_gray)
                    searchInStore.background =
                        context?.getDrawable(R.drawable.search_text_corners_shape_gray)
                    searchInStock.background =
                        context?.getDrawable(R.drawable.search_text_corners_shape)
                }
            }
        })
        searchAllProduct.setOnClickListener {
            viewModel.setStatePlace(StatePlace.All)
        }
        searchInStore.setOnClickListener {
            viewModel.setStatePlace(StatePlace.InStore)
        }
        searchInStock.setOnClickListener {
            viewModel.setStatePlace(StatePlace.Warehouse)
        }
        searchBySort.setOnClickListener {
            showPopup()
        }
        searchFilter.setOnClickListener {
            fragmentInteractor?.onSearchOpenFilter()
        }
        viewModel.searhSort.observe(viewLifecycleOwner, Observer {
            if (viewModel.feature.value?.typeSort != it) {
                viewModel.feature.value?.typeSort = it
                fragmentInteractor?.featureCatalog(viewModel.feature.value!!)
            }
            when (it) {
                TypeSort("prices", "asc") -> {
                    searchBySortText.text = "Сначала дещёвые"
                }
                TypeSort("prices", "desc") -> {
                    searchBySortText.text = "Сначала дорогие"
                }
                TypeSort("evaluation", "desc") -> {
                    searchBySortText.text = "По рейтингу"
                }
            }
        })
        searchTextSearchProduct.doOnTextChanged { text, start, before, count ->
            searchText(text.toString())
        }
    }

    private fun searchText(text: String) {
        val list = arrayListOf<Product>()
        viewModel.searchList.value?.forEach {
            if (it.name.toLowerCase().contains(text.toLowerCase())) {
                list.add(it)
            }
        }
        searchProductAdapter?.items = list
        searchProductAdapter?.notifyDataSetChanged()
    }

    private fun setSerchRecycler() {
        searchProductAdapter = ProductAdapter(searchFragmentInteractor = fragmentInteractor)
        searchRecycler.adapter = searchProductAdapter
        searchRecycler.layoutManager =
            GridLayoutManager(context, 2, GridLayoutManager.VERTICAL, false);
        searchRecycler.addItemDecoration(SearchItemDecoration())
        searchRecycler.setNestedScrollingEnabled(false);
        viewModel.searchList.observe(viewLifecycleOwner, Observer {
            it.let {
                searchProductAdapter?.items = it
                searchProductAdapter?.notifyDataSetChanged()
                searchTitle.text = viewModel.feature.value?.nameCatalog
                if (!searchTextSearchProduct.text.isEmpty()) {
                    searchText(searchTextSearchProduct.text.toString())
                    searchTitle.text = searchTextSearchProduct.text
                }
            }
        })
    }


    override fun onStop() {
        positionScroll = searchScrollView.getScrollY()
        super.onStop()
    }

    override fun onDestroy() {
        fragmentInteractor = null
        searchProductAdapter = null
        super.onDestroy()
    }

    private fun showPopup() {
        val popupMenu = PopupMenu(context, searchBySortText);
        popupMenu.setOnMenuItemClickListener(this)
        popupMenu.inflate(R.menu.poput_by_sort)
        popupMenu.show()
    }

    override fun onMenuItemClick(item: MenuItem?): Boolean {
        when (item?.itemId) {
            R.id.searchPriceAsc -> {
                viewModel.setSort(TypeSort("prices", "asc"))
                return true
            }
            R.id.searchPriceDesc -> {
                viewModel.setSort(TypeSort("prices", "desc"))
                return true
            }
            R.id.searchUs -> {
                viewModel.feature.value?.priceMax = null
                viewModel.feature.value?.priceMin = null
                viewModel.setSort(TypeSort("evaluation", "desc"))
                return true
            }
            else -> {
                return false
            }
        }
    }

}

enum class StatePlace {
    All,
    InStore,
    Warehouse
}