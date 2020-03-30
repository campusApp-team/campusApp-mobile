package com.example.campusapp

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.navOptions
import com.example.campusapp.ui.main.event.EventListFragment
import com.example.campusapp.ui.main.forum.ForumListFragment
import com.example.campusapp.ui.main.project.ProjectListFragment
import com.google.android.material.bottomappbar.BottomAppBar
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.tabs.TabLayout


class MainActivity : AppCompatActivity(),
    ProjectListFragment.OnProjectFragmentInteractionListener,
    ForumListFragment.OnForumFragmentInteractionListener,
    EventListFragment.OnEventFragmentInteractionListener{

    // lateinit is used to initialise variables only after the view is inflated.
    // This helps in reducing calls to findViewbyId, which is expensive
    lateinit var bottomAppBar: BottomAppBar
    lateinit var fab: FloatingActionButton
    lateinit var tabLayout: TabLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val host: NavHostFragment = supportFragmentManager
            .findFragmentById(R.id.my_nav_host_fragment) as NavHostFragment? ?: return
        val navController = host.navController

        setupUI(navController)

    }

    override fun onBackPressed() {
        val builder: AlertDialog.Builder = AlertDialog.Builder(this@MainActivity,R.style.AlertDialogueCustom)
        builder.setMessage("Do you want to exit?")
            .setCancelable(true)
            .setPositiveButton("Yes") { dialog, id -> finish() }
            .setNegativeButton("No" ) { dialog, id -> dialog.cancel() }
        val alert: AlertDialog = builder.create()
        alert.show()
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

    private fun setupUI(navController:NavController){
        val options = navOptions {
            anim {
                enter = R.anim.fade_in
                exit = R.anim.fade_out
                popEnter = R.anim.fade_in
                popExit = R.anim.fade_out
            }
        }
        tabLayout = findViewById(R.id.tab_layout)
        tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabReselected(tab: TabLayout.Tab?) {
                when(tabLayout.selectedTabPosition){
                    0 -> Toast.makeText(applicationContext,"Forums",Toast.LENGTH_SHORT).show()
                    1 -> Toast.makeText(applicationContext,"Projects",Toast.LENGTH_SHORT).show()
                    else -> Toast.makeText(applicationContext,"Events",Toast.LENGTH_SHORT).show()
                }
            }
            override fun onTabUnselected(tab: TabLayout.Tab) {
            }
            override fun onTabSelected(tab: TabLayout.Tab) {
                when(tabLayout.selectedTabPosition){
                    0 -> {
                        navController.popBackStack()
                        navController.navigate(R.id.forumlist_dest, null, options)
                    }
                    1 -> {
                        navController.popBackStack()
                        navController.navigate(R.id.projectlist_dest,null,options)
                    }
                    else -> {
                        navController.popBackStack()
                        navController.navigate(R.id.eventlist_dest,null,options)
                    }
                }
            }
        })
        bottomAppBar = findViewById(R.id.bottom)
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

}