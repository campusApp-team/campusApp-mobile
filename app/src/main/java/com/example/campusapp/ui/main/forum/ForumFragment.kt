package com.example.campusapp.ui.main.forum

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import com.example.campusapp.R
import com.example.campusapp.backend.DataRef
import com.example.campusapp.backend.DataRef.db
import com.google.firebase.firestore.DocumentSnapshot
import kotlinx.android.synthetic.main.forum_detail.*


class ForumFragment : Fragment() {
    private var mDoc: List<DocumentSnapshot> = listOf()
    private val TAG = "Forum"

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
        forum_id_tv.text = forumId
        chat_progress_bar.visibility = View.VISIBLE
        // Data for forum basic info
        db.document(DataRef.FORUMS_COLLECTION+"/"+forumId).get()
            .addOnSuccessListener { document ->
                if (document != null) {
                    Log.d(TAG, "DocumentSnapshot data: ${document.data}")
                    forum_title.text = document.getString("title")
                } else {
                    Log.d(TAG, "No such document")
                }
                forum_progress.visibility = View.GONE
            }
            .addOnFailureListener { exception ->
                Log.d(TAG, "get() failed with ", exception)
                forum_progress.visibility = View.GONE
            }

        viewManager = LinearLayoutManager(context)
        viewAdapter = MessageAdapter(mDoc)

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
//                    for (doc in mDoc) { Log.v(TAG, "${doc.id} -> ${doc.get("text")}") }
                    viewAdapter.updateData(mDoc)
                    recyclerView.adapter!!.notifyDataSetChanged()
                }
            }
    }
}
