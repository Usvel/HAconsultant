package com.example.haconsultant.fragment.home

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.example.haconsultant.R
import com.example.haconsultant.fragment.catalog.SearchDecorationTypeProvider
import com.example.haconsultant.fragment.catalog.SearchFragmentInteractor
import com.example.haconsultant.fragment.product.ProductFragmentInteractor
import com.example.haconsultant.model.Product
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.home_item_product.view.*

class ProductAdapter(
    private val homeFragmentInteractor: HomeFragmentInteractor? = null,
    private val searchFragmentInteractor: SearchFragmentInteractor? = null,
    private val productFragmentInteractor: ProductFragmentInteractor? = null
) :
    RecyclerView.Adapter<ProductViewHolder>(), SearchDecorationTypeProvider {

    var items: List<Product> = listOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(R.layout.home_item_product, parent, false)
        if (homeFragmentInteractor != null)
            return ProductViewHolder(
                view,
                { homeFragmentInteractor.onHomeOpenItem(it) }
            )
        else {
            if (searchFragmentInteractor != null) {
                return ProductViewHolder(
                    view,
                    { searchFragmentInteractor.onSearchOpenItem(it) }
                )
            } else {
                return ProductViewHolder(
                    view,
                    { productFragmentInteractor?.onProductOpenItem(it)}
                )
            }
        }
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun getTypeProsition(position: Int, context: Context): Boolean {
        if (position == RecyclerView.NO_POSITION) {
            return false
        }

        if (items.isEmpty()) {
            return false
        }

        if (position.rem(2) == 1) {
            return false
        }
        return true
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