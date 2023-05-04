package com.example.chat.ui.screen

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Send
import androidx.compose.runtime.*
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
import com.example.chat.R
import com.example.chat.model.MainViewModel
import com.example.chat.ui.theme.Violet

@Composable
fun ChatScreen(viewModel: MainViewModel) {

    var message by remember {
        mutableStateOf("")
    }
    Scaffold(
        topBar = {
            TopAppBar(
                backgroundColor = Color.White,
                title = {
                    Row() {
                        Image(
                            painter = painterResource(id = R.drawable.avatar),
                            contentDescription = "",
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .size(50.dp)
                                .clip(CircleShape)
                        )
                        Text(
                            text = viewModel.recipient.value.name,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(start = 10.dp)
                        )
                    }
                })

        },
        content = {

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(15.dp),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                LazyColumn(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                    items(viewModel.chatMessage) { item ->
                        if (viewModel.USER.key == item.keySender) {
                            Box(
                                modifier = Modifier.fillMaxWidth(),
                                contentAlignment = Alignment.CenterEnd
                            ) {
                                Box(
                                    modifier = Modifier
                                        .clip(RoundedCornerShape(10.dp))
                                        .background(Violet)
                                ) {
                                    Text(
                                        text = item.text,
                                        color = Color.White,
                                        fontSize = 18.sp,
                                        modifier = Modifier.padding(15.dp)
                                    )
                                }
                            }
                        } else {
                            Box(
                                modifier = Modifier.fillMaxWidth(),
                                contentAlignment = Alignment.CenterStart
                            ) {
                                Box(
                                    modifier = Modifier.border(
                                        BorderStroke(
                                            2.dp,
                                            SolidColor(Violet)
                                        ), shape = RoundedCornerShape(10.dp)
                                    )
                                ) {
                                    Text(
                                        text = item.text,
                                        fontSize = 18.sp,
                                        modifier = Modifier.padding(15.dp)
                                    )

                                }
                            }
                        }
                        //Text(text = item.text)
                    }
                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(15.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    OutlinedTextField(
                        value = message,
                        onValueChange = { message = it },
                        modifier = Modifier.width(250.dp)
                    )
                    IconButton(onClick = {
                        if (message.trim().isEmpty()) return@IconButton
                        viewModel.sendText(message)
                        message = ""
                    }) {
                        Icon(imageVector = Icons.Default.Send, contentDescription = "")
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