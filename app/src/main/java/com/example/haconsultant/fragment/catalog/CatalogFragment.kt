package com.example.haconsultant.fragment.catalog

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import com.example.haconsultant.R
import com.example.haconsultant.fragment.home.ProductAdapter
import com.example.haconsultant.fragment.product.ProductFragment
import com.example.haconsultant.fragment.product.ProductFragmentInteractor
import com.example.haconsultant.model.Catalog
import com.example.haconsultant.model.HomeData
import com.example.haconsultant.model.Product
import kotlinx.android.synthetic.main.fragment_catalog.*

class CatalogFragment : Fragment() {

    private var recievedCatalog: Catalog? = null

    private var fragmentInteractor: CatalogFragmentInteractor? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is CatalogFragmentInteractor) {
            fragmentInteractor = context
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_catalog, container, false)
        recievedCatalog =
            requireArguments().getParcelable(ARG_MESSAGE_CATALOG)
        // Inflate the layout for this fragment
        return view
    }

    companion object {
        private const val ARG_MESSAGE_CATALOG = "catalog"

        fun newInstance(catalog: Catalog): CatalogFragment {
            val fragment = CatalogFragment()
            val arguments = Bundle()
            arguments.putParcelable(ARG_MESSAGE_CATALOG, catalog)
            fragment.arguments = arguments
            return fragment
        }
    }

    private var catalogAdapter: CatalogAdapter? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        //Переписать
        if (recievedCatalog?.name != "Каталог") {
            catalogBtnBack.isVisible = true
        }
        catalogBtnBack.setOnClickListener {
            fragmentInteractor?.onCatalogBack()
        }
        catalogTitle.text = recievedCatalog?.name
        catalogAdapter = CatalogAdapter(fragmentInteractor)
        catalogAdapter!!.items = recievedCatalog?.listCatalog!!
        catalogRecycler.adapter = catalogAdapter
    }

}