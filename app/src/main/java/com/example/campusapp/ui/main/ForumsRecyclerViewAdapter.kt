package com.example.campusapp.ui.main

import android.util.Log
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.example.campusapp.R
import com.example.campusapp.backend.Firestore


import com.example.campusapp.ui.main.ForumFragment.OnListFragmentInteractionListener
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore

import kotlinx.android.synthetic.main.forum_list_item.view.*
import kotlin.random.Random

/**
 * [RecyclerView.Adapter] that can display a [DummyItem] and makes a call to the
 * specified [OnListFragmentInteractionListener].
 */
class ForumsRecyclerViewAdapter(
    private val mListener: OnListFragmentInteractionListener?
) : RecyclerView.Adapter<ForumsRecyclerViewAdapter.ViewHolder>() {

    private val mOnClickListener: View.OnClickListener
    private val db: FirebaseFirestore = FirebaseFirestore.getInstance()
    private var mDoc: List<DocumentSnapshot> = listOf()
    private var mColors:IntArray = IntArray(0)
    val TAG = "ForumsRecycler"

    init {

        mOnClickListener = View.OnClickListener { v ->
            val item = v.tag as String
            // Notify the active callbacks interface (the activity) that an item has been selected.
            mListener?.onForumFragmentInteraction(item)
        }
        db.collection(Firestore.FORUMS_COLLECTION)
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
            .inflate(R.layout.forum_list_item, parent, false)
        mColors = view.context.resources.getIntArray(R.array.msg_colors)
        return ViewHolder(view)
    }

    //    each forum items are populated in onBindViewHolder method
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = mDoc[position%4]
        holder.mId = item.id
        holder.mTitle.text = item.getString("title")
        holder.mDescription.text = item.getString("description")
//        val col = mColors[position%mColors.size]
        val col = mColors[(Math.random()*mColors.size).toInt()]
        holder.mBar.setBackgroundColor(col)
//        Log.v(TAG,"onBind $position ${holder.mTitle.text}")

        with(holder.mView) {
            tag = item.id
            setOnClickListener(mOnClickListener)
        }
    }
    // TODO 11 : remove dupication of data
    override fun getItemCount(): Int = mDoc.size*4

    inner class ViewHolder(val mView: View) : RecyclerView.ViewHolder(mView) {
        var mId:String = "null"
        val mTitle: TextView = mView.forum_title_list_item
        val mDescription: TextView = mView.forum_description_list_item
        val mBar:FrameLayout = mView.forum_bar_list_item

        override fun toString(): String {
            return super.toString() + " '" + mDescription.text + "'"
        }
    }
}
