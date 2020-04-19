package com.example.campusapp.ui.main.forum


import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.campusapp.R
import com.example.campusapp.backend.DataRef
import com.example.campusapp.backend.DataRef.db
import com.example.campusapp.ui.main.forum.ForumListFragment.OnForumFragmentInteractionListener
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.forum_list_item.view.*

class ForumsRecyclerViewAdapter(
    private val mListener: OnForumFragmentInteractionListener?
) : RecyclerView.Adapter<ForumsRecyclerViewAdapter.ViewHolder>() {

    private val mOnClickListener: View.OnClickListener
    private var mDoc: List<DocumentSnapshot> = listOf()
    //    private var mColors:IntArray = IntArray(0)
    val TAG = "ForumsRecycler"

    init {

        mOnClickListener = View.OnClickListener { v ->
            val item = v.tag as String
            // Notify the active callbacks interface (the activity) that an item has been selected.
            mListener?.onForumFragmentInteraction(item, ViewHolder(v).mTitle.text.toString())
        }
        db.collection(DataRef.FORUMS_COLLECTION)
            .addSnapshotListener { value, e ->
                if (e != null) {
                    Log.w(TAG, "Listen failed.", e)
                    return@addSnapshotListener
                }
                if(value != null){
                    mDoc = value.documents
                    this.notifyDataSetChanged()
//                    for (doc in value) {Log.v(TAG,"${doc.id} -> ${doc.get("title")}")}
                }
            }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.forum_list_item, parent, false)
        return ViewHolder(view)
    }

    //    each forum items are populated in onBindViewHolder method
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = mDoc[position%4]
        holder.mId = item.id
        holder.mTitle.text = item.getString("title")
        holder.mDescription.text = item.getString("description")
        val animation = AnimationUtils.loadAnimation(holder.itemView.context, R.anim.rise_up)
        holder.itemView.startAnimation(animation)
        val params = holder.itemView.layoutParams as StaggeredGridLayoutManager.LayoutParams
        params.bottomMargin = when(position){
            itemCount-1 -> 300
            else -> 12
        }
        holder.itemView.layoutParams = params
        with(holder.mView) {
            tag = item.id
            setOnClickListener(mOnClickListener)
        }
//        val col = mColors[position%mColors.size]
//        val col = mColors[(Math.random()*mColors.size).toInt()]
//        holder.mBar.setBackgroundColor(col)
//        Log.v(TAG,"onBind $position ${holder.mTitle.text}")
    }
    // TODO11 : remove dupication of data
    override fun getItemCount(): Int = mDoc.size*4

    inner class ViewHolder(val mView: View) : RecyclerView.ViewHolder(mView) {
        var mId:String = "null"
        val mTitle: TextView = mView.forum_title_list_item
        val mDescription: TextView = mView.forum_description_list_item
//        val mBar:ImageView = mView.forum_bar_list_item

        override fun toString(): String {
            return super.toString() + " '" + mDescription.text + "'"
        }
    }
}
