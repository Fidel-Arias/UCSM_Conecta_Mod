package org.ucsmconecta.mod.components.message

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import kotlinx.coroutines.delay
import org.ucsmconecta.mod.components.icons.getIconCheckCircle
import org.ucsmconecta.mod.components.icons.getIconError
import org.ucsmconecta.mod.ui.theme.ErrorColor
import org.ucsmconecta.mod.ui.theme.LightGreen

@Composable
fun MessageModal(
    message: String,
    isError: Boolean = false,
    icon: ImageVector = if (isError) getIconError() else getIconCheckCircle(),
    onDismiss: () -> Unit
) {
    // Control del tiempo de vida del modal
    LaunchedEffect(Unit) {
        delay(2000) // 3 segundos
        onDismiss()
    }

    Dialog(onDismissRequest = { onDismiss() }) {
        Card(
            modifier = Modifier
                .fillMaxWidth(0.85f)
                .padding(16.dp),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(
                containerColor = if (isError) Color(0xFFFFE6E6) else Color(0xFFE6FFEA)
            ),
            elevation = CardDefaults.cardElevation(8.dp)
        ) {
            Row(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = if (isError) Color(0xFFD32F2F) else Color(0xFF2E7D32),
                    modifier = Modifier.size(32.dp)
                )
                Spacer(modifier = Modifier.width(12.dp))
                Text(
                    text = message,
                    color = if (isError) Color(0xFFD32F2F) else Color(0xFF2E7D32),
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 16.sp
                )
            }
        }
    }
}