package com.example.haconsultant.fragment.filter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.haconsultant.R
import com.example.haconsultant.fragment.catalog.CatalogFragmentInteractor
import com.example.haconsultant.fragment.catalog.CatalogViewHolder
import kotlinx.android.synthetic.main.iteam_filter.view.*
import kotlinx.android.synthetic.main.item_arrow_text.view.*

class IteamFilterAdapter(private val iteamFilterFragmentInteractor: IteamFilterFragmentInteractor? = null) :
    RecyclerView.Adapter<IteamFilterViewHolder>() {

    var items: List<IteamFilterData> = listOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IteamFilterViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(R.layout.iteam_filter, parent, false)
        return IteamFilterViewHolder(view, {
            items.forEachIndexed { index, iteamFilterData ->
                if (iteamFilterData.value == it) {
                    if (iteamFilterData.check == false) {
                        iteamFilterFragmentInteractor?.onAddMap(
                            key = iteamFilterData.key,
                            value = it
                        )
                        iteamFilterData.check = true
                    } else {
                        iteamFilterFragmentInteractor?.onRemoveMap(key = iteamFilterData.key)
                        iteamFilterData.check = false
                    }
                } else {
                    iteamFilterData.check = false
                }
                notifyDataSetChanged()
            }
        })
    }

    override fun onBindViewHolder(holder: IteamFilterViewHolder, position: Int) {
        holder.onBind(items[position])
    }

    override fun getItemCount(): Int {
        return items.size
    }

}

class IteamFilterViewHolder(
    val view: View,
    private val onClick: (Any) -> Unit
) :
    RecyclerView.ViewHolder(view) {
    fun onBind(iteamFilterData: IteamFilterData) {
        view.iteamFilterText.text = iteamFilterData.value.toString()
        view.iteamFilterCheck.isChecked = iteamFilterData.check
        view.setOnClickListener {
            onClick(iteamFilterData.value)
        }
    }
}