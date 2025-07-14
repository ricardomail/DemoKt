package com.oasis.app_me.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.oasis.app_common.util.Constants
import com.oasis.app_me.R
import com.oasis.app_me.bean.MyCollectDetail
import com.oasis.app_me.listener.CollectItemClickListener

private const val NORMAL: Int = 0
private const val FOOT: Int = 1
private const val LAST: Int = 2

class CollectRvAdapter(private val listener: CollectItemClickListener) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>(),
    View.OnClickListener {
    private var diff: AsyncListDiffer<MyCollectDetail>
    var isLastPage = false

    init {
        diff = AsyncListDiffer(this, MyCallback())
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            NORMAL -> {
                MyViewHolder(
                    LayoutInflater.from(parent.context)
                        .inflate(R.layout.item_collect_rv, parent, false)
                )
            }

            FOOT -> {
                MyFootHolder(
                    LayoutInflater.from(parent.context)
                        .inflate(com.oasis.app_common.R.layout.foot_rv, parent, false)
                )
            }

            else -> {
                MyLastHolder(
                    LayoutInflater.from(parent.context)
                        .inflate(com.oasis.app_common.R.layout.last_rv, parent, false)
                )
            }
        }
    }

    override fun getItemCount(): Int {
        return if (diff.currentList.size == 0) 1 else diff.currentList.size + 1
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (getItemViewType(position) == NORMAL) {
            val data = diff.currentList[position]
            (holder as MyViewHolder).apply {
                title.text = data.title
                time.text = data.niceDate
                holder.name.text = data.author.ifEmpty { data.chapterName }
                itemView.tag = position
                itemView.setOnClickListener(this@CollectRvAdapter)
            }


        }
    }

    private var lastClickTime: Long = 0
    override fun onClick(v: View?) {
        val currentTime = System.currentTimeMillis()
        if (currentTime - lastClickTime > Constants.MIN_CLICK_DELAY_TIME && v != null) {
            lastClickTime = currentTime
            listener.onClick(v.tag as Int)

        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (position == itemCount - 1) {
            if (isLastPage) LAST else FOOT
        } else {
            NORMAL
        }
    }

    inner class MyViewHolder(item: View) : RecyclerView.ViewHolder(item) {
        var title: TextView = item.findViewById(R.id.title)
        var name: TextView = item.findViewById(R.id.name)
        var time: TextView = item.findViewById(R.id.time)
    }

    fun setData(list: List<MyCollectDetail>?) {
        diff.submitList(if (list != null) ArrayList(list) else null)
    }

    class MyFootHolder(item: View) : RecyclerView.ViewHolder(item)
    class MyLastHolder(item: View) : RecyclerView.ViewHolder(item)

    inner class MyCallback : DiffUtil.ItemCallback<MyCollectDetail>() {
        override fun areItemsTheSame(oldItem: MyCollectDetail, newItem: MyCollectDetail): Boolean =
            oldItem.id == newItem.id

        override fun areContentsTheSame(
            oldItem: MyCollectDetail,
            newItem: MyCollectDetail
        ): Boolean = oldItem.title == newItem.title && oldItem.niceDate == newItem.niceDate

    }
}