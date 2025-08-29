package org.ucsmconecta.mod.components.inputs

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.jetbrains.compose.resources.painterResource
import org.ucsmconecta.mod.ui.theme.ErrorColor
import ucsmconectamod.composeapp.generated.resources.ClosedEyes_icon
import ucsmconectamod.composeapp.generated.resources.Eye_icon
import ucsmconectamod.composeapp.generated.resources.Res
import ucsmconectamod.composeapp.generated.resources.arroba

@Composable
fun EmailTextField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    textFieldColors: TextFieldColors,
) {
    val ArrobaIcon = painterResource(Res.drawable.arroba)
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        placeholder = {Text(label)},
        singleLine = true,
        leadingIcon = {
            IconButton(
                onClick = {}
            ) {
                Image(
                    painter = ArrobaIcon,
                    contentDescription = "Email",
                    modifier = Modifier
                        .size(30.dp)
                )
            }
        },
        shape = RoundedCornerShape(16.dp),
        colors = textFieldColors,
        modifier = Modifier
            .fillMaxWidth()
    )
}

@Composable
fun PasswordTextField(
    value: String,
    passwordVisible: Boolean,
    onValueChange: (String) -> Unit,
    onPasswordVisibilityChange: () -> Unit,
    label: String,
    textFieldColors: TextFieldColors,
) {
    // Iconos para los campos de texto
    val EyeIcon = painterResource(Res.drawable.Eye_icon)
    val ClosedEyeIcon = painterResource(Res.drawable.ClosedEyes_icon)

    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        singleLine = true,
        placeholder = {Text(label)},
        visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
        leadingIcon = {
            val image = if (passwordVisible) EyeIcon else ClosedEyeIcon
            val description = if (passwordVisible) "Ocultar contraseña" else "Mostrar contraseña"

            IconButton(onClick = onPasswordVisibilityChange) {
                Image(
                    painter = image,
                    contentDescription = description,
                    modifier = Modifier
                        .size(30.dp)
                )
            }
        },
        shape = RoundedCornerShape(16.dp),
        colors = textFieldColors,

        modifier = Modifier.fillMaxWidth()
    )
}

@Composable
fun InputTextField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    ancho: Float = 1f, // 👈 Ancho por defecto
    textFieldColors: TextFieldColors,
    maxLines: Int = 1,
    keyboardType: KeyboardType = KeyboardType.Text, // 👈 Texto por defecto
    isRequired: Boolean = false,
    enabled: Boolean = true,
    maxLength: Int? = null,
    isError: Boolean = false,
    errorMessage: String? = null
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.Center,
    ) {
        OutlinedTextField(
            value = value,
            onValueChange = { newValue ->
                // 👇 Si hay maxLength, se corta el texto
                if (maxLength == null || newValue.length <= maxLength) {
                    onValueChange(newValue)
                }
            },
            placeholder = {
                Text(
                    text = if (isRequired) "$label *" else label, // 👈 Indicador de requerido
                    fontWeight = if (isRequired) FontWeight.Bold else FontWeight.Normal
                )
            },
            singleLine = maxLines == 1,
            maxLines = maxLines,
            shape = RoundedCornerShape(16.dp),
            colors = textFieldColors,
            enabled = enabled,
            isError = isError,
            modifier = Modifier
                .fillMaxWidth(ancho)
                .align(Alignment.CenterHorizontally), // 👈 Alineación horizontal
            keyboardOptions = KeyboardOptions(
                keyboardType = keyboardType,
                imeAction = ImeAction.Next
            )
        )

        // 👇 Mensaje de error dinámico
        if (isError && errorMessage != null) {
            Text(
                text = errorMessage,
                color = ErrorColor,
                fontSize = 12.sp,
                textAlign = TextAlign.Left,
                modifier = Modifier
                    .padding(start = 4.dp, top = 2.dp) // 👈 Se alinea con borde del campo
                    .fillMaxWidth(ancho) // 👈 Respeta el mismo ancho que el campo
                    .align(Alignment.CenterHorizontally) // 👈 Para que "siga" al TextField
            )
        }
    }
}