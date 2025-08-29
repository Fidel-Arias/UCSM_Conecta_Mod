package org.ucsmconecta.mod.components.body

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.jetbrains.compose.resources.Font
import org.ucsmconecta.mod.components.button.CustomButton
import org.ucsmconecta.mod.components.header.HeaderBodyLogin
import org.ucsmconecta.mod.components.inputs.EmailTextField
import org.ucsmconecta.mod.components.inputs.PasswordTextField
import org.ucsmconecta.mod.components.titles.SubtitleBody
import org.ucsmconecta.mod.ui.theme.PrimaryColor
import ucsmconectamod.composeapp.generated.resources.ReadexProBold
import ucsmconectamod.composeapp.generated.resources.Res

@Composable
fun BodyLogin(onLoginSuccess: () -> Unit) {
    val READEX_PRO_BOLD = FontFamily(
        Font(Res.font.ReadexProBold)
    ) // Fuente Readex Pro Bold

    Column(
        modifier = Modifier
            .padding(16.dp)
    ) {
        HeaderBodyLogin()
        Spacer(modifier = Modifier.height(20.dp)) // Espacio entre el encabezado y el cuerpo
        SubtitleBody(
            subtitle = "XXXI Jornada Internacional de Ingeniería de Sistemas",
            fontFamily = READEX_PRO_BOLD,
            fontSize = 18.sp
        ) // Título de la sección
        Spacer(modifier = Modifier.height(20.dp))
        Login(READEX_PRO_BOLD, onLoginSuccess = { onLoginSuccess() })
    }
}

@Composable
fun Login(
    readexProBold: FontFamily,
    onLoginSuccess: () -> Unit
) {
    // Variables para almacenar el estado de los campos de texto
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }

    // TextFields para el inicio de sesión
    val textFieldColors = TextFieldDefaults.colors(
        focusedIndicatorColor = PrimaryColor,
        unfocusedIndicatorColor = Color.Transparent,
        focusedContainerColor = Color.White,
        unfocusedContainerColor = Color.White,
        cursorColor = PrimaryColor,
        focusedLabelColor = Color.Black,
        unfocusedLabelColor = Color.Black
    )

    Column(
        modifier = Modifier
            .fillMaxHeight(0.75f)
            .fillMaxWidth()
            .clip(RoundedCornerShape(18.dp)) // <- recorta el contenedor con esquinas redondeadas
            .background(Color.Black.copy(0.5f))
            .padding(20.dp), // Espacio alrededor de los campos de texto
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            modifier = Modifier.fillMaxWidth(),
            text = "INICIAR SESIÓN",
            fontFamily = readexProBold,
            fontSize = 28.sp,
            textAlign = TextAlign.Center,
            color = PrimaryColor
        )

        Spacer(modifier = Modifier.height(20.dp)) // Espacio entre el título y los campos de texto

        EmailTextField(
            value = email,
            onValueChange = { email = it },
            label = "Correo Electrónico",
            textFieldColors = textFieldColors,
        )

        Spacer(modifier = Modifier.height(10.dp))

        PasswordTextField(
            value = password,
            onValueChange = { password = it },
            label = "Contraseña",
            textFieldColors = textFieldColors,
            passwordVisible = passwordVisible,
            onPasswordVisibilityChange = { passwordVisible = !passwordVisible },
        )

        Spacer(modifier = Modifier.height(20.dp))

        CustomButton(onClick = { onLoginSuccess() }, text = "Ingresar") // Botón personalizado para iniciar sesión
    }
}