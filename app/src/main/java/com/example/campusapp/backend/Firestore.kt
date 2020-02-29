package com.example.campusapp.backend

import com.google.firebase.firestore.FirebaseFirestore

// this class is indented to do backend queries. Currently used only as reference for const labels for queries
object Firestore {
    val db:FirebaseFirestore = FirebaseFirestore.getInstance()

//    private var forumsDocSnap: List<DocumentSnapshot> = listOf()
//    private var projectsDocSnap: List<DocumentSnapshot> = listOf()
    const val FORUMS_COLLECTION = "forums"
    const val PROJECTS_COLLECTION = "projects"
    const val EVENTS_COLLECTION = "events"

    const val TAG = "firestore"

/*
    fun getForums(f: ForumsListViewAdapter): ArrayList<DocumentSnapshot>? {
        if (forumsDocSnap==null || forumsDocSnap!!.size == 0) {
            loadForums(f)
        }
        return forumsDocSnap
    }

    private fun loadForums(fragAdapter: ForumsListViewAdapter) {
        forumsDocSnap = ArrayList()
        db.collection(FORUMS)
                .addSnapshotListener(EventListener<QuerySnapshot> { newForumsSnapshot, e ->
                    if (e != null) {
                        Log.w(LOGTAG, "Listen failed.", e)
                        return@EventListener
                    }

                    for (dc in newForumsSnapshot!!.documentChanges) {
                        when (dc.type) {
                            DocumentChange.Type.ADDED -> {
                                Log.v(LOGTAG, "New thread : " + dc.document.data)
                                forumsDocSnap!!.add(dc.document)
                                fragAdapter.notifyItemInserted(forumsDocSnap!!.lastIndex)
                            }
                            DocumentChange.Type.MODIFIED ->{
                                Log.v("fD/docChange", "Modified city: " + dc.document.data)
                                val id = dc.document.id
                                for (i in forumsDocSnap!!.indices) {
                                    if(forumsDocSnap!![i].id==id){
                                        forumsDocSnap!!.removeAt(i)
                                        forumsDocSnap!!.add(i,dc.document)
                                        fragAdapter.notifyItemChanged(i)
                                        break
                                    }
                                }
                            }
                            DocumentChange.Type.REMOVED ->{
                                Log.v(LOGTAG, "Removed thread : " + dc.document.data)
                                val id = dc.document.id
                                for (i in forumsDocSnap!!.indices) {
                                    if(forumsDocSnap!![i].id==id){
                                        forumsDocSnap!!.removeAt(i)
                                        fragAdapter.notifyItemRemoved(i)
                                        break
                                    }
                                }
                            }
                        }
                    }
                })
    }
    fun loadForumMessages(id:String, fragAdapter: MessageRecyclerViewAdapter):ArrayList<DocumentSnapshot>{
        val msgDocSnapshot: ArrayList<DocumentSnapshot> = ArrayList()

        val docRef=db.collection("forums/$id/msg").orderBy("time")

        docRef.addSnapshotListener(EventListener { snapshot, e ->
            if (e != null) {
                Log.w("$LOGTAG/MessageListen", "Listen failed.", e)
                return@EventListener
            }

            if (snapshot != null && !snapshot.isEmpty) {
                Log.v("$LOGTAG/MessageAdded", "" + snapshot)
//                documentSnapshot!!.removeAt(i)
//                msgDocSnapshot.clear()
                var i=0
                while(i<snapshot.documentChanges.size){
                    msgDocSnapshot.add(snapshot.documentChanges[i].document)
                    i++
                }
                fragAdapter.notifyDataSetChanged()
            } else {
                Log.w("$LOGTAG/MessageListen", "Current data: null")
            }
        })

        return msgDocSnapshot
    }

    fun getProjects(f: ProjectsRecyclerViewAdapter): List<DocumentSnapshot>? {
        //  TODO 20 data retrieval works fine, but its not passed to fragment.
        projectsDocSnap = listOf()
        db.collection(PROJECTS_COLLECTION)
            .addSnapshotListener { value, e ->
                if (e != null) {
                    Log.w(TAG, "Listen failed.", e)
                    return@addSnapshotListener
                }
                if (value != null) {
                    projectsDocSnap = value.documents
                    for (doc in value) {
                        Log.d(TAG, "${doc.id} -> ${doc.get("title")}")
                    }
                    f.notifyDataSetChanged()
                }
            }
        return projectsDocSnap
    }
    */
}
