package com.example.focusarena

import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AppViewModel @Inject constructor(
    private val firebaseAuth: FirebaseAuth
) : ViewModel(){
    fun isUserLoggedIn(): Boolean{
        return firebaseAuth.currentUser!=null
    }
}