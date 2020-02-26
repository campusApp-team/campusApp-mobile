package com.example.campusapp.ui.main

import android.content.Context
import android.util.Log
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import com.example.campusapp.R
import com.example.campusapp.backend.Firestore


import com.example.campusapp.ui.main.ProjectFragment.OnListFragmentInteractionListener
import com.google.firebase.firestore.DocumentSnapshot

import kotlinx.android.synthetic.main.fragment_project.view.*

/**
 * [RecyclerView.Adapter] that can display a project and makes a call to the
 * specified [OnListFragmentInteractionListener].
 * TOD Replace the implementation with code for your data type.
 */
class ProjectsRecyclerViewAdapter(
    private val mListener: OnListFragmentInteractionListener?
) : RecyclerView.Adapter<ProjectsRecyclerViewAdapter.ViewHolder>() {
    private var mDoc: List<DocumentSnapshot> = listOf()
    private val mOnClickListener: View.OnClickListener
    var i = 0

    init {
        i+=1
        mDoc = Firestore.getProjects(this)
        mOnClickListener = View.OnClickListener { v ->
            val item = v.tag as String
            // Notify the active callbacks interface (the activity, if the fragment is attached to
            // one) that an item has been selected.
            mListener?.onListFragmentInteraction(item)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.fragment_project, parent, false)
        return ViewHolder(view)
    }

    //    each project views are populated in onBindViewHolder method
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = mDoc[position]
        holder.mId = item.id
        holder.mTitle.text = item.getString("title")
        holder.mDescription.text = item.getString("description")
        Log.d("ProjRecAdapter","onBind $position ${holder.mTitle.text}")

        with(holder.mView) {
            tag = item.id
            setOnClickListener(mOnClickListener)
        }
    }

    override fun getItemCount() : Int = mDoc.size

    inner class ViewHolder(val mView: View) : RecyclerView.ViewHolder(mView) {
        var mId:String = "null"
        val mTitle: TextView = mView.project_title
        val mDescription: TextView = mView.project_description

        override fun toString(): String {
            return super.toString() + " '" + mDescription.text + "'"
        }
    }
}
