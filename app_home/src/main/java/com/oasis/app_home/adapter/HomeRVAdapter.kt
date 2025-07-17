package com.oasis.app_home.adapter

import android.graphics.Rect
import android.view.LayoutInflater
import android.view.TouchDelegate
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.oasis.app_common.util.Constants
import com.oasis.app_common.util.Extension.dp2px
import com.oasis.app_home.R
import com.oasis.app_home.bean.ArticleDetail
import com.oasis.app_home.holder.MyFootHolder
import com.oasis.app_home.holder.MyLastHolder
import com.oasis.app_home.holder.MyViewHolder
import com.oasis.app_home.listener.HomeItemClickListener

private const val NORMAL = 0
private const val FOOT = 1
private const val LAST = 2

class HomeRVAdapter(private var listener: HomeItemClickListener) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>(),
    View.OnClickListener {
    private var diff: AsyncListDiffer<ArticleDetail>
    var isLastPage = false

    init {
        diff = AsyncListDiffer(this, MyCallback())
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            NORMAL -> {
                MyViewHolder(
                    LayoutInflater.from(parent.context)
                        .inflate(R.layout.item_home_rv, parent, false)
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
            (holder as MyViewHolder).title.text = data.title
            holder.time.text = data.niceDate
            holder.type.text = data.superChapterName
            holder.tag1.visibility = if (data.fresh) View.VISIBLE else View.GONE
            holder.tag2.visibility = if (data.superChapterId == 408) View.VISIBLE else View.GONE
            holder.name.text = data.author.ifEmpty { data.shareUser }
            refreshPosition(holder.tag1, holder.tag2, holder.name)
            holder.collect.isSelected = data.collect
            holder.itemView.tag = position
            holder.itemView.setOnClickListener(this)
            holder.collect.tag = position
            // 扩大收藏的点击范围
            holder.itemView.post {
                val hitRect = Rect()
                holder.collect.getHitRect(hitRect)
                hitRect.left -= 20f.dp2px()
                hitRect.top -= 20f.dp2px()
                hitRect.right += 20f.dp2px()
                hitRect.bottom += 20f.dp2px()
                holder.itemView.touchDelegate = TouchDelegate(hitRect, holder.collect)
            }
            holder.collect.setOnClickListener(this)
        }
    }

    // TODO: 后期需要修改为constraintLayout 
    private fun refreshPosition(tag1: TextView, tag2: TextView, name: TextView) {
        if (tag1.visibility == View.VISIBLE) return
        if (tag1.visibility == View.GONE && tag2.visibility == View.GONE) {
            resetMargin(name, 0)
        }

        if (tag1.visibility == View.GONE && tag2.visibility == View.VISIBLE) {
            resetMargin(tag2, 0)
            resetMargin(name, 10f.dp2px())
        }
    }

    private fun resetMargin(view: TextView, margin: Int) {
        val layoutParams: LinearLayout.LayoutParams = view.layoutParams as LinearLayout.LayoutParams
        layoutParams.marginStart = margin
        view.layoutParams = layoutParams
    }

    private var lastClickTime: Long = 0
    override fun onClick(v: View?) {
        val currentTime = System.currentTimeMillis()
        if (currentTime - lastClickTime > Constants.MIN_CLICK_DELAY_TIME && v != null) {
            lastClickTime = currentTime

            if (v.id == R.id.iv_collect) {
                listener.onCollectClick(v.tag as Int)
            } else {
                listener.onItemClick(v.tag as Int)
            }

        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (position == itemCount - 1) {
            if (isLastPage) LAST else FOOT
        } else {
            NORMAL
        }
    }

    fun setData(data: List<ArticleDetail>?) {
        diff.submitList(if (data != null) ArrayList(data) else null)
    }


    inner class MyCallback : DiffUtil.ItemCallback<ArticleDetail>() {
        override fun areItemsTheSame(oldItem: ArticleDetail, newItem: ArticleDetail): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: ArticleDetail, newItem: ArticleDetail): Boolean {
            return oldItem.title == newItem.title && oldItem.niceDate == newItem.niceDate
        }

    }
}