package com.sumit.heady.Activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView.HORIZONTAL
import com.sumit.heady.Adapters.VariantsAdapter
import com.sumit.heady.Constants.PRODUCT_LIST
import com.sumit.heady.Constants.disableItemAnimator
import com.sumit.heady.Model.ProductX
import com.sumit.heady.R
import kotlinx.android.synthetic.main.activity_details_page.*
import org.parceler.Parcels

class DetailsPageActivity : AppCompatActivity() {
    private var getProductDetails: ProductX? = null
    var variantsAdapter: VariantsAdapter? = null
    var qtyCount: Int = 1
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details_page)

        getProductDetails = Parcels.unwrap(intent?.getParcelableExtra(PRODUCT_LIST))
        setRecyclerAdapter()
        setDetails()
        setListeners()
    }

    private fun setListeners() {

        imgMinus.setOnClickListener {
            if (qtyCount != 1) qtyCount -= 1
            txtProductQty.text = qtyCount.toString()
        }
        imgAdd.setOnClickListener {
            if (qtyCount <10) qtyCount += 1
            txtProductQty.text = qtyCount.toString()
        }


    }

    private fun setRecyclerAdapter() {
        val mLayoutManager = LinearLayoutManager(this, HORIZONTAL, false)
        rvVariants.layoutManager = mLayoutManager
        variantsAdapter = VariantsAdapter(this@DetailsPageActivity)
        rvVariants.disableItemAnimator()
        rvVariants.adapter = variantsAdapter
    }

    private fun setDetails() {
        txtProducttax.text = "GST: ${getProductDetails?.tax?.value}% GST"
        txtProductName.text = getProductDetails?.name
        variantsAdapter?.setVariants(getProductDetails?.variants)

        /*getProductDetails?.variants?.map { it.size }?.forEach {
            val chip = Chip(groupChips.context, null, R.style.Widget_MaterialComponents_Chip_Entry)
            chip.text = it?.toInt().toString()
            chip.isCloseIconVisible = false
            chip.isClickable = false
            chip.isCheckable = true
            groupChips.addView(chip as View)
            chip.setOnCloseIconClickListener { groupChips.removeView(chip as View) }
        }*/


    }
}
