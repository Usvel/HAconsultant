package com.example.haconsultant.fragment.filter

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.haconsultant.R
import com.example.haconsultant.firebase.api.TypeSort
import com.example.haconsultant.fragment.catalog.CatalogAdapter
import com.example.haconsultant.fragment.catalog.CatalogFragmentInteractor
import com.example.haconsultant.fragment.search.SearhViewModel
import com.example.haconsultant.model.HomeData
import kotlinx.android.synthetic.main.fragment_search_filter.*
import kotlinx.android.synthetic.main.item_arrow_text.view.*

class SearchFilter : Fragment() {

    private var fragmentInteractor: SearchFilterFragmentInteractor? = null

    val viewModel: SearhViewModel by lazy {
        ViewModelProvider(requireActivity()).get(SearhViewModel::class.java)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is SearchFilterFragmentInteractor) {
            fragmentInteractor = context
        }
    }

    private var catalogAdapter: CatalogAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_search_filter, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setRecyclerView()
        viewModel.mapFilter.observe(viewLifecycleOwner, Observer {
            val listString = arrayListOf<String>()
            if (viewModel.feature.value?.typeSort == TypeSort("evaluation", "desc")) {
                searchPrice.isVisible = false
            } else {
                searchPrice.arrowText.text = "Цена"
            }
            it.forEach {
                if (it.key == "manufacturer") {
                    searchManufacturer.arrowText.text = "Производитель"
                    searchPrice.arrowText.text = "Цена"
                } else {
                    listString.add(it.key)
                }
            }
            catalogAdapter?.items = listString
            catalogAdapter?.notifyDataSetChanged()
        })
        searchManufacturer.setOnClickListener {
            fragmentInteractor?.openSearchFilter("manufacturer")
        }

        searchFilterBtnBack.setOnClickListener {
            fragmentInteractor?.onSearchFilterBack()
        }
        searchPrice.setOnClickListener {
            fragmentInteractor?.openPriceFilter()
        }
    }

    private fun setRecyclerView() {
        catalogAdapter = CatalogAdapter(searchFilterFragmentInteractor = fragmentInteractor)
        searchFilterRecycler.adapter = catalogAdapter
        //catalogAdapter?.items = HomeData.getLinsPage()
    }

}