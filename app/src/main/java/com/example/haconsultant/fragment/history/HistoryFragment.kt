package com.example.haconsultant.fragment.history

import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.haconsultant.R
import com.example.haconsultant.fragment.catalog.CatalogAdapter
import com.example.haconsultant.fragment.product.ProductFragmentInteractor
import com.example.haconsultant.model.Orders
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_catalog.*

class HistoryFragment : Fragment(), HistoryAdapterInteractor {

    private var fragmentInteractor: HistoryFragmentInteractor? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is HistoryFragmentInteractor) {
            fragmentInteractor = context
        }
    }

    private var listOrder: List<Orders>? = null

    private var catalogAdapter: CatalogAdapter? = null

    private val compositeDisposable = CompositeDisposable()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_history, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setRecyclerView()
        val disposable = fragmentInteractor?.loadHistoryFragment()
            ?.subscribeOn(Schedulers.io())
            ?.observeOn(AndroidSchedulers.mainThread())
            ?.subscribe({
                val list = arrayListOf<String>()
                it.forEach {
                    list.add(it.name)
                }
                listOrder = it
                catalogAdapter?.items = list
                catalogAdapter?.notifyDataSetChanged()
            }, {
                AlertDialog.Builder(context).setTitle("Ошибка загрузки")
                    .setMessage(it.message)
                    .show()
            })
        compositeDisposable.add(disposable!!)
    }

    private fun setRecyclerView() {
        catalogAdapter = CatalogAdapter(historyAdapterInteractor = this)
        catalogRecycler.adapter = catalogAdapter
    }


    override fun onDestroy() {
        fragmentInteractor = null
        catalogAdapter = null
        compositeDisposable.clear()
        super.onDestroy()
    }

    override fun onOpenOreder(catalog: String) {
        listOrder?.forEach {
            if (it.name == catalog) {
                fragmentInteractor?.openOrder(it)
            }
        }
    }
}