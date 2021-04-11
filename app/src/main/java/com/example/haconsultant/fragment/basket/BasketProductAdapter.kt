package com.example.haconsultant.fragment.basket

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.haconsultant.R
import com.example.haconsultant.model.BasketItem
import com.example.haconsultant.model.Product
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_basket.view.*

class BasketProductAdapter(
    private val basketFragmentInteractor: BasketFragmentInteractor?,
    private val basketAdapterInteractor: BasketAdapterInteractor?
) :
    RecyclerView.Adapter<BasketProductViewHolder>(), BasketRemoveListener {

    var items: MutableList<BasketItem> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BasketProductViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(R.layout.item_basket, parent, false)
        return BasketProductViewHolder(
            viewProduct = view,
            onClick = {
                basketFragmentInteractor?.onBasketOpenItem(it)
            },
            this
        )
    }

    override fun onBindViewHolder(holder: BasketProductViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun removeItem(basketItem: BasketItem, position: Int) {
        if (position != -1) {
            items.remove(basketItem)
            notifyItemRemoved(position)
            if (items.isEmpty()) {
                basketAdapterInteractor?.sizeInZero()
            }
            else {
                basketAdapterInteractor?.minusPrice(item = basketItem)
            }
        }
    }
}

class BasketProductViewHolder(
    private val viewProduct: View,
    private val onClick: (Product) -> Unit,
    private val removeListener: BasketRemoveListener
) : RecyclerView.ViewHolder(viewProduct) {
    fun bind(basketItem: BasketItem) {
        if (basketItem.product.imageUrl != null) {
            Picasso.with(viewProduct.context).load(basketItem.product.imageUrl).into(viewProduct.imageProduct)
        } else {
            viewProduct.imageProduct.setImageResource(R.mipmap.ic_launcher)
        }
        viewProduct.nameProduct.text = basketItem.product.name
        viewProduct.priceProduct.text = basketItem.product.prices.toString() + " ₽"
        viewProduct.setOnClickListener {
            onClick(basketItem.product)
        }
        viewProduct.clearProduct.setOnClickListener {
            removeListener.removeItem(basketItem, this.absoluteAdapterPosition)
        }
    }
}