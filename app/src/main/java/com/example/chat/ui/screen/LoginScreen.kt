package com.example.chat.ui.screen

import android.widget.Toast
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.chat.R
import com.example.chat.model.MainViewModel
import com.example.chat.ui.theme.Violet

@Composable
fun LoginScreen(navController: NavController, viewModel: MainViewModel) {
    var email by remember {
        mutableStateOf("")
    }

    var password by remember {
        mutableStateOf("")
    }

    val context = LocalContext.current
    val spacerSize = 50.dp
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(start = 10.dp, top = 50.dp)
    ) {

        Row(modifier = Modifier.fillMaxWidth()) {

            Column(modifier = Modifier.width(280.dp)) {
                Text(
                    text = "Добро пожаловать обратно!",
                    fontWeight = FontWeight.Bold,
                    fontSize = 24.sp
                )
                Text(
                    text = "Рады видеть вас снова. Чтобы войти используйте свою учётную запись.",
                    fontSize = 15.sp
                )
            }

            Image(
                painter = painterResource(id = R.drawable.login),
                contentDescription = "",
                modifier = Modifier.height(189.dp),
                contentScale = ContentScale.FillHeight
            )
        }

        Column(
            verticalArrangement = Arrangement.spacedBy(10.dp),
            modifier = Modifier.padding(10.dp)
        ) {

            OutlinedTextField(
                value = email,
                onValueChange = { email = it },
                label = { Text(text = "Почта") },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(20.dp)
            )

            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                label = { Text(text = "Пароль") },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(20.dp)
            )

            Spacer(modifier = Modifier.height(spacerSize))

            Button(modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),
                colors = ButtonDefaults.buttonColors(backgroundColor = Violet),
                shape = RoundedCornerShape(20.dp),
                onClick = {
                    viewModel.userLogin(email, password) { error ->
                        if (error == null) {
                            navController.navigate("chat_list")
                        } else {
                            Toast.makeText(context, error.message, Toast.LENGTH_LONG).show()
                        }
                    }
                    navController.navigate("chat_list")
                }) {
                Text(text = "Войти", color = Color.White)
            }

            Spacer(modifier = Modifier.height(spacerSize))

            Row(modifier = Modifier.fillMaxWidth()) {
                Canvas(modifier = Modifier
                    .width(90.dp)
                    .height(15.dp), onDraw = {
                    drawLine(
                        color = Color.Gray,
                        start = Offset(0f, size.height / 1.9f),
                        end = Offset(size.width, size.height / 1.9f),
                    )
                })

                Text(
                    text = "Нет аккаунта? Зарегестрироваться",
                    color = Color.Black,
                    modifier = Modifier.clickable { navController.navigate("registration") }.height(20.dp))

                Canvas(modifier = Modifier
                    .width(90.dp)
                    .height(15.dp), onDraw = {
                    drawLine(
                        color = Color.Gray,
                        start = Offset(0f, size.height / 1.5f),
                        end = Offset(size.width, size.height / 1.5f),
                    )
                })

            }
        }

    }
}
