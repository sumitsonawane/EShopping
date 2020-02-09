package com.sumit.heady.Activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager.HORIZONTAL
import com.sumit.heady.Adapters.CategoryAdapter
import com.sumit.heady.Adapters.ProductListAdapter
import com.sumit.heady.Adapters.RankingBannerAdapter
import com.sumit.heady.Constants.createFactory
import com.sumit.heady.Constants.disableItemAnimator
import com.sumit.heady.Model.ApiResponseStats
import com.sumit.heady.Model.GetAllProducts
import com.sumit.heady.Model.ProductX
import com.sumit.heady.R
import com.sumit.heady.ViewModel.HomeViewModel
import kotlinx.android.synthetic.main.activity_main.*
import java.net.HttpURLConnection.HTTP_OK

/**
 * Home page for all products
 */
class HomeActivity : AppCompatActivity() {

    private var getAllProducts: GetAllProducts? = null
    private lateinit var rankingBannerAdapter: RankingBannerAdapter
    val mostRankingItems = ArrayList<String>()
    var categoryAdapter: CategoryAdapter? = null
    var productListAdapter: ProductListAdapter? = null
    var productListItems: MutableList<ProductX>? = mutableListOf()
    var categoriesItems = ArrayList<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setUpToolbar()

        setRankingsAdapter()
        setupCatogoriesAdapter()
        getProductsViewmodel()
        setupProductsAdapter()

        swHomepage.setOnRefreshListener { getProductsViewmodel() }
    }

    private fun setUpToolbar() {
        setSupportActionBar(toolbar)
        toolbar?.navigationIcon = ContextCompat.getDrawable(this,R.drawable.ic_launcher_foreground)
    }

    /**
     * set Rankings list in Banner Adapter
     * also get callbacks for rank selection and the result in product list
     */
    private fun setRankingsAdapter() {
        rankingBannerAdapter = RankingBannerAdapter(this, mostRankingItems) { rankname ->

            val getRankPRoducts = getAllProducts?.rankings?.filter { rankname.equals(it.ranking) }
            val getCommonProducts = getAllProducts?.categories?.flatMap { it.products }
                ?.intersect(getRankPRoducts!!.flatMap { it.products })
            productListAdapter?.clearData()
            productListAdapter?.setProducts(getCommonProducts!!.toMutableList())

        }
        bannerLayout.setAdapter(rankingBannerAdapter, 3)
    }

    /**
     * set Categories horizontan list in Category Adapter
     * also get callbacks for rank selection and the result in product list
     */
    private fun setupCatogoriesAdapter() {
        val mLayoutManager = LinearLayoutManager(this, HORIZONTAL, false)
        recyclerCategory.layoutManager = mLayoutManager
        categoryAdapter = CategoryAdapter(categoriesItems) { catname ->
            productListAdapter?.clearData()
            productListAdapter?.setProducts(getAllProducts?.categories?.filter { res -> res.name == catname }?.flatMap { it.products } as MutableList<ProductX>)
        }
        recyclerCategory.disableItemAnimator()
        recyclerCategory.adapter = categoryAdapter

    }

    /**
     * set Products Vertical list in Products Adapter
     */
    private fun setupProductsAdapter() {
        val mLayoutManager = androidx.recyclerview.widget.GridLayoutManager(this, 2)
        rvProductList.layoutManager = mLayoutManager
        productListAdapter = ProductListAdapter(rvProductList, this, productListItems)
        rvProductList.addItemDecoration(
            androidx.recyclerview.widget.DividerItemDecoration(
                this,
                androidx.recyclerview.widget.DividerItemDecoration.HORIZONTAL
            )
        )
        rvProductList.addItemDecoration(
            androidx.recyclerview.widget.DividerItemDecoration(
                this,
                androidx.recyclerview.widget.DividerItemDecoration.VERTICAL
            )
        )
        rvProductList.adapter = productListAdapter

    }

    /**
     * get All products from API with Viewmodel observation and set it to respective adapters
     */
    private fun getProductsViewmodel() {
        val productsViewFactory = HomeViewModel().createFactory()
        val productsViewModel =
            ViewModelProviders.of(this, productsViewFactory).get(HomeViewModel::class.java)

        productsViewModel.callProductsAPI().observe(this, Observer {

            when (it.status) {
                ApiResponseStats.Status.COMPLETED -> {
                    when (it.data?.errCode) {
                        HTTP_OK -> {
                            getAllProducts = it.data?.data
                            val getRanking: MutableList<String> = mutableListOf()
                            for (ranks in getAllProducts!!.rankings) {
                                getRanking.add(ranks.ranking)
                            }
                            rankingBannerAdapter.setBannerData(getRanking)

                            //Categories
                            val getAllCategories: MutableList<String> = mutableListOf()
                            for (category in getAllProducts!!.categories) getAllCategories.add(
                                category.name
                            )

                            categoryAdapter?.setCategories(getAllCategories)


                            productListAdapter?.setProducts(getAllProducts?.categories?.flatMap { it.products } as MutableList<ProductX>)


                        }
                    }
                    swHomepage.isRefreshing = false
                }
                ApiResponseStats.Status.LOADING -> swHomepage.isRefreshing = true
                ApiResponseStats.Status.SUCCESS -> swHomepage.isRefreshing = false
                ApiResponseStats.Status.ERROR -> swHomepage.isRefreshing = false

            }


        })
    }
}
