package com.example.haconsultant.fragment.home

import android.content.Context
import android.graphics.Color
import android.graphics.Rect
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatImageView
import androidx.recyclerview.widget.RecyclerView
import coil.ImageLoader
import coil.decode.SvgDecoder
import coil.request.ImageRequest
import com.example.haconsultant.R
import com.example.haconsultant.model.HomeCatalog
import kotlinx.android.synthetic.main.home_item_catalog.view.*
import kotlinx.android.synthetic.main.home_item_product.view.*

class HomeCatalogAdapter(private val fragmentInteractor: HomeFragmentInteractor?) :
    RecyclerView.Adapter<HomeViewHolderCaralog>(),
    HomeDecorationTypeProvider {

    var list: List<HomeCatalog> = listOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeViewHolderCaralog {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(R.layout.home_item_catalog, parent, false)
        return HomeViewHolderCaralog(view, { fragmentInteractor?.onHomeOpenCatalog(it) })
    }

    override fun onBindViewHolder(holder: HomeViewHolderCaralog, position: Int) {
        holder.bind(list[position])
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun getTypeProsition(position: Int, context: Context): Boolean {
        if (position == RecyclerView.NO_POSITION) {
            return false
        }

        if (list.isEmpty()) {
            return false
        }

        if (position == (list.lastIndex)) {
            return false
        }
        return true
    }
}

class HomeViewHolderCaralog(
    private val homeViewCatalog: View,
    private val onClick: (HomeCatalog) -> Unit
) : RecyclerView.ViewHolder(homeViewCatalog) {
    fun bind(homeCatalog: HomeCatalog) {
        homeViewCatalog.homeNameCatalog.text = homeCatalog.name
        if (homeCatalog.imageUrl == null) {
            homeViewCatalog.homeCatalogFrame.setBackgroundColor(Color.parseColor("#6200EE"))
            homeViewCatalog.homeImageCatalog.setImageResource(R.drawable.ic_baseline_menu_open_24)
        } else {
            //Picasso.with(homeViewCatalog.context).load(homeCatalog.imageUrl).into(homeViewCatalog.homeImageCatalog)
            (homeViewCatalog.homeImageCatalog as AppCompatImageView).loadSvg(homeCatalog.imageUrl)
            homeViewCatalog.homeCatalogFrame.setBackgroundColor(Color.GRAY)
        }
        homeViewCatalog.setOnClickListener {
            onClick(homeCatalog)
        }
    }
}

class HomeCatalogItemDecoration : RecyclerView.ItemDecoration() {

    companion object {
        private val MARGIN_RIGHT = 10
    }

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        val position = parent.getChildAdapterPosition(view)
        if (position == RecyclerView.NO_POSITION) {
            return
        }

        val adapter = parent.adapter
        if (adapter is HomeDecorationTypeProvider) {
            val type = adapter.getTypeProsition(position, parent.context)
            outRect.right = if (type) {
                MARGIN_RIGHT.dpToPx(parent.context).toInt()
            } else {
                0
            }
        }
    }
}

fun AppCompatImageView.loadSvg(url: String) {
    val imageLoader = ImageLoader.Builder(this.context)
        .componentRegistry { add(SvgDecoder(this@loadSvg.context)) }
        .build()

    val request = ImageRequest.Builder(this.context)
        .crossfade(true)
        .crossfade(500)
        .data(url)
        .target(this)
        .build()

    imageLoader.enqueue(request)
}

fun Int.dpToPx(context: Context) = TypedValue.applyDimension(
    TypedValue.COMPLEX_UNIT_DIP,
    this.toFloat(),
    context.resources.displayMetrics
)