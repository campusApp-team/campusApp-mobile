package com.example.campusapp.ui.main


import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.campusapp.R
import com.example.campusapp.backend.Firestore
import com.example.campusapp.ui.main.ProjectFragment.OnListFragmentInteractionListener
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.project_list_item.view.*

/**
 * [RecyclerView.Adapter] that can display a project and makes a call to the
 * specified [OnListFragmentInteractionListener].
 * TOD Replace the implementation with code for your data type.
 */
class ProjectsRecyclerViewAdapter(
    private val mListener: OnListFragmentInteractionListener?
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
//                    for (doc in value) {
//                        Log.v(TAG,"${doc.id} -> ${doc.get("title")}")
//                    }
                    this.notifyDataSetChanged()
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
        val item = mDoc[position]
        holder.mId = item.id
        holder.mTitle.text = item.getString("title")
        holder.mDescription.text = item.getString("description")
        Log.d(TAG,"onBind $position ${holder.mTitle.text}")

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
