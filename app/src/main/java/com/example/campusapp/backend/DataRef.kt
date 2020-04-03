package com.example.campusapp.backend

import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.ktx.storage

// this class is indented to do backend queries. Currently used only as reference for const labels for queries
object DataRef {
    val db = Firebase.firestore
    val storage = Firebase.storage
//    private var forumsDocSnap: List<DocumentSnapshot> = listOf()
//    private var projectsDocSnap: List<DocumentSnapshot> = listOf()
    const val FORUMS_COLLECTION = "forums"
    const val FORUMS_MESSAGES = "messages"

    const val PROJECTS_COLLECTION = "projects"
    const val EVENTS_COLLECTION = "events"
    const val TEMPLATE_DATA = "0template"

}
