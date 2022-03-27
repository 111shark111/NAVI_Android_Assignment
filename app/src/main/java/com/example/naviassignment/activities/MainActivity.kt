package com.example.naviassignment.activities

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AbsListView
import android.widget.ProgressBar
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.naviassignment.mainRecyclerView.MyAdapter
import com.example.naviassignment.models.APIResponse
import com.example.naviassignment.R
import com.example.naviassignment.repository.MainRepository
import com.example.naviassignment.retrofit.APIService
import com.example.naviassignment.viewModels.MainViewModel
import com.example.naviassignment.viewModels.MainViewModelFactory
import com.example.naviassignment.utils.Constants

class MainActivity : AppCompatActivity() {
    private lateinit var mainViewModel : ViewModel
    private lateinit var mainRecyclerView : RecyclerView
    private lateinit var progressBar : ProgressBar
    private lateinit var mainAdapter: MyAdapter
    private  var mainList : APIResponse = APIResponse()
    private var isScrolling : Boolean = false
    private var currentItems : Int = 0
    private var totalItems : Int = 0
    private var scrolledOutItems : Int = 0
    private var currentPage : Int = 1
    private var isMoreData : Boolean = true
    private lateinit var repository: MainRepository
    private lateinit var apiService: APIService


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initialize()
        setUpObservers()
        handleLazyLoading()
    }


    private fun initialize () {
        setContentView(R.layout.activity_main)
        val repoName = intent.getStringExtra("repoName")
        val userName = intent.getStringExtra("userName")
        apiService = Constants.getInstance().create(APIService::class.java)
        repository = MainRepository(apiService)
        progressBar = findViewById(R.id.progress_bar)
        progressBar.visibility =View.VISIBLE
        mainRecyclerView = findViewById(R.id.mainRecyclerView)
        mainRecyclerView.layoutManager = LinearLayoutManager(this)
        mainViewModel = ViewModelProvider(this,MainViewModelFactory(repository,repoName.toString(),userName.toString()))[MainViewModel::class.java]
    }


    private fun handleLazyLoading() {
        mainRecyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener(){
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if(newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL){
                    isScrolling = true
                }
            }

            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                currentItems = (mainRecyclerView.layoutManager as LinearLayoutManager).childCount
                totalItems = (mainRecyclerView.layoutManager as LinearLayoutManager).itemCount
                scrolledOutItems = (mainRecyclerView.layoutManager as LinearLayoutManager).findFirstVisibleItemPosition()
                if(isMoreData && isScrolling && scrolledOutItems + currentItems == totalItems){
                    currentPage++
                    (mainViewModel as MainViewModel).fetchData(currentPage)
                }
                isScrolling = false
            }
        })
    }
    private fun setUpObservers () {
        (mainViewModel as MainViewModel).requests.observe(this,Observer{
            Log.i("checkData","${it}")
            mainList.addAll(it)
            if(::mainAdapter.isInitialized){
                mainAdapter.notifyDataSetChanged()
            }
            else{
                mainAdapter = MyAdapter(this,mainList)
                mainRecyclerView.adapter = mainAdapter
            }
            progressBar.visibility = View.GONE
            if(it.size < 10){
                isMoreData = false
            }
        })
    }
}


