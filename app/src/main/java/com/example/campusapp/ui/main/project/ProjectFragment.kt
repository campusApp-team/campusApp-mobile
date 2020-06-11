
package com.example.campusapp.ui.main.project

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toolbar
import androidx.fragment.app.FragmentManager
import androidx.navigation.fragment.navArgs
import androidx.viewpager.widget.ViewPager

import com.example.campusapp.R
import com.google.android.material.tabs.TabLayout
import kotlinx.android.synthetic.main.project_detail.*

class ProjectFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.project_detail, container, false)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val safeArgs: ProjectFragmentArgs by navArgs()
    }

}