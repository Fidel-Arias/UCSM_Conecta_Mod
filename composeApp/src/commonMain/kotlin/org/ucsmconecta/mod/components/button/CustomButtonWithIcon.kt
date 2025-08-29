package org.ucsmconecta.mod.components.button

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun CustomButtonWithIcon(
    text: String,
    icon: ImageVector,
    colorButton: Color,
    descriptionIcon: String? = null,
    sizeIcon: Int,
    onClick: () -> Unit,
    bold: Boolean = false,
    fontSize: Int,
    isRow: Boolean = true,
    space: Int = 0,
    roundedCorner: Int = 0,
) {
    Button(
        onClick = onClick,
        enabled = true,
        shape = RoundedCornerShape(roundedCorner.dp),
        modifier = Modifier
            .fillMaxWidth(),
        colors = ButtonDefaults.buttonColors(
            containerColor = colorButton,
            contentColor = Color.White
        )
    ) {
        if (isRow){
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = descriptionIcon,
                    tint = Color.White,
                    modifier = Modifier.size(sizeIcon.dp)
                )
                Spacer(Modifier.width(space.dp))
                Text(
                    text = text,
                    color = Color.White,
                    textAlign = TextAlign.Center,
                    fontSize = fontSize.sp,
                    fontWeight = if (bold) FontWeight.Bold else FontWeight.Normal,
                )
            }
        } else {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = descriptionIcon,
                    tint = Color.White,
                    modifier = Modifier.size(sizeIcon.dp)
                )
                Spacer(Modifier.height(space.dp))
                Text(
                    text = text,
                    color = Color.White,
                    textAlign = TextAlign.Center,
                    fontSize = fontSize.sp,
                    fontWeight = if (bold) FontWeight.Bold else FontWeight.Normal,
                    modifier = Modifier.fillMaxWidth(),
                    lineHeight = 25.sp
                )
            }
        }
    }
}