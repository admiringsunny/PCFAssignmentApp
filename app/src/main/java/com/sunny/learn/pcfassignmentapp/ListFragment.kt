package com.sunny.learn.pcfassignmentapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_list.*
import retrofit2.Call
import retrofit2.Response
import retrofit2.Callback

class ListFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_list, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        if (!Connectivity().verifyAvailableNetwork(context as AppCompatActivity)) {
            Toast.makeText(context, "Internet not available\nWe are watching Offline Data", Toast.LENGTH_SHORT).show()
            loadResults()
            return
        }


        val request = ServiceBuilder.buildService(PCFEndpoints::class.java)
        val call = request.getAllDataList()
        call.enqueue(object : Callback<List<Result>> {
            override fun onFailure(call: Call<List<Result>>, t: Throwable) {
                progress_bar.visibility = View.GONE
                Toast.makeText(context, "${t.message}", Toast.LENGTH_LONG).show()
            }

            override fun onResponse(call: Call<List<Result>>, response: Response<List<Result>>) {

                if (response.isSuccessful) {
                    progress_bar.visibility = View.GONE
                    recyclerView.apply {
                        setHasFixedSize(true)
                        layoutManager = LinearLayoutManager(context)
                        val resultsList = response.body() as List<Result>
                        adapter = PCFAdapter(context, resultsList!!)

                        saveResults(resultsList)
                    }

                } else {
                    progress_bar.visibility = View.GONE
                    Toast.makeText(context, "No Response!!", Toast.LENGTH_LONG).show()
                }
            }
        })
    }


    fun saveResults(resultsList: List<Result>) {
        val db: PCFDataBase = PCFDataBase(context)

        for (result in resultsList) {
            db.addRockStar(result)
        }
    }

    fun loadResults() {
        progress_bar.visibility = View.GONE
        val db: PCFDataBase = PCFDataBase(context)
        val allRockStars: List<Result> = db.getAllRockStars()
        if (allRockStars == null || allRockStars.size < 1) {
            Toast.makeText(context, "Internet not available\nOffline Data not available", Toast.LENGTH_SHORT).show()
            return
        }

        recyclerView.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(context)
            adapter = PCFAdapter(context, allRockStars)
        }

    }
}
