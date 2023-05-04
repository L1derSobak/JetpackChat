package com.example.chat.firebase

import com.example.chat.model.UserModel
import com.google.firebase.database.FirebaseDatabase

fun SendUser(user: UserModel){
    try{
        val database = FirebaseDatabase.getInstance()
        val myRef = database.getReference("users")
        myRef.push().setValue(user)
    }catch(e: Exception){
        println("Send user error: ${e.message}")
    }
}