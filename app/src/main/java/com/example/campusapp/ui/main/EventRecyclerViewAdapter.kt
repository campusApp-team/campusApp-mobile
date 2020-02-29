package com.example.campusapp.ui.main

import android.util.Log
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.campusapp.R
import com.example.campusapp.backend.Firestore


import com.example.campusapp.ui.main.EventFragment.OnEventFragmentInteractionListener
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore

import kotlinx.android.synthetic.main.event_list_item.view.*

/**
 * [RecyclerView.Adapter] that can display a [DummyItem] and makes a call to the
 * specified [OnEventFragmentInteractionListener].
 * TOD: Replace the implementation with code for your data type.
 */
class EventRecyclerViewAdapter(
    private val mListener: OnEventFragmentInteractionListener?
) : RecyclerView.Adapter<EventRecyclerViewAdapter.ViewHolder>() {

    private val mOnClickListener: View.OnClickListener
    private val db: FirebaseFirestore = FirebaseFirestore.getInstance()
    private var mDoc: List<DocumentSnapshot> = listOf()
    val TAG = "EventsRecycler"

    init {
        mOnClickListener = View.OnClickListener { v ->
            val item = v.tag as String
            // Notify the active callbacks interface (the activity) that an item has been selected.
            mListener?.onEventFragmentInteraction(item)
        }
        db.collection(Firestore.EVENTS_COLLECTION)
            .addSnapshotListener { value, e ->
                if (e != null) {
                    Log.w(TAG, "Listen failed.", e)
                    return@addSnapshotListener
                }
                if(value != null){
                    mDoc = value.documents
                    for (doc in value) {
                        Log.d(TAG,"${doc.id} -> ${doc.get("title")}")
                    }
                    this.notifyDataSetChanged()
                }
            }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.event_list_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = mDoc[position%mDoc.size]
        holder.mId = item.id
        holder.mTitle.text = item.getString("title")
        holder.mDescription.text = item.getString("description")

        with(holder.mView) {
            tag = item
            setOnClickListener(mOnClickListener)
        }
    }

    // TODO 10 : remove dupication of data
    override fun getItemCount(): Int = mDoc.size * 3

    inner class ViewHolder(val mView: View) : RecyclerView.ViewHolder(mView) {
        var mId:String = "null"
        val mTitle: TextView = mView.event_title_list_item
        val mDescription: TextView = mView.event_description_list_item

        override fun toString(): String {
            return super.toString() + " '" + mDescription.text + "'"
        }
    }
}
