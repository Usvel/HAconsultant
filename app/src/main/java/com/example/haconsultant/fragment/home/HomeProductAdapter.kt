package com.example.haconsultant.fragment.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.example.haconsultant.R
import com.example.haconsultant.model.Product
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.home_item_product.view.*

class HomeProductAdapter(private val fragmentInteractor: HomeFragmentInteractor?) :
    RecyclerView.Adapter<ProductViewHolder>() {

    var items: List<Product> = listOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(R.layout.home_item_product, parent, false)
        return ProductViewHolder(view, { fragmentInteractor?.onOpenItem(it) })
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int {
        return items.size
    }
}

class ProductViewHolder(private val viewProduct: View, private val onClick: (Product) -> Unit) :
    RecyclerView.ViewHolder(viewProduct) {
    fun bind(product: Product) {
        if (product.imageUrl != null) {
            Picasso.with(viewProduct.context).load(product.imageUrl).into(viewProduct.imageProduct)
        } else {
            viewProduct.imageProduct.setImageResource(R.mipmap.ic_launcher)
        }
        if (product.sizeReviews == 0) {
            viewProduct.starProduct.setImageResource(R.drawable.ic_baseline_star_grey_24)
            viewProduct.evaluationProduct.isVisible = false
            viewProduct.sizeReviewsProduct.text = "Нет отзывов"

        } else {
            viewProduct.starProduct.setImageResource(R.drawable.ic_baseline_star_red_24)
            viewProduct.evaluationProduct.isVisible = true
            viewProduct.evaluationProduct.text = product.evaluation.toString()
            viewProduct.sizeReviewsProduct.text = product.sizeReviews.toString() + " отзывов"
        }
        viewProduct.nameProduct.text = product.name
        viewProduct.priceProduct.text = product.prices.toString() + " ₽"
        viewProduct.setOnClickListener {
            onClick(product)
        }
    }
}