package com.example.chat.firebase

import com.example.chat.model.MessageModel
import com.google.firebase.database.FirebaseDatabase

fun SendMessage(text: String, recipient: String, sender: String) {
    try {
        val database = FirebaseDatabase.getInstance()
        val myRef = database.getReference("messages")

        myRef.push().setValue(MessageModel(text = text, keyRecipient = recipient, keySender = sender))
    } catch (e: Exception) {
        println("Error: ${e.message}")
    }
}