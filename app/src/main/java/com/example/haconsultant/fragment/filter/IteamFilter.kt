package com.example.haconsultant.fragment.filter

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.example.haconsultant.R
import com.example.haconsultant.fragment.catalog.CatalogAdapter
import com.example.haconsultant.fragment.product.ProductFragment
import com.example.haconsultant.fragment.search.SearchFragmentInteractor
import com.example.haconsultant.fragment.search.SearhViewModel
import com.example.haconsultant.model.Product
import kotlinx.android.synthetic.main.fragment_iteam_filter.*
import kotlinx.android.synthetic.main.fragment_search_filter.*
import kotlinx.android.synthetic.main.fragment_search_filter.searchFilterRecycler


class IteamFilter : Fragment() {

    private var fragmentInteractor: IteamFilterFragmentInteractor? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is IteamFilterFragmentInteractor) {
            fragmentInteractor = context
        }
    }

    val viewModel: SearhViewModel by lazy {
        ViewModelProvider(requireActivity()).get(SearhViewModel::class.java)
    }

    private var iteamFilterAdapter: IteamFilterAdapter? = null

    companion object {
        private const val ARG_MESSAGE_FILTER = "product"

        fun newInstance(name: String): IteamFilter {
            val fragment = IteamFilter()
            val arguments = Bundle()
            arguments.putSerializable(ARG_MESSAGE_FILTER, name)
            fragment.arguments = arguments
            return fragment
        }

    }

    private var recievedName: String? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_iteam_filter, container, false)
        recievedName = requireArguments().getString(ARG_MESSAGE_FILTER)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setRecyclerView()
        if (recievedName != null) {
            if (recievedName == "manufacturer") {
                iteamFilterTitle.text = "Производитель"
            } else {
                iteamFilterTitle.text = recievedName
            }
            val listIteam = arrayListOf<IteamFilterData>()

            (viewModel.mapFilter.value?.get(recievedName) as List<Any>).forEach {
                if (viewModel.feature.value?.map?.get(recievedName) == it) {
                    listIteam.add(IteamFilterData(recievedName!!, it, true))
                } else {
                    listIteam.add(IteamFilterData(recievedName!!, it, false))
                }
            }
            iteamFilterAdapter?.items = listIteam
            //iteamFilterAdapter?.items = viewModel.mapFilter.value?.get(recievedName) as List<Any>
            iteamFilterAdapter?.notifyDataSetChanged()
        }
        itemFilterBtnBack.setOnClickListener {
            fragmentInteractor?.onIteamFilterBack()
        }
    }

    private fun setRecyclerView() {
        iteamFilterAdapter = IteamFilterAdapter(fragmentInteractor)
        searchFilterRecycler.adapter = iteamFilterAdapter
        //catalogAdapter?.items = HomeData.getLinsPage()
    }

}

data class IteamFilterData(
    val key: String,
    val value: Any,
    var check: Boolean
)