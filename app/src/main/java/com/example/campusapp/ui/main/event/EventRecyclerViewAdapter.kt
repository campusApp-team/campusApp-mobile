package com.example.campusapp.ui.main.event


import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.campusapp.R
import com.example.campusapp.backend.Firestore
import com.example.campusapp.ui.main.event.EventListFragment.OnEventFragmentInteractionListener
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.event_list_item.view.*

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
                    this.notifyDataSetChanged()
//                    for (doc in value) {Log.v(TAG,"${doc.id} -> ${doc.get("title")}")}
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
        val animation = AnimationUtils.loadAnimation(holder.itemView.context, R.anim.rise_up)
        holder.itemView.startAnimation(animation)
        val params = holder.itemView.layoutParams as RecyclerView.LayoutParams
        params.bottomMargin = when(position){
            itemCount-1 -> 300
            else -> 13
        }
        holder.itemView.layoutParams = params

        with(holder.mView) {
            tag = item.id
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
