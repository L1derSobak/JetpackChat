package com.example.chat

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.chat.model.MainViewModel
import com.example.chat.ui.screen.ChatListScreen
import com.example.chat.ui.screen.ChatScreen
import com.example.chat.ui.screen.LoginScreen
import com.example.chat.ui.screen.RegistrationScreen
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

@Composable
fun NavGraph(viewModel: MainViewModel) {
    val navController = rememberNavController()

    NavHost(navController = navController,startDestination = if(Firebase.auth.currentUser != null) "chat_list" else "login"){
        composable("registration"){
            RegistrationScreen(navController = navController, viewModel = viewModel)
        }
        composable("chat_list"){
            ChatListScreen(viewModel = viewModel, navController)
        }
        composable("chat"){
            ChatScreen(viewModel)
        }
        composable("login"){
            LoginScreen(navController = navController, viewModel = viewModel)
        }
    }
}