package com.example.chat.model

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.chat.MainActivity
import com.example.chat.firebase.*
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.system.exitProcess

class MainViewModel : ViewModel() {
    var recipient = mutableStateOf(UserModel())
    var USER = UserModel()

    private var _isLoad = mutableStateOf(false)
    val isLoad
        get() = _isLoad

    //Users list
    val users: MutableState<List<UserModel>> = mutableStateOf(listOf(UserModel()))

    private val _allMessages: MutableState<List<MessageModel>> =
        mutableStateOf(listOf(MessageModel()))

    val chatMessage: List<MessageModel>
        get() = _allMessages.value.filter {
            (it.keyRecipient == recipient.value.key || it.keyRecipient == recipient.value.key)
        }

    fun getUsers() {
        viewModelScope.launch {
            _isLoad.value = true
            withContext(Dispatchers.IO) {
                Thread.sleep(2000)
            }
            users.value = GetUsers()
            _isLoad.value = false
        }
    }

    fun sendText(text: String) {
        viewModelScope.launch {
            _isLoad.value = true
            withContext(Dispatchers.IO) {
                Thread.sleep(2000)
            }
            SendMessage(text = text, sender = USER.key, recipient = recipient.value.key)
            _isLoad.value = false
        }
    }

    fun getAllMessages() {
        viewModelScope.launch {
            _allMessages.value = ReadMessages(USER.key)
        }
    }

    fun userRegistration(email: String, password: String, name: String, onResult: (Throwable?) -> Unit) {
        viewModelScope.launch {
            UserRegistration(email, password) { error ->
                if (error == null) {
                    if (Firebase.auth.currentUser != null) {
                        USER = UserModel(key = Firebase.auth.currentUser!!.uid, name = name)
                        SendUser(USER)
                        loadInfo()
                    }
                } else {
                    println("Registration error ${error.message}")
                }
                    onResult(error)
            }
        }
    }

    private fun loadInfo(){
        getUsers()
        getAllMessages()
    }

    fun inputUser(){
        viewModelScope.launch {
            withContext(Dispatchers.IO){
                Thread.sleep(1000)
            }
            USER = UserModel(key = Firebase.auth.currentUser!!.uid)
            loadInfo()
        }

    }

    fun userLogin(email: String, password: String, onResult: (Throwable?) -> Unit) {
        viewModelScope.launch {
            UserLogin(email = email, password = password) { error ->
                if (error == null) {
                    USER = UserModel(key = Firebase.auth.currentUser!!.uid)
                    loadInfo()
                } else {
                    println("Registration error ${error.message}")
                }

                onResult(error)
            }
        }
    }

    fun logout(){
        Firebase.auth.signOut()
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                Thread.sleep(2000)
            }


            val activity = MainActivity()
            activity.finish()

            exitProcess(0)
        }
    }
}