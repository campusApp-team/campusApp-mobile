package com.example.campusapp.ui.main.forum

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.campusapp.R
import com.example.campusapp.backend.DataRef.ANON_USERNAME
import com.example.campusapp.backend.DataRef.FORUMS_MESSAGES
import com.example.campusapp.backend.DataRef.FORUMS_SUBFORUMS
import com.example.campusapp.backend.DataRef.MESSAGES_SENDER
import com.example.campusapp.backend.DataRef.MESSAGES_TEXT
import com.example.campusapp.backend.DataRef.MESSAGES_TIMESTAMP
import com.example.campusapp.backend.DataRef.db
import com.google.firebase.Timestamp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.Query
import kotlinx.android.synthetic.main.forum_detail.*


class ForumFragment : Fragment() {
    private var messagesDoc: List<DocumentSnapshot> = listOf()
    private var subforumDoc: List<DocumentSnapshot> = listOf()

    private lateinit var recyclerView: RecyclerView
    private lateinit var viewAdapter: MessageAdapter
    private lateinit var viewManager: RecyclerView.LayoutManager
    private lateinit var reference: String
    private lateinit var titlePath: String

    private var user:FirebaseUser? = null
    private var listener: OnSubforumInteractionListener? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val safeArgs: ForumFragmentArgs by navArgs()
        reference = safeArgs.reference
        titlePath = safeArgs.titlePath
        return inflater.inflate(R.layout.forum_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        user = FirebaseAuth.getInstance().currentUser
        var username = ANON_USERNAME
        if(user==null){
            Toast.makeText(context, "Anonymous", Toast.LENGTH_SHORT).show()
        }else{
            username = user!!.displayName.toString()
        }

        progress_bar_chat.visibility = View.VISIBLE
        et_message.requestFocus()
        send_btn_message.setOnClickListener {
            sentMessage(username)
        }
        updateUI(view)
    }

    private fun sentMessage(username: String) {
        val msg = et_message.text.toString()
        if (msg.isNotEmpty()){
            // Add a new document with a generated id.
            et_message.text.clear()
            val data = hashMapOf(
                MESSAGES_SENDER to username,
                MESSAGES_TEXT to msg,
                MESSAGES_TIMESTAMP to Timestamp.now()
            )

            db.collection("$reference/$FORUMS_MESSAGES/")
                .add(data)
                .addOnSuccessListener { documentReference ->
                    Log.d(this.javaClass.simpleName, "DocumentSnapshot written with ID: ${documentReference.id}")
                }
                .addOnFailureListener { e ->
                    Log.w(this.javaClass.simpleName, "Error adding document", e)
                    et_message.setText(msg)
                    Toast.makeText(context, "Error sending message", Toast.LENGTH_SHORT).show()
                }
            recyclerView.smoothScrollToPosition(viewAdapter.itemCount)
        }
    }

    private fun updateUI(view:View) {
        viewManager = LinearLayoutManager(context)
        viewAdapter = MessageAdapter(listener, messagesDoc, subforumDoc, reference, titlePath)

        recyclerView = view.findViewById<RecyclerView>(R.id._recycler_chat).apply {
            layoutManager = viewManager
            adapter = viewAdapter
        }

        var readStatus = 0
        // retrieve sub threads
        db.collection("$reference/$FORUMS_SUBFORUMS")
            .addSnapshotListener{ value, e ->
                if (progress_bar_chat != null){
                    if (readStatus==1){
                        progress_bar_chat.visibility = View.INVISIBLE
                    }else{
                        readStatus++
                    }
                }
                if (e != null) {
                    Log.w(this.javaClass.simpleName, "Listen failed.", e)
                    return@addSnapshotListener
                }
                if (value != null){
                    if( !value.isEmpty) {
                        subforumDoc = value.documents
//                    for (doc in mDoc) { Log.v(this.javaClass.simpleName, "${doc.id} -> ${doc.get("text")}") }
                    }
                }
                viewAdapter.updateSubForumsData(subforumDoc)
            }

        // retrieve messages
        db.collection("$reference/$FORUMS_MESSAGES")
            .orderBy(MESSAGES_TIMESTAMP, Query.Direction.ASCENDING)
            .addSnapshotListener { value, e ->
                if (progress_bar_chat != null){
                    if (readStatus==1){
                        progress_bar_chat.visibility = View.INVISIBLE
                    }else{
                        readStatus++
                    }
                }
                if (e != null) {
                    Log.w(this.javaClass.simpleName, "Listen failed.", e)
                    return@addSnapshotListener
                }
                if (value != null){
                    if( !value.isEmpty) {
                        messagesDoc = value.documents
                        forum_empty_view_msg.visibility = View.GONE
//                    for (doc in mDoc) { Log.v(this.javaClass.simpleName, "${doc.id} -> ${doc.get("text")}") }
                    }else{
                        forum_empty_view_msg.visibility = View.VISIBLE
                    }
                }
                viewAdapter.updateMessagesData(messagesDoc)
            }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnSubforumInteractionListener) {
            listener = context
        } else {
            throw RuntimeException(context.toString() + " must implement OnListFragmentInteractionListener")
        }
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     *
     * See the Android Training
     * [Communicating with Other Fragments](http://developer.android.com/training/basics/fragments/communicating.html)
     * for more information.
     */
    interface OnSubforumInteractionListener {
        fun onSubForumClicked(reference: String, titlePath:String)
    }

}
