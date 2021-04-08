package com.example.haconsultant.fragment.home

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.haconsultant.R
import kotlinx.android.synthetic.main.fragment_home.*

class HomeFragment : Fragment() {

    val viewModel: HomeViewModel by lazy {
        ViewModelProvider(requireActivity()).get(HomeViewModel::class.java)
    }

    private var fragmentInteractor: HomeFragmentInteractor? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is HomeFragmentInteractor) {
            fragmentInteractor = context
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_home, container, false)
        return view
    }

    private var newProductAdapter: HomeProductAdapter? = null
    private var lastProductAdapter: HomeProductAdapter? = null
    private var homeCatalogAdapter: HomeCatalogAdapter? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setNewRecycler()
        setLastRecycler()
        setCatalogRecycler()
        iconQrCode.setOnClickListener {
            fragmentInteractor?.onOpenCameraQrCode()
        }
        homeBtnSearchProduct.setOnClickListener {
            fragmentInteractor?.onOpenSerch()
        }
        homeTextSearchProduct.setOnClickListener {
            fragmentInteractor?.onOpenSerch()
        }
    }

    fun setNewRecycler(){
        newProductAdapter = HomeProductAdapter(fragmentInteractor)
        viewModel.newList.observe(viewLifecycleOwner, Observer {
            it.let {
                newProductAdapter?.items = it
            }
        })
        homeRecyclerNew.adapter = newProductAdapter
    }

    fun setLastRecycler(){
        lastProductAdapter = HomeProductAdapter(fragmentInteractor)
        viewModel.lastList.observe(viewLifecycleOwner, Observer {
            it.let {
                lastProductAdapter?.items = it
            }
        })
        homeRecyclerWatched.adapter = lastProductAdapter
    }

    fun setCatalogRecycler(){
        homeCatalogAdapter = HomeCatalogAdapter(fragmentInteractor)
        viewModel.catalogList.observe(viewLifecycleOwner, Observer {
            it.let {
                homeCatalogAdapter?.list = it
            }
        })
        homeRecyclerСatalog.adapter = homeCatalogAdapter
        homeRecyclerСatalog.addItemDecoration(HomeCatalogItemDecoration())
    }

    override fun onDestroy() {
        fragmentInteractor = null
        newProductAdapter = null
        lastProductAdapter = null
        homeCatalogAdapter = null
        super.onDestroy()
    }
}