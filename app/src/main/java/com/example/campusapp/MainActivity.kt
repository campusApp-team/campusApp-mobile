package com.example.campusapp

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.campusapp.ui.main.event.EventListFragment
import com.example.campusapp.ui.main.forum.ForumListFragment
import com.example.campusapp.ui.main.project.ProjectListFragment
import com.google.android.material.bottomappbar.BottomAppBar
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar


class MainActivity : AppCompatActivity(),
    ProjectListFragment.OnProjectFragmentInteractionListener,
    ForumListFragment.OnForumFragmentInteractionListener,
    EventListFragment.OnEventFragmentInteractionListener{

    // lateinit is used to initialise variables only after the view is inflated.
    // This helps in reducing calls to findViewbyId, which is expensive
    lateinit var bottomAppBar: BottomAppBar
    lateinit var fab: FloatingActionButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        bottomAppBar = findViewById(R.id.bottom)

//        bottomAppBar.setOnMenuItemClickListener {
//            viewPager.currentItem = when (it.itemId) {
//                R.id.forum_menu -> 0
//                R.id.project_menu -> 1
//                else -> 2
//            }
//            true
//        }

        bottomAppBar.setNavigationOnClickListener {
            // TODO 4 : account details bottom sheet
            Toast.makeText(this,"todo : Account bottomsheet",Toast.LENGTH_SHORT).show()
        }

        fab = findViewById(R.id.fab)
        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_SHORT)
                .setAnchorView(fab)
                .setAction("Action", null).show()
            bottomAppBar.fabAlignmentMode = when(bottomAppBar.fabAlignmentMode){
                BottomAppBar.FAB_ALIGNMENT_MODE_END -> BottomAppBar.FAB_ALIGNMENT_MODE_CENTER
                else -> BottomAppBar.FAB_ALIGNMENT_MODE_END
            }
        }
    }

    override fun onForumFragmentInteraction(id: String) {
        Toast.makeText(this,"todo : Forum Clicked",Toast.LENGTH_SHORT).show()
        // TODO 1 : make fragment for Forums
    }

    override fun onProjectFragmentInteraction(id: String) {
        Toast.makeText(this,"todo : Project Clicked",Toast.LENGTH_SHORT).show()
        // TODO 2 : make fragment for Projects
    }

    override fun onEventFragmentInteraction(id: String) {
        Toast.makeText(this,"todo : Event Clicked",Toast.LENGTH_SHORT).show()
        // TODO 3 : make fragment for Events/Extras
    }

}