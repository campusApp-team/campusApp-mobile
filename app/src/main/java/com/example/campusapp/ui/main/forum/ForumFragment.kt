package com.example.campusapp.ui.main.forum

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.example.campusapp.R
import com.example.campusapp.backend.DataRef
import com.example.campusapp.backend.DataRef.db
import com.example.campusapp.backend.DataRef.storage
import com.example.campusapp.tool.GlideApp
import com.google.firebase.firestore.DocumentSnapshot
import kotlinx.android.synthetic.main.forum_detail.*
import kotlin.math.roundToInt


class ForumFragment : Fragment() {
    private var mDoc: List<DocumentSnapshot> = listOf()
    private val TAG = "ForumFragment"

    private lateinit var recyclerView: RecyclerView
    private lateinit var viewAdapter: MessageAdapter
    private lateinit var viewManager: RecyclerView.LayoutManager

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.forum_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val safeArgs: ForumFragmentArgs by navArgs()
        val forumId = safeArgs.forumId
        chat_progress_bar.visibility = View.VISIBLE

        updateUI(view, forumId)

    }

    private fun updateUI(view:View ,forumId: String) {
        // retrieving header info
        var forumName = "Forum"
        db.document(DataRef.FORUMS_COLLECTION+"/"+forumId).get()
            .addOnSuccessListener { document ->
                if (document != null) {
//                    Log.d(TAG, "DocumentSnapshot data: ${document.data}")
                    forumName = document.getString("title")!!
                    viewAdapter.updateData(forumName)
                    recyclerView.adapter!!.notifyItemChanged(0)

                } else {
                    Log.d(TAG, "No such document")
                }
            }
            .addOnFailureListener { exception ->
                Log.d(TAG, "get() failed with ", exception)
            }
        viewManager = LinearLayoutManager(context)
        viewAdapter = MessageAdapter(mDoc, forumId, forumName)

        recyclerView = view.findViewById<RecyclerView>(R.id.chat_recycler).apply {
            layoutManager = viewManager
            adapter = viewAdapter
        }

        // retrieving messages
        db.collection(DataRef.FORUMS_COLLECTION + "/" + forumId + "/" + DataRef.FORUMS_MESSAGES)
            .addSnapshotListener { value, e ->
                chat_progress_bar.visibility = View.GONE
                if (e != null) {
                    Log.w(TAG, "Listen failed.", e)
                    return@addSnapshotListener
                }
                if (value != null && !value.isEmpty) {
                    mDoc = value.documents
                    viewAdapter.updateData(mDoc)
                    recyclerView.adapter!!.notifyDataSetChanged()
//                    for (doc in mDoc) { Log.v(TAG, "${doc.id} -> ${doc.get("text")}") }
                }
            }
    }
}
