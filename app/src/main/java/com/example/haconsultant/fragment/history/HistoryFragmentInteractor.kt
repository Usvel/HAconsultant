package com.example.haconsultant.fragment.history

import com.example.haconsultant.model.Orders
import io.reactivex.Single

interface HistoryFragmentInteractor {
    fun loadHistoryFragment(): Single<List<Orders>>
    fun openOrder(orders: Orders)
}