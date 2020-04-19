package com.example.campusapp.backend

import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.ktx.storage

// Used only as reference for const labels for queries
object DataRef {
    val db = Firebase.firestore
    val storage = Firebase.storage
//    private var forumsDocSnap: List<DocumentSnapshot> = listOf()
//    private var projectsDocSnap: List<DocumentSnapshot> = listOf()
    const val ANON_USERNAME = "Anonymous"

    const val FORUMS_COLLECTION = "forums"
    const val FORUMS_SUBFORUMS = "subforums"
    const val FORUMS_TITLE = "title"
    const val FORUMS_DESCRIPTION = "description"
    const val FORUMS_ACTIVE_USERS = "active_users"
    const val FORUMS_VOTES = "votes"
    const val FORUMS_MESSAGES = "messages"
    const val MESSAGES_SENDER = "sender"
    const val MESSAGES_TEXT = "text"
    const val MESSAGES_TIMESTAMP = "timestamp"

    const val PROJECTS_COLLECTION = "projects"
    const val EVENTS_COLLECTION = "events"
    const val TEMPLATE_DATA = "0template"

}
