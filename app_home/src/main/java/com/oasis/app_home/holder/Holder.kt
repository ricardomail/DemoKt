package com.oasis.app_home.holder

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.oasis.app_home.R

class MyViewHolder(item: View) : RecyclerView.ViewHolder(item) {
    var title: TextView = item.findViewById(R.id.tv_title)
    var name: TextView = item.findViewById(R.id.tv_name)
    var time: TextView = item.findViewById(R.id.tv_time)
    var type: TextView = item.findViewById(R.id.tv_type)
    var tag1: TextView = item.findViewById(R.id.tv_tag_1)
    var tag2: TextView = item.findViewById(R.id.tv_tag_2)
    var collect: ImageView = item.findViewById(R.id.iv_collect)
}

class MyFootHolder(item: View) : RecyclerView.ViewHolder(item)
class MyLastHolder(item: View) : RecyclerView.ViewHolder(item)