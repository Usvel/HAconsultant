package com.example.haconsultant.fragment.user

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.haconsultant.R
import kotlinx.android.synthetic.main.fragment_user.*
import kotlinx.android.synthetic.main.home_item_product.view.*
import kotlinx.android.synthetic.main.item_arrow_text.view.*

class UserFragment : Fragment() {

    var fragmentInteractor: UserFragmentInteractor? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is UserFragmentInteractor) {
            fragmentInteractor = context as UserFragmentInteractor
        }
    }

    val viewModel: UserViewModel by lazy {
            ViewModelProvider(requireActivity()).get(UserViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_user, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setText()
        viewModel.name.observe(viewLifecycleOwner, Observer {
            it.let {
               userName.text = it
            }
        })
        viewModel.id.observe(viewLifecycleOwner, Observer {
            it.let{
                userId.text = it
            }
        })
        userIconQrCode.setOnClickListener {
            fragmentInteractor?.onUserOpenCameraQrCode()
        }
    }

    private fun setText(){
        userOrders.arrowText.text = "Заказы"
        userBonuses.arrowText.text = "Бонусы"
        userCoupons.arrowText.text =  "Купоны"
    }

    override fun onDestroy() {
        fragmentInteractor = null
        super.onDestroy()
    }
}