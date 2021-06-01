package com.example.haconsultant.fragment.additional

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.haconsultant.R
import com.example.haconsultant.fragment.catalog.CatalogFragment
import com.example.haconsultant.fragment.product.ProductFragmentInteractor
import com.example.haconsultant.model.Catalog
import kotlinx.android.synthetic.main.fragment_addirional.*

class AdditionalFragment : Fragment() {

    private var fragmentInteractor: AddirionalFragmentInteractor? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is AddirionalFragmentInteractor) {
            fragmentInteractor = context
        }
    }

    private var info: String? = null
    private var title: String? = null

    companion object {
        private const val ARG_MESSAGE_STRING = "info"
        private const val ARG_MESSAGE_TITLE = "title"

        fun newInstance(info: String, title: String): AdditionalFragment {
            val fragment = AdditionalFragment()
            val arguments = Bundle()
            arguments.putString(ARG_MESSAGE_STRING, info)
            arguments.putString(ARG_MESSAGE_TITLE, title)
            fragment.arguments = arguments
            return fragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_addirional, container, false)
        info =
            requireArguments().getString(ARG_MESSAGE_STRING)

        title = requireArguments().getString(ARG_MESSAGE_TITLE)


        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (info != null) {
            addirionalText.text = info
        }
        if (title != null) {
            addirionalTitle.text = title
        }
        addirionalBtnBack.setOnClickListener {
            fragmentInteractor?.onAdditionalBack()
        }
    }
}