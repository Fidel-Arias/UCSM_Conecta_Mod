package org.ucsmconecta.mod.components.titles

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.jetbrains.compose.resources.Font
import ucsmconectamod.composeapp.generated.resources.Res
import ucsmconectamod.composeapp.generated.resources.RobotoSlab_Bold

@Composable
fun TextConBorde(title: String) {
    val texto = title
    val robotoSlab = FontFamily(Font(Res.font.RobotoSlab_Bold))
    val estiloTexto = TextStyle(
        fontSize = 20.sp,
        fontWeight = FontWeight.Bold,
        textAlign = TextAlign.Center,
        fontFamily = robotoSlab,
        color = Color.White // color principal de la letra
    )

    Box {
        // Borde negro alrededor
        Text(
            text = texto.uppercase(),
            style = estiloTexto.copy(color = Color.Black),
            maxLines = 2,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier
                .fillMaxWidth()
                .offset(x = 1.dp, y = 1.dp)
        )
        Text(
            text = texto.uppercase(),
            style = estiloTexto.copy(color = Color.Black),
            maxLines = 2,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier
                .fillMaxWidth()
                .offset(x = -1.dp, y = 1.dp)
        )
        Text(
            text = texto.uppercase(),
            style = estiloTexto.copy(color = Color.Black),
            maxLines = 2,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier
                .fillMaxWidth()
                .offset(x = 1.dp, y = -1.dp)
        )
        Text(
            text = texto.uppercase(),
            style = estiloTexto.copy(color = Color.Black),
            maxLines = 2,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier
                .fillMaxWidth()
                .offset(x = -1.dp, y = -1.dp)
        )

        // Texto principal encima
        Text(
            text = texto.uppercase(),
            maxLines = 2,
            overflow = TextOverflow.Ellipsis,
            style = estiloTexto,
            modifier = Modifier
                .fillMaxWidth()
        )
    }
}
