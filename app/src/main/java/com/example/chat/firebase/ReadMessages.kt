package com.example.chat.firebase

import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import com.example.chat.model.MessageModel
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import java.lang.Exception

fun ReadMessages(user: String): SnapshotStateList<MessageModel> {
    val _messages = mutableStateListOf<MessageModel>()

    try {
        val database = FirebaseDatabase.getInstance()
        val myRef = database.getReference("messages")

        myRef.addChildEventListener(object : ChildEventListener {
            override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
                val value = snapshot.getValue(MessageModel::class.java)
                if (value != null) {
                    if (value.keySender == user || value.keyRecipient == user) {
                        _messages.add(value)
                    }
                    Log.d("FIREBASE_LIST", value.text)
                }
            }

            override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {
                TODO("Not yet implemented")
            }

            override fun onChildRemoved(snapshot: DataSnapshot) {
                TODO("Not yet implemented")
            }

            override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {
                TODO("Not yet implemented")
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
    } catch (e: Exception) {
        println("Error: ${e.message}")
    }

    return _messages
}