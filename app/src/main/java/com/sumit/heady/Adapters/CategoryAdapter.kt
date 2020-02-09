package com.sumit.heady.Adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.sumit.heady.Activities.HomeActivity
import com.sumit.heady.Model.Category
import com.sumit.heady.R
import kotlinx.android.synthetic.main.row_category.view.*

class CategoryAdapter(
    var categoriesItems: MutableList<String>,
    var onCategoryListener : (String) -> Unit// set click listener callbacks
) : RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CategoryAdapter.CategoryViewHolder {
        return CategoryViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.row_category,
                parent,
                false
            )
        )
    }

    inner class CategoryViewHolder(view: View) :
        RecyclerView.ViewHolder(view) {
        init {
                itemView.cardCategory.setOnClickListener {onCategoryListener.invoke(categoriesItems[adapterPosition])  }

        }

    }

    override fun getItemCount()= categoriesItems.size

    override fun onBindViewHolder(holder: CategoryAdapter.CategoryViewHolder, position: Int) {
        if (categoriesItems.isEmpty())
            return

        holder.itemView.txtCategories.text = categoriesItems[position]

    }
    /**
     * clear and set data for list
     */
    fun setCategories(categoriesItems: MutableList<String>) {
        this.categoriesItems.clear()
        this.categoriesItems = categoriesItems.toMutableList()
        notifyDataSetChanged()


    }

}
