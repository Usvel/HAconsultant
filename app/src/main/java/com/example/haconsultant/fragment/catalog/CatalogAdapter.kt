package com.example.haconsultant.fragment.catalog

import android.graphics.Rect
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.haconsultant.R
import com.example.haconsultant.fragment.home.HomeFragmentInteractor
import com.example.haconsultant.model.Catalog
import com.example.haconsultant.model.Product
import kotlinx.android.synthetic.main.item_arrow_text.view.*

class CatalogAdapter(private val catalogfragmentInteractor: CatalogFragmentInteractor? = null) :
    RecyclerView.Adapter<CatalogViewHolder>() {

    var items: List<Catalog> = listOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CatalogViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(R.layout.item_arrow_text, parent, false)
        return CatalogViewHolder(view, { catalogfragmentInteractor?.onCatalogOpenNext(it) })
    }

    override fun onBindViewHolder(holder: CatalogViewHolder, position: Int) {
        holder.onBind(items[position])
    }

    override fun getItemCount(): Int {
        return items.size
    }

}

class CatalogViewHolder(
    val view: View,
    private val onClick: (Catalog) -> Unit
) :
    RecyclerView.ViewHolder(view) {
    fun onBind(catalog: Catalog) {
        view.arrowText.text = catalog.name
        view.setOnClickListener {
            onClick(catalog)
        }
    }
}