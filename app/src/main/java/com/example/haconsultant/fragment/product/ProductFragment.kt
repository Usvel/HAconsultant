package com.example.haconsultant.fragment.product

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.viewpager.widget.ViewPager
import androidx.viewpager2.widget.ViewPager2
import com.example.haconsultant.R
import com.example.haconsultant.model.HomeData
import kotlinx.android.synthetic.main.fragment_product.*

class ProductFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_product, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val adapter = ProductPagerAdapter()
        adapter.items = HomeData.getLinsPage()
        productViewPager.adapter = adapter
        productIndicator.setViewPager(productViewPager)
        productRatingBar.setOnClickListener {
            Toast.makeText(this.context,productRatingBar.rating.toString(), Toast.LENGTH_SHORT).show()
        }
    }
}