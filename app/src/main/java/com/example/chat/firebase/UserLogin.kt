package com.example.chat.firebase

import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

fun UserLogin(email: String, password: String, onResult: (Throwable?)->Unit){
    Firebase.auth.signInWithEmailAndPassword(email,password).addOnCompleteListener{
        onResult(it.exception)
    }
}