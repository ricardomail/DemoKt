package com.oasis.app_navigation.adapter

import android.text.Layout
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.google.android.flexbox.FlexboxLayout
import com.oasis.app_common.util.Constants
import com.oasis.app_navigation.R
import com.oasis.app_navigation.bean.Navi
import com.oasis.app_navigation.bean.NaviItemEvent
import com.oasis.app_navigation.listener.NaviItemClickListener

class NaviRVAdapter(private val listener: NaviItemClickListener) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>(), View.OnClickListener {

    private val diff: AsyncListDiffer<Navi>

    init {
        diff = AsyncListDiffer(this, MyCallBack())
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return MyViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_sys_rv, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return diff.currentList.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val data = diff.currentList[position]
        (holder as MyViewHolder).name.text = data.name
        holder.flex.removeAllViews()
        data.articles.forEachIndexed { index, value ->
            val layout = LayoutInflater.from(holder.itemView.context)
                .inflate(R.layout.item_flex, null, false)
            val t = layout.findViewById<TextView>(R.id.tx_flex)
            t.text = value.title
            layout.tag = NaviItemEvent(position, index)
            layout.setOnClickListener(this)
            holder.flex.addView(layout)
        }
    }

    fun setData(list: List<Navi>?) {
        diff.submitList(if (list != null) ArrayList(list) else null)
    }

    inner class MyViewHolder(item: View) : RecyclerView.ViewHolder(item) {
        var name: TextView = item.findViewById(R.id.tx_sys_rv)
        var flex: FlexboxLayout = item.findViewById(R.id.flex_sys)
    }

    private var lastClickTime: Long = 0
    override fun onClick(v: View?) {
        val currentTime = System.currentTimeMillis()
        if (currentTime - lastClickTime > Constants.MIN_CLICK_DELAY_TIME && v != null) {
            lastClickTime = currentTime
            listener.onClick(v.tag as NaviItemEvent)
        }
    }

    inner class MyCallBack : DiffUtil.ItemCallback<Navi>() {
        override fun areItemsTheSame(oldItem: Navi, newItem: Navi): Boolean {
            return oldItem.cid == newItem.cid
        }

        override fun areContentsTheSame(oldItem: Navi, newItem: Navi): Boolean {
            return oldItem.name == newItem.name
        }

    }
}


