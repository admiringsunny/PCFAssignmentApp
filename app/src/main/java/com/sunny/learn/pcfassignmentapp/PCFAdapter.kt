package com.sunny.learn.pcfassignmentapp

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class PCFAdapter(val context: Context, val results: List<Result>) :
    RecyclerView.Adapter<PCFAdapter.PCFViewHolder>(), FragComm  {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PCFViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_layout, parent, false)
        return PCFViewHolder(view)
    }

    override fun getItemCount(): Int {
        return results.size
    }

    override fun onBindViewHolder(holder: PCFViewHolder, position: Int) {
        holder.bind(results[position])
        holder.itemView.setOnClickListener {
            gotoDetailsFragment(context, position)
        }
    }

    inner class PCFViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val iv_avatar: ImageView = itemView.findViewById(R.id.iv_avatar)
        private val tv_id: TextView = itemView.findViewById(R.id.tv_id)
        private val tv_name: TextView = itemView.findViewById(R.id.tv_name)
        private val tv_full_name: TextView = itemView.findViewById(R.id.tv_full_name)

        fun bind(result: Result) {
            Glide.with(itemView.context).load(result.owner.avatar_url).into(iv_avatar)
            tv_id.text = "ID: " + result.id
            tv_name.text = "Name: " + result.name
            tv_full_name.text = "Full Name: " + result.full_name
        }

    }

    override fun gotoDetailsFragment(context: Context, position: Int) {
        val detailsFragment = DetailsFragment.newInstance(results[position])
        (context as MainActivity).supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_list, detailsFragment)
            .addToBackStack(detailsFragment.toString())
            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
            .commit()
    }
}
interface FragComm{
    fun gotoDetailsFragment(context: Context, position: Int)
}
