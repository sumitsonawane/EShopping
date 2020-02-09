package com.sumit.heady.Adapters

import android.content.Intent
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.sumit.heady.Activities.DetailsPageActivity
import com.sumit.heady.Constants.PRODUCT_LIST
import com.sumit.heady.Model.Variant
import com.sumit.heady.R
import kotlinx.android.synthetic.main.row_variants.view.*
import org.parceler.Parcels

class VariantsAdapter(
    var homeActivity: DetailsPageActivity
) : RecyclerView.Adapter<VariantsAdapter.VariantViewHolder>() {

    var variantList: MutableList<Variant>? = null
    var rowSelected: Int = 0
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): VariantViewHolder {
        return VariantViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.row_variants,
                parent,
                false
            )
        )
    }

    inner class VariantViewHolder(view: View) :
        RecyclerView.ViewHolder(view) {
        init {
            itemView.setOnClickListener {
                rowSelected = adapterPosition
                notifyDataSetChanged()
            }
        }

    }

    override fun getItemCount(): Int {
        return variantList?.orEmpty()?.size!!
    }

    override fun onBindViewHolder(holder: VariantViewHolder, position: Int) {
        if (variantList!!.isEmpty())
            return


        holder.itemView.lblPrice.text =
            "₹ ${variantList!![position].price}"
        variantList!![position].size?.toInt()?.let { holder.itemView.txtSize.text = it.toString() }
        variantList!![position].color?.let {
            holder.itemView.imgColor.setBackgroundColor(
                Color.parseColor(
                    it.substring(it.lastIndexOf(' ') + 1)
                )
            )
        }

        if (rowSelected == position) {
            holder.itemView.constsVarients.background =
                homeActivity.getDrawable(R.drawable.product_item_border)
            holder.itemView.constsVarients.alpha = 1F
        } else {
            holder.itemView.constsVarients.background =
                homeActivity.getDrawable(R.drawable.product_item_border_black)
            holder.itemView.constsVarients.alpha = 0.5F

        }


    }

    /**
     * clear and set data for list
     */
    fun setVariants(variantList: MutableList<Variant>?) {
        this.variantList?.clear()
        this.variantList = variantList
        notifyDataSetChanged()


    }

}
