package com.sunny.learn.pcfassignmentapp

import android.content.Context
import android.content.Intent
import android.content.Intent.*
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.fragment_details.*

class DetailsFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        return inflater.inflate(R.layout.fragment_details, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val result = arguments?.get("result") as Result

        progress_bar.visibility = View.GONE

        tv_name.text = "Name: ${result.name}"
        Glide.with(activity as Context).load(result.owner.avatar_url).into(iv_avatar)

        tv_details.text ="""
            Details:
            
            Full Name: ${result.full_name}
            
            Description: ${result.description}
        """.trimIndent()

        tv_url.text = "${result.owner.html_url}"
        tv_url.setOnClickListener(View.OnClickListener {
            val intent = Intent(ACTION_VIEW)
            intent.setData(Uri.parse("${result.owner.html_url}"))
//            intent.flags = FLAG_ACTIVITY_NEW_TASK
            startActivity(intent)
        })
    }

    companion object {
        fun newInstance(result: Result): DetailsFragment {

            val fragment = DetailsFragment()

            val args = Bundle()
            args.putSerializable("result", result)
            fragment.arguments = args

            return fragment
        }
    }
}
