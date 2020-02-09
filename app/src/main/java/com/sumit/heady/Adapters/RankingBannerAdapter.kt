package com.sumit.heady.Adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.sumit.heady.R
import kotlinx.android.synthetic.main.row_banner.view.*

class RankingBannerAdapter(
    val context: Context,
    var mostRankingItems: MutableList<String>,
    var onRankSelection: (String) -> Unit // set click listener callbacks
) : androidx.recyclerview.widget.RecyclerView.Adapter<RankingBannerAdapter.RankingViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RankingViewHolder {
        return RankingViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.row_banner,
                parent,
                false
            )
        )
    }


    override fun onBindViewHolder(holder: RankingViewHolder, position: Int) {
        if (mostRankingItems.isEmpty())
            return

        holder.itemView.txtTitle.text = mostRankingItems[position]

    }

    /**
     * clear and set data for list
     */
    fun setBannerData(mostRankingItems: MutableList<String>) {
        this.mostRankingItems.clear()
        this.mostRankingItems = mostRankingItems.toMutableList()
        notifyDataSetChanged()


    }

    override fun getItemCount() = mostRankingItems.size

    inner class RankingViewHolder(view: View) :
        androidx.recyclerview.widget.RecyclerView.ViewHolder(view) {
        init {
            itemView.constRanking.setOnClickListener { onRankSelection.invoke(mostRankingItems[adapterPosition]) }

        }

    }

}