package com.example.campusapp.ui.main

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.example.campusapp.R
import com.example.campusapp.ui.main.event.EventListFragment
import com.example.campusapp.ui.main.forum.ForumListFragment
import com.example.campusapp.ui.main.project.ProjectListFragment


private val TAB_TITLES = arrayOf(
    R.string.tab_text_forums,
    R.string.tab_text_projects,
    R.string.tab_text_events
)
/**
 * A [FragmentPagerAdapter] that returns a fragment corresponding to
 * one of the sections/tabs/pages.
 */
class SectionsPagerAdapter(private val context: Context, fm: FragmentManager) : FragmentPagerAdapter(fm) {
    override fun getItem(position: Int): Fragment {
        // getItem is called to instantiate the fragment for the given page.
        // Return corresponding fragments
        return when (position) {
            0 -> ForumListFragment()
            1 -> ProjectListFragment()
            else -> EventListFragment()
        }
    }
//    override fun getPageTitle(position: Int): CharSequence? {
//        return context.resources.getString(TAB_TITLES[position])
//    }

    override fun getCount(): Int {
        return 3
    }
}