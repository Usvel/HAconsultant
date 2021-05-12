package com.example.haconsultant.fragment.user

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.haconsultant.R
import com.example.haconsultant.fragment.filter.IteamPriceFragmentInteractor
import kotlinx.android.synthetic.main.fragment_setings.*

class SetingsFragment : Fragment() {

    private var fragmentInteractor: SetingsFragmentInteractor? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is SetingsFragmentInteractor) {
            fragmentInteractor = context
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        settingsBtnOk.setOnClickListener {
            if (!settingsEditName.text.isEmpty()) {
                Log.d("User", settingsEditName.text.toString())
                fragmentInteractor?.onSettingsAddName(settingsEditName.text.toString())
            }
        }
    }

    override fun onDestroy() {
        fragmentInteractor = null
        super.onDestroy()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_setings, container, false)
    }

}