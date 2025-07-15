package com.oasis.app_project.adapter

import android.text.Layout
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.makeramen.roundedimageview.RoundedImageView
import com.oasis.app_common.util.Constants
import com.oasis.app_project.R
import com.oasis.app_project.bean.ProjectDetail
import com.oasis.app_project.listener.ProItemClickListener

private const val NORMAL: Int = 0
private const val FOOT: Int = 1
private const val LAST: Int = 2

class ProRVAdapter(private val listener: ProItemClickListener) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>(), View.OnClickListener {
    private var lastClickTime: Long = 0
    private var diff: AsyncListDiffer<ProjectDetail>
    var isLastPage = false

    init {
        diff = AsyncListDiffer(this, MyCallback())
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            NORMAL -> {
                MyViewHolder(
                    LayoutInflater.from(parent.context).inflate(R.layout.item_pro_rv, parent, false)
                )
            }

            FOOT -> {
                MyFootHolder(
                    LayoutInflater.from(parent.context)
                        .inflate(com.oasis.app_common.R.layout.foot_rv, parent, false)
                )
            }

            LAST -> {
                MyLastHolder(
                    LayoutInflater.from(parent.context)
                        .inflate(com.oasis.app_common.R.layout.last_rv, parent, false)
                )
            }

            else -> {
                throw IllegalArgumentException("No this item")
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
                rimg.load(data.envelopePic)
                desc.text = data.desc
                title.text = data.title
                name.text = data.author
                time.text = data.niceDate
                itemView.tag = position
                itemView.setOnClickListener(this@ProRVAdapter)
                collect.isSelected = data.collect
                itemView.tag = position
                itemView.setOnClickListener(this@ProRVAdapter)
                collect.tag = position
                collect.setOnClickListener(this@ProRVAdapter)
            }
        }
    }

    override fun onClick(v: View?) {
        val currentTime = System.currentTimeMillis()
        if (currentTime - lastClickTime > Constants.MIN_CLICK_DELAY_TIME && v != null) {
            lastClickTime = currentTime

            if (v.id == R.id.img_collect) {
                listener.onCollectClick(v.tag as Int)
            } else {
                listener.onItemClick(v.tag as Int)
            }

        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (position == itemCount - 1) {
            if (isLastPage) {
                LAST
            } else {
                FOOT
            }
        } else {
            NORMAL
        }
    }

    fun setData(list: List<ProjectDetail>?) {
        //AsyncListDiffer需要一个新数据，不然添加无效
        diff.submitList(if (list != null) ArrayList(list) else null)
    }

    class MyViewHolder(item: View) : RecyclerView.ViewHolder(item) {
        var title: TextView = item.findViewById(R.id.title)
        var desc: TextView = item.findViewById(R.id.desc)
        var name: TextView = item.findViewById(R.id.name)
        var time: TextView = item.findViewById(R.id.time)
        var rimg: RoundedImageView = item.findViewById(R.id.rimg)
        var collect: ImageView = item.findViewById(R.id.img_collect)
    }

    class MyFootHolder(item: View) : RecyclerView.ViewHolder(item)

    class MyLastHolder(item: View) : RecyclerView.ViewHolder(item)

    class MyCallback : DiffUtil.ItemCallback<ProjectDetail>() {
        override fun areItemsTheSame(oldItem: ProjectDetail, newItem: ProjectDetail): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: ProjectDetail, newItem: ProjectDetail): Boolean {
            return oldItem.title == newItem.title
        }

    }
}