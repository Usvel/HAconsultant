package com.example.haconsultant.fragment.filter

import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.example.haconsultant.R
import com.example.haconsultant.fragment.search.SearhViewModel
import kotlinx.android.synthetic.main.fragment_iteam_price.*
import kotlinx.android.synthetic.main.fragment_search_filter.*


class IteamPriceFragment : Fragment() {

    val viewModel: SearhViewModel by lazy {
        ViewModelProvider(requireActivity()).get(SearhViewModel::class.java)
    }

    private var fragmentInteractor: IteamPriceFragmentInteractor? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is IteamPriceFragmentInteractor) {
            fragmentInteractor = context
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_iteam_price, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        if (viewModel.feature.value?.priceMin != null) {
            priceMin.text = Editable.Factory.getInstance()
                .newEditable(viewModel.feature.value?.priceMin.toString())
        }

        if (viewModel.feature.value?.priceMax != null) {
            priceMax.text = Editable.Factory.getInstance()
                .newEditable(viewModel.feature.value?.priceMax.toString())
        }

        itemPriceBtnBack.setOnClickListener {
            fragmentInteractor?.onSearchPriceBack()
        }
        priceOkBtn.setOnClickListener {
            var min: Int? = null
            var max: Int? = null
            var compare = true
            if (!priceMin.text.isEmpty()) {
                min = Integer.parseInt(priceMin.text.toString())
            } else {
                compare = false
            }
            if (!priceMax.text.isEmpty()) {
                max = Integer.parseInt(priceMax.text.toString())
            } else {
                compare = false
            }

            if (compare) {
                if (max!! < min!!) {
                    Toast.makeText(context, "Неправильно задан диапазон", Toast.LENGTH_SHORT).show()
                } else {
                    fragmentInteractor?.onSearchAddPrice(min, max)
                }
            } else {
                fragmentInteractor?.onSearchAddPrice(min, max)
            }
            Log.d("Price", min.toString() + " " + max.toString())
        }
    }

}