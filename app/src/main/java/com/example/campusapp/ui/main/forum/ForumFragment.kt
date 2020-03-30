package com.example.campusapp.ui.main.forum

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs

import com.example.campusapp.R
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.forum_detail.*


class ForumFragment : Fragment() {

    private val db: FirebaseFirestore = FirebaseFirestore.getInstance()
    private var mDoc: List<DocumentSnapshot> = listOf()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.forum_detail, container, false)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val safeArgs: ForumFragmentArgs by navArgs()
        forum_id_tv.text = safeArgs.forumId
    }

}
