package com.example.haconsultant.fragment.home

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.haconsultant.R
import com.example.haconsultant.model.HomeCatalog
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.fragment_product.*

class HomeFragment : Fragment() {

    private var positionScroll = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        retainInstance = true;
    }

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

    private var newProductAdapter: ProductAdapter? = null
    private var lastProductAdapter: ProductAdapter? = null
    private var homeCatalogAdapter: HomeCatalogAdapter? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        viewModel.statusNewList.observe(viewLifecycleOwner, Observer {
            it.let {
                when (it) {
                    Status.Success -> {
                        //шиммер
                        homeTextNew.isVisible = true
                        homeRecyclerNew.isVisible = true
                    }
                    Status.Failure -> {
                        //шиммер
                        homeTextNew.isVisible = false
                        homeRecyclerNew.isVisible = false
                    }
                    else -> Unit
                }
            }
        })

        setNewRecycler()
        setLastRecycler()
        setCatalogRecycler()
        iconQrCode.setOnClickListener {
            fragmentInteractor?.onHomeOpenCameraQrCode()
        }
        homeBtnSearchProduct.setOnClickListener {
            fragmentInteractor?.onHomeOpenSerch()
        }
//        homeTextSearchProduct.setOnClickListener {
//            fragmentInteractor?.onHomeOpenSerch()
//        }
        homeTextSearchProduct.setOnFocusChangeListener { v, hasFocus ->
            if (hasFocus == true) {
                fragmentInteractor?.onHomeOpenSerch()
            }
        }
        homeScrollView.scrollTo(0, positionScroll)
    }

    fun setNewRecycler() {
        newProductAdapter = ProductAdapter(homeFragmentInteractor = fragmentInteractor)
        viewModel.newList.observe(viewLifecycleOwner, Observer {
            it.let {
                newProductAdapter?.items = it
                newProductAdapter?.notifyItemInserted(it.size)
            }
        })
        homeRecyclerNew.adapter = newProductAdapter
    }

    fun setLastRecycler() {
        lastProductAdapter = ProductAdapter(homeFragmentInteractor = fragmentInteractor)
        viewModel.lastList.observe(viewLifecycleOwner, Observer {
            it.let {
                lastProductAdapter?.items = it
                lastProductAdapter?.notifyDataSetChanged()
                if (it.size != 0) {
                    homeRecyclerWatched.isVisible = true
                    homeLastText.isVisible = true
                }
            }
        })
        homeRecyclerWatched.adapter = lastProductAdapter
    }

    fun setCatalogRecycler() {
        homeCatalogAdapter = HomeCatalogAdapter(fragmentInteractor)
        viewModel.catalogList.observe(viewLifecycleOwner, Observer {
            it.let {
                val listCatalog: MutableList<HomeCatalog> = arrayListOf()
                listCatalog.add(0, HomeCatalog(name = "Каталог", imageUrl = null))
                it.forEach { listCatalog.add(it) }
                homeCatalogAdapter?.list = listCatalog
                homeCatalogAdapter?.notifyDataSetChanged()
            }
        })
        homeRecyclerСatalog.adapter = homeCatalogAdapter
        homeRecyclerСatalog.addItemDecoration(HomeCatalogItemDecoration())
    }

    override fun onStop() {
        positionScroll = homeScrollView.getScrollY()
        super.onStop()
    }

    override fun onDestroy() {
        fragmentInteractor = null
        newProductAdapter = null
        lastProductAdapter = null
        homeCatalogAdapter = null
        super.onDestroy()
    }
}