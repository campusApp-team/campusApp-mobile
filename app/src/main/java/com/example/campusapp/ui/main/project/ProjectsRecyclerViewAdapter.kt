package com.example.campusapp.ui.main.project


import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.campusapp.R
import com.example.campusapp.backend.Firestore
import com.example.campusapp.ui.main.project.ProjectListFragment.OnProjectFragmentInteractionListener
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.project_list_item.view.*

/**
 * [RecyclerView.Adapter] that can display a project and makes a call to the
 * specified [OnProjectFragmentInteractionListener].
 * TOD Replace the implementation with code for your data type.
 */
class ProjectsRecyclerViewAdapter(
    private val mListener: OnProjectFragmentInteractionListener?
) : RecyclerView.Adapter<ProjectsRecyclerViewAdapter.ViewHolder>() {
    private val mOnClickListener: View.OnClickListener
    private val db: FirebaseFirestore = FirebaseFirestore.getInstance()
    private var mDoc: List<DocumentSnapshot> = listOf()

    val TAG = "ProjectsRecycler"

    init {
        // Retrieve data (list of active projects) from firestore database
        db.collection(Firestore.PROJECTS_COLLECTION)
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

        // Notify the active callbacks interface (the activity) that an item has been selected.
        mOnClickListener = View.OnClickListener { v ->
            val item = v.tag as String
            mListener?.onProjectFragmentInteraction(item)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.project_list_item, parent, false)
        return ViewHolder(view)
    }

    //    each project views are populated in onBindViewHolder method
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = mDoc[position%mDoc.size]
        holder.mId = item.id
        holder.mTitle.text = item.getString("title")
        holder.mDescription.text = item.getString("description")
        val animation = AnimationUtils.loadAnimation(holder.itemView.context, R.anim.rise_up)
        holder.itemView.startAnimation(animation)

//        Log.d(TAG,"onBind $position ${holder.mTitle.text}")
        val params = holder.itemView.layoutParams as RecyclerView.LayoutParams
        params.bottomMargin = when(position){
            itemCount-1 -> 300
            else -> 5
        }
        holder.itemView.layoutParams = params

        with(holder.mView) {
            tag = item.id
            setOnClickListener(mOnClickListener)
        }
    }

    //    TODO 12 : remove duplication of data
    override fun getItemCount() : Int = mDoc.size*3

    inner class ViewHolder(val mView: View) : RecyclerView.ViewHolder(mView) {
        var mId:String = "null"
        val mTitle: TextView = mView.project_title
        val mDescription: TextView = mView.project_description

        override fun toString(): String {
            return super.toString() + " '" + mDescription.text + "'"
        }
    }
}
