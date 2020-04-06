package com.example.campusapp

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.navOptions
import com.example.campusapp.ui.main.AccountBottomFragment
import com.example.campusapp.ui.main.event.EventListFragment
import com.example.campusapp.ui.main.event.EventListFragmentDirections
import com.example.campusapp.ui.main.forum.ForumListFragment
import com.example.campusapp.ui.main.forum.ForumListFragmentDirections
import com.example.campusapp.ui.main.project.ProjectListFragment
import com.example.campusapp.ui.main.project.ProjectListFragmentDirections
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.IdpResponse
import com.google.android.material.bottomappbar.BottomAppBar
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.tabs.TabLayout
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class MainActivity : AppCompatActivity(),
    ProjectListFragment.OnProjectFragmentInteractionListener,
    ForumListFragment.OnForumFragmentInteractionListener,
    EventListFragment.OnEventFragmentInteractionListener{

    // lateinit is used to initialise variables only after the view is inflated.
    // This helps in reducing calls to (expensive) findViewbyId()
    lateinit var btmAppBar: BottomAppBar
    lateinit var fab: FloatingActionButton
    lateinit var tabLayout: TabLayout
    lateinit var navController: NavController
    lateinit var btmSheet: AccountBottomFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val host: NavHostFragment = supportFragmentManager
            .findFragmentById(R.id.my_nav_host_fragment) as NavHostFragment? ?: return
        navController = host.navController

        setupUI()


    }


    override fun onBackPressed() {
        when(navController.currentDestination!!.id){
            R.id.forum_dest,R.id.project_dest,R.id.event_dest -> {
                tabLayout.visibility = View.VISIBLE
                fab.show()
            }
            else -> tabLayout.visibility = View.INVISIBLE
        }
        super.onBackPressed()
//        val builder: AlertDialog.Builder = AlertDialog.Builder(this@MainActivity,R.style.AlertDialogueCustom)
//        builder.setMessage("Do you want to exit?")
//            .setCancelable(true)
//            .setPositiveButton("Yes") { dialog, id -> finish() }
//            .setNegativeButton("No" ) { dialog, id -> dialog.cancel() }
//        val alert: AlertDialog = builder.create()
//        alert.show()
    }

    override fun onForumFragmentInteraction(id: String) {
        val action = ForumListFragmentDirections.viewDetails(id)
        navController.navigate(action)
        tabLayout.visibility = View.INVISIBLE
        fab.hide()
//        bottomAppBar.performHide()
    }

    override fun onProjectFragmentInteraction(id: String) {
        val action = ProjectListFragmentDirections.viewDetails(id)
        navController.navigate(action)
        tabLayout.visibility = View.INVISIBLE
        fab.hide()
    }

    override fun onEventFragmentInteraction(id: String) {
        val action = EventListFragmentDirections.viewDetails(id)
        navController.navigate(action)
        tabLayout.visibility = View.INVISIBLE
        fab.hide()
    }

    private fun setupUI(){
        btmSheet = AccountBottomFragment()
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
        btmAppBar = findViewById(R.id.bottom)
        btmAppBar.setNavigationOnClickListener {
            // TODO : account details bottom sheet
            btmSheet.show(supportFragmentManager,AccountBottomFragment.TAG)
            Toast.makeText(this,"todo : Account bottomsheet",Toast.LENGTH_SHORT).show()
        }

        fab = findViewById(R.id.fab)
//        fab.setOnClickListener { view ->
//            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_SHORT)
//                .setAnchorView(fab)
//                .setAction("Action", null).show()
//        }
    }




}