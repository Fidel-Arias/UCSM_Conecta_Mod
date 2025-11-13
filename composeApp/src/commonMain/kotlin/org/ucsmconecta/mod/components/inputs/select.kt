package org.ucsmconecta.mod.components.inputs

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.Icon
import androidx.compose.material3.MenuAnchorType
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import org.ucsmconecta.mod.components.icons.getIconArrowDropDown
import org.ucsmconecta.mod.components.icons.getIconArrowDropUp
import org.ucsmconecta.mod.ui.theme.SoftGray

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomSelect(
    label: String,
    opciones: List<String>,
    onOptionSelected: (String) -> Unit,
    ancho: Float = 1f, // Ancho opcional para el selector
) {
    // Menú desplegable
    var selectedOption by rememberSaveable { mutableStateOf("") }
    var expanded by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier.fillMaxWidth(ancho),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        ExposedDropdownMenuBox(
            expanded = expanded,
            onExpandedChange = { expanded = !expanded },
        ) {
            TextField(
                value = selectedOption.ifEmpty { label },
                onValueChange = {},
                readOnly = true,
                label = {
                    Text(
                        text = label,
                        color = Color.Black,
                        fontWeight = FontWeight.Bold
                    )
                },
                textStyle = TextStyle(color = if (selectedOption.isEmpty()) Color.Gray else Color.Black),
                trailingIcon = {
                    Icon(
                        imageVector = if (expanded) getIconArrowDropUp() else getIconArrowDropDown(),
                        contentDescription = "Expandir"
                    )
                },
                colors = TextFieldDefaults.colors(
                    focusedTextColor = Color.Black,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    disabledIndicatorColor = Color.Transparent,
                    focusedContainerColor = SoftGray, // gris suave
                    unfocusedContainerColor = SoftGray
                ),
                modifier = Modifier
                    .menuAnchor()
                    .clip(RoundedCornerShape(12.dp))
                    .fillMaxWidth()
            )

            ExposedDropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false },
            ) {
                opciones.forEach { opcion ->
                    DropdownMenuItem(
                        text = { Text(opcion) },
                        onClick = {
                            selectedOption = opcion
                            expanded = false
                            onOptionSelected(opcion)
                        }
                    )
                }
            }
        }
    }
}