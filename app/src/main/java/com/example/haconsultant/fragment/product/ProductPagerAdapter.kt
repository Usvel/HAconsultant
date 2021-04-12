package com.example.haconsultant.fragment.product

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.example.haconsultant.R
import com.squareup.picasso.Picasso

class ProductPagerAdapter : RecyclerView.Adapter<ProductPagerAdapter.Pager2ViewHolder>() {
    inner class Pager2ViewHolder(val itemView: View) : RecyclerView.ViewHolder(itemView) {
        val itemImage = itemView.findViewById<ImageView>(R.id.productPageImage)
    }

    var items: List<String> = listOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Pager2ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(R.layout.product_item_page, parent, false)
        return Pager2ViewHolder(view)
    }

    override fun onBindViewHolder(holder: Pager2ViewHolder, position: Int) {
        Picasso.with(holder.itemImage.context).load(items[position]).into(holder.itemImage)
    }

    override fun getItemCount(): Int {
        return items.size
    }
}
