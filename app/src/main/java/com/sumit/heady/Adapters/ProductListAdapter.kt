package com.sumit.heady.Adapters

import android.content.Intent
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityOptionsCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.sumit.heady.Activities.DetailsPageActivity
import com.sumit.heady.Activities.HomeActivity
import com.sumit.heady.Constants.PRODUCT_LIST
import com.sumit.heady.Constants.toOnlineImageLoader
import com.sumit.heady.Model.ProductX
import com.sumit.heady.R
import kotlinx.android.synthetic.main.row_products.view.*
import org.parceler.Parcel
import org.parceler.Parcels

class ProductListAdapter(
    recyclerCategory: RecyclerView,
    var homeActivity: HomeActivity,
    var productListItems: MutableList<ProductX>?
) : RecyclerView.Adapter<ProductListAdapter.ProductViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ProductViewHolder {
        return ProductViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.row_products,
                parent,
                false
            )
        )
    }

    inner class ProductViewHolder(view: View) :
        RecyclerView.ViewHolder(view) {
        init {
            itemView.setOnClickListener {

                val i = Intent(
                    homeActivity,
                    DetailsPageActivity::class.java
                ).putExtra(PRODUCT_LIST, Parcels.wrap(productListItems?.get(adapterPosition)))
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    val options = ActivityOptionsCompat.makeSceneTransitionAnimation(
                        homeActivity,
                        itemView.imgProduct,
                        homeActivity.getString(R.string.imgTransition)
                    )
                    homeActivity.startActivity(i, options.toBundle())
                } else
                    homeActivity.startActivity(i)

            }
        }

    }

    override fun getItemCount(): Int {
        return productListItems?.size!!
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        if (productListItems!!.isEmpty())
            return
        //  holder.itemView.imgProduct.toOnlineImageLoader("https://loremflickr.com/320/240/${productListItems!![position].name.last()}")


        holder.itemView.txtProductName.text = productListItems!![position].name
        /* holder.itemView.txtSize.text = productListItems!![position].variants[0].size?.toString()
         holder.itemView.txtColors.text = productListItems!![position].variants[0].color?.toString()*/
        holder.itemView.txtProductPrice.text =
            "₹ ${productListItems!![position].variants.orEmpty()[0].price}"
        holder.itemView.txtProducttax.text = "(${productListItems!![position].tax?.value}% GST)"

    }

    /**
     * clear and set data for list
     */
    fun setProducts(productListItems: MutableList<ProductX>) {
        this.productListItems?.clear()
        this.productListItems = productListItems
        notifyDataSetChanged()


    }

}
