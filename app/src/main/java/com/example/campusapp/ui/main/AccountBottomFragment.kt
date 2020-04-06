package com.example.campusapp.ui.main

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import com.example.campusapp.R
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.IdpResponse
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.android.synthetic.main.bottom_account.*

class AccountBottomFragment : BottomSheetDialogFragment() {

    private var user: FirebaseUser? = null
    private val RC_SIGN_IN = 101
    private var isSignedIn = false
    lateinit var button:Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.bottom_account, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        isSignedIn = checkAuth()
        button = sign_in_btn
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
//        isSignedIn = checkAuth()
//        setupButton()
    }

    private fun setupButton(){
        if (isSignedIn){
            button.text = "Sign out"
        }else{
            button.text = "Sign In"
        }
    }

    private fun signOut(){
        AuthUI.getInstance()
            .signOut(context!!)
            .addOnCompleteListener {
                Toast.makeText(context, "signed out successfully", Toast.LENGTH_SHORT).show()
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

    companion object {
        const val TAG = "ModalBottomSheet"
    }


}
