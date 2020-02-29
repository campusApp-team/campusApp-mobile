package com.example.campusapp

import android.graphics.Color
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager.widget.ViewPager
import com.example.campusapp.ui.main.EventFragment
import com.example.campusapp.ui.main.ForumFragment
import com.example.campusapp.ui.main.ProjectFragment
import com.example.campusapp.ui.main.SectionsPagerAdapter
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.tabs.TabLayout

class MainActivity : AppCompatActivity(),
    ProjectFragment.OnListFragmentInteractionListener,
    ForumFragment.OnListFragmentInteractionListener,
    EventFragment.OnEventFragmentInteractionListener{

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val sectionsPagerAdapter = SectionsPagerAdapter(this, supportFragmentManager)
        val viewPager: ViewPager = findViewById(R.id.view_pager)
        viewPager.adapter = sectionsPagerAdapter
        val tabs: TabLayout = findViewById(R.id.tabs)
        tabs.setupWithViewPager(viewPager)
        tabs.setTabTextColors(R.color.color6,resources.getColor(R.color.white))
        tabs.setSelectedTabIndicatorColor(resources.getColor(R.color.white))

        val fab: FloatingActionButton = findViewById(R.id.fab)
        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }
    }

    override fun onForumFragmentInteraction(id: String) {
        Toast.makeText(this,"todo : Forum Clicked",Toast.LENGTH_SHORT).show()
    }

    override fun onProjectFragmentInteraction(id: String) {
        Toast.makeText(this,"todo : Project Clicked",Toast.LENGTH_SHORT).show()
    }

    override fun onEventFragmentInteraction(id: String) {
        Toast.makeText(this,"todo : Event Clicked",Toast.LENGTH_SHORT).show()
    }

}