package com.example.naviassignment.mainRecyclerView

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.naviassignment.activities.MainActivity
import com.example.naviassignment.models.APIResponse
import com.example.naviassignment.R

const val VIEW_TYPE_LOADING = 0
const val VIEW_TYPE_LISTITEM = 1
class MyAdapter(private val activity: MainActivity, private val dataList : APIResponse) : RecyclerView.Adapter<RecyclerView.ViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        when (viewType){
            VIEW_TYPE_LISTITEM -> {
                val view = inflater.inflate(R.layout.single_item,parent,false)
                return MyListViewHolder(view)
            }
            VIEW_TYPE_LOADING -> {
                val view = inflater.inflate(R.layout.loading_view,parent,false)
                return MyLoadingViewHolder(view)
            }
        }
        val view = inflater.inflate(R.layout.loading_view,parent,false)
        return MyLoadingViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if(holder is MyLoadingViewHolder){
            if(position < dataList.size ){
                holder.progressBar.visibility = View.GONE
            }
            else holder.progressBar.visibility = View.VISIBLE

        }
        else if(holder is MyListViewHolder){
            holder.userName.text = dataList[position].user.login
            holder.title.text = dataList[position].title
            val stringCreated = "Created At : "+ dataList[position].created_at
            val stringClosed = "Closed At : "+ dataList[position].closed_at
            holder.createdAt.text = stringCreated
            holder.closedAt.text = stringClosed
            Glide.with(activity).load(dataList[position].user.avatar_url).circleCrop()
                .into(holder.userImage);
        }
    }


    override fun getItemCount(): Int {
       return dataList.size + 1;
    }

    override fun getItemViewType(position: Int): Int {
        return if(position < dataList.size){
            VIEW_TYPE_LISTITEM
        } else VIEW_TYPE_LOADING
    }

    class MyListViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){
        var userImage = itemView.findViewById<ImageView>(R.id.user_image)
        var userName = itemView.findViewById<TextView>(R.id.user_name)
        var title = itemView.findViewById<TextView>(R.id.title)
        var createdAt = itemView.findViewById<TextView>(R.id.created_at)
        var closedAt = itemView.findViewById<TextView>(R.id.closed_at)
    }

    class MyLoadingViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        var progressBar = itemView.findViewById<ProgressBar>(R.id.progress_bar)
    }


}


