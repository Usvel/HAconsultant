package com.example.haconsultant.fragment.user

import android.app.AlertDialog
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
            it.let {
                userId.text = it
            }
        })
        userIconQrCode.setOnClickListener {
            fragmentInteractor?.onUserOpenCameraQrCode()
        }
        userSetings.setOnClickListener {
            if (viewModel.id.value != "00000000") {
                fragmentInteractor?.openSetings()
            } else {
                AlertDialog.Builder(context).setTitle("Профиль").setMessage("Вы не вошли").show()
            }
        }
        userHistori.setOnClickListener {
            if (viewModel.id.value != "00000000") {
                fragmentInteractor?.openHistory()
            } else {
                AlertDialog.Builder(context).setTitle("Профиль").setMessage("Вы не вошли").show()
            }
        }
    }

    private fun setText() {
        userSetings.arrowText.text = "Настройки"
        userHistori.arrowText.text = "История заказов"
        userCoupons.arrowText.text = "Купоны"
    }

    override fun onDestroy() {
        fragmentInteractor = null
        super.onDestroy()
    }
}