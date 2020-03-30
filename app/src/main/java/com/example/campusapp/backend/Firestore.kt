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

    const val TEMPLATE_DATA = "0template"

}
