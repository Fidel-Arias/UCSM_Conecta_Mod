package org.ucsmconecta.mod.components.card

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.jetbrains.compose.resources.Font
import ucsmconectamod.composeapp.generated.resources.Anton
import ucsmconectamod.composeapp.generated.resources.Res


@Composable
fun cardCarreras(
    carrera: String,
    image: Painter,
    onclick: () -> Unit
) {
    val antonFont = FontFamily(Font(
        Res.font.Anton
    )) // Fuente Anton
    val careerImage = image // Imagen de la carrera

    Card(
        onClick = onclick,
        modifier = Modifier
            .fillMaxWidth()
            .height(115.dp)
            .border(
                width = 3.dp,
                color = Color.White,
                shape = RoundedCornerShape(10.dp)), // mismo shape que el Card
        shape = RoundedCornerShape(10.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize(),
            contentAlignment = Alignment.CenterStart
        ) {
            Image(
                painter = careerImage,
                contentDescription = "Imagen de $carrera",
                contentScale = ContentScale.Crop, // hace que sobresalga si se pasa
            )
            Text(
                text = carrera,
                fontSize = 22.sp,
                fontFamily = antonFont,
                color = Color.Black,
                modifier = Modifier
                    .offset(x = (-1).dp, y = (-1).dp)
                    .fillMaxWidth(0.7f)
                    .padding(10.dp)
            )
            Text(
                text = carrera,
                fontSize = 22.sp,
                fontFamily = antonFont,
                color = Color.Black,
                modifier = Modifier
                    .offset(x = (1).dp, y = (-1).dp)
                    .fillMaxWidth(0.7f)
                    .padding(10.dp)
            )
            Text(
                text = carrera,
                fontSize = 22.sp,
                fontFamily = antonFont,
                color = Color.Black,
                modifier = Modifier
                    .offset(x = (-1).dp, y = (1).dp)
                    .fillMaxWidth(0.7f)
                    .padding(10.dp)
            )
            Text(
                text = carrera,
                fontSize = 22.sp,
                fontFamily = antonFont,
                color = Color.Black,
                modifier = Modifier
                    .offset(x = (1).dp, y = (1).dp)
                    .fillMaxWidth(0.7f)
                    .padding(10.dp)
            )
            // texto principal en blanco, centrado
            Text(
                text = carrera,
                fontSize = 22.sp,
                fontFamily = antonFont,
                color = Color.White,
                modifier = Modifier
                    .fillMaxWidth(0.7f)
                    .padding(10.dp)
            )
        }
    }
}
