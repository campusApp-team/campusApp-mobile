package com.example.campusapp

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager.widget.ViewPager
import com.example.campusapp.ui.main.EventFragment
import com.example.campusapp.ui.main.ForumFragment
import com.example.campusapp.ui.main.ProjectFragment
import com.example.campusapp.ui.main.SectionsPagerAdapter
import com.google.android.material.bottomappbar.BottomAppBar
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(),
    ProjectFragment.OnProjectFragmentInteractionListener,
    ForumFragment.OnForumFragmentInteractionListener,
    EventFragment.OnEventFragmentInteractionListener{

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val sectionsPagerAdapter = SectionsPagerAdapter(this, supportFragmentManager)
        val viewPager: ViewPager = findViewById(R.id.view_pager)
        viewPager.adapter = sectionsPagerAdapter
        val bottomAppBar: BottomAppBar = findViewById(R.id.bottom)

        bottomAppBar.setOnMenuItemClickListener {
            viewPager.currentItem = when (it.itemId) {
                R.id.forum_menu -> 0
                R.id.project_menu -> 1
                else -> 2
            }
            true
        }

        bottomAppBar.setNavigationOnClickListener {
            // TODO 4 : account details bottom sheet
            Toast.makeText(this,"todo : Account bottomsheet",Toast.LENGTH_SHORT).show()
        }

        val fab: FloatingActionButton = findViewById(R.id.fab)
        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_SHORT)
                .setAnchorView(fab)
                .setAction("Action", null).show()
//            bottomBar.fabAlignmentMode = when(bottomBar.fabAlignmentMode){
//                BottomAppBar.FAB_ALIGNMENT_MODE_END -> BottomAppBar.FAB_ALIGNMENT_MODE_CENTER
//                else -> BottomAppBar.FAB_ALIGNMENT_MODE_END
//            }
        }
    }

    override fun onForumFragmentInteraction(id: String) {
        Toast.makeText(this,"todo : Forum Clicked",Toast.LENGTH_SHORT).show()
        // TODO 1 : make bottomSheet for Forums
    }

    override fun onProjectFragmentInteraction(id: String) {
        Toast.makeText(this,"todo : Project Clicked",Toast.LENGTH_SHORT).show()
        // TODO 2 : make bottomSheet for Projects
    }

    override fun onEventFragmentInteraction(id: String) {
        Toast.makeText(this,"todo : Event Clicked",Toast.LENGTH_SHORT).show()
        // TODO 3 : make activity for Events/Extras
    }

}