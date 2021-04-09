package com.example.haconsultant.fragment.user

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.haconsultant.R
import kotlinx.android.synthetic.main.fragment_user.*
import kotlinx.android.synthetic.main.home_item_product.view.*
import kotlinx.android.synthetic.main.item_arrow_text.view.*

class UserFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_user, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        userOrders.arrowText.text = "Заказы"
        userBonuses.arrowText.text = "Бонусы"
        userCoupons.arrowText.text =  "Купоны"
    }
}