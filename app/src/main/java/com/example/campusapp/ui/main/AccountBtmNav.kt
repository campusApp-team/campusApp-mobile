package com.example.campusapp.ui.main

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.FrameLayout
import android.widget.LinearLayout
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.annotation.NonNull
import androidx.cardview.widget.CardView
import androidx.fragment.app.Fragment
import com.example.campusapp.R
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.IdpResponse
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetBehavior.*
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.android.synthetic.main.bottom_account.*

class AccountBtmNav : Fragment() {

    private var user: FirebaseUser? = null
    private val RC_SIGN_IN = 101
    private var isSignedIn = false
    lateinit var button:CardView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.bottom_account, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        behavior = from(background_container)
        behavior.state = STATE_HIDDEN
        backgroundContainer = background_container
        scrimView = scrim_view
        scrimView.setOnClickListener { close() }
        behavior.setBottomSheetCallback(object:BottomSheetBehavior.BottomSheetCallback() {
            override fun onSlide(bottomSheet: View, slideOffset: Float) {
            }
            override fun onStateChanged(@NonNull bottomSheet:View, newState:Int) {
                if(newState== STATE_HIDDEN) {
                        scrimView.visibility = View.INVISIBLE
                    }
                state.text = when(newState){
                    STATE_DRAGGING -> "Dragging"
                    STATE_COLLAPSED -> "Collapsed"
                    STATE_HIDDEN -> "Hidden"
                    STATE_SETTLING -> "Settling"
                    else -> "Half expanded"
                }
            }
        })

        button = sign_in_btn

        isSignedIn = checkAuth()
        setupButton()
        button.setOnClickListener {
            if (isSignedIn){
                signOut()
            }else{
                signIn()
            }
        }
    }

    private fun signIn(){

        val providers = arrayListOf(
            AuthUI.IdpConfig.EmailBuilder().build(),
            AuthUI.IdpConfig.PhoneBuilder().build())

        // Create and launch sign-in intent
        startActivityForResult(
            AuthUI.getInstance()
                .createSignInIntentBuilder()
                .setAvailableProviders(providers)
                .setLogo(R.drawable.ic_launcher_foreground)
                .setTheme(R.style.AppTheme)
                .build(),
            RC_SIGN_IN)
//                .setTosAndPrivacyPolicyUrls("https://example.com/terms.html","https://example.com/privacy.html")
//          TODO20 setup deep link https://firebase.google.com/docs/auth/android/firebaseui#email_link_authentication
    }

    // after sign-in flow, the result is obtained in onActivityResult()
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {

        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == RC_SIGN_IN) {
            val response = IdpResponse.fromResultIntent(data)

            when {
                resultCode == Activity.RESULT_OK -> {
                    // Successfully signed in
                    user = FirebaseAuth.getInstance().currentUser
                }
                response==null -> {
                    Toast.makeText(context, "sign-in cancelled", Toast.LENGTH_SHORT).show()
                }
                else -> {
                    Toast.makeText(context, "sign-in error :\n${response.error}", Toast.LENGTH_SHORT).show()
                    // Sign in failed. If response is null the user canceled the sign-in flow using the back button.
                    // Otherwise check response.getError().getErrorCode() and handle the error.
                }
            }
        }
        setupButton()
    }

    private fun setupButton(){
        isSignedIn = checkAuth()
        if (isSignedIn){
            username_tv_bottom.text = FirebaseAuth.getInstance().currentUser!!.displayName
            sign_in_tv.text = "Sign out"
        } else {
            username_tv_bottom.text = "Anonymous"
            sign_in_tv.text = "Sign In"
        }
    }

    private fun signOut(){
        AuthUI.getInstance()
            .signOut(context!!)
            .addOnCompleteListener {
                Toast.makeText(context, "signed out successfully", Toast.LENGTH_SHORT).show()
                setupButton()
                // ...
            }
//        Deleting User
//        AuthUI.getInstance()
//            .delete(this)
//            .addOnCompleteListener {
//                // ...
//            }
    }

    private fun checkAuth(): Boolean {
        user = FirebaseAuth.getInstance().currentUser
        return if(user==null){
            Toast.makeText(context, "not authenticated.", Toast.LENGTH_SHORT).show()
            false
        }else{
            Toast.makeText(context, "authenticated.", Toast.LENGTH_SHORT).show()
            true
        }
    }

    fun getUserData(){
//        TODO fetch user details and return to mainActivity
    }

    companion object {
        const val TAG = "ModalBottomSheet"
    }

    /**
     *  Below methods are implemented based on the Material Design guidelines via  case study project
     *  [ReplyApp] ( https://material.io/design/material-studies/reply.html )
     *  ( https://github.com/material-components/material-components-android-examples/tree/develop/Reply )
     *
     */
    private lateinit var behavior: BottomSheetBehavior<FrameLayout>
    private lateinit var backgroundContainer: FrameLayout
    private lateinit var scrimView: View

    private val closeDrawerOnBackPressed = object : OnBackPressedCallback(false) {
        override fun handleOnBackPressed() {
            close()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requireActivity().onBackPressedDispatcher.addCallback(this, closeDrawerOnBackPressed)
    }

    fun toggle() {
        when (behavior.state) {
            STATE_HIDDEN -> open()
            STATE_HALF_EXPANDED,
            STATE_EXPANDED,
            STATE_COLLAPSED -> close()
        }
    }

    private fun open() {
        scrimView.visibility = View.VISIBLE
        behavior.state = STATE_HALF_EXPANDED
    }

    private fun close() {
        scrimView.visibility = View.INVISIBLE
        behavior.state = STATE_HIDDEN
    }

}
