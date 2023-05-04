package com.example.chat.ui.screen

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.chat.R
import com.example.chat.model.MainViewModel
import com.example.chat.ui.theme.Violet

@Composable
fun ChatListScreen(viewModel: MainViewModel, navController: NavController) {

    if (viewModel.USER.key == "") {
        viewModel.inputUser()
    }
    Scaffold(topBar = {
        TopAppBar(
            title = {},
            backgroundColor = Color.White,
            contentColor = Color.White,
            actions = {
                IconButton(onClick = { viewModel.logout()}) {
                    Icon(Icons.Default.ExitToApp, contentDescription = "", tint= Violet)
                }
            }
        )
    }, content = {
        Column() {
            Spacer(modifier = Modifier.height(50.dp))

            LazyColumn(
                modifier = Modifier.padding(10.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(viewModel.users.value) { user ->
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .border(
                                BorderStroke(2.dp, SolidColor(Violet)),
                                shape = RoundedCornerShape(10.dp)
                            )

                            .padding(5.dp)
                            .clickable {
                                viewModel.recipient.value = user
                                navController.navigate("chat")
                            }
                    ) {
                        Row(modifier = Modifier.fillMaxWidth()) {
                            Image(
                                painter = painterResource(id = R.drawable.avatar),
                                contentDescription = "",
                                contentScale = ContentScale.Crop,
                                modifier = Modifier
                                    .size(60.dp)
                                    .clip(CircleShape)
                            )
                            Text(
                                text = user.name, fontSize = 16.sp, fontWeight = FontWeight.Bold,
                                modifier = Modifier.padding(start = 10.dp)
                            )
                        }
                    }
                }
            }
        }
        if (viewModel.isLoad.value) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        }
    })
}