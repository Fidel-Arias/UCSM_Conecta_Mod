package org.ucsmconecta.mod.components.navbar

import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import org.ucsmconecta.mod.components.icons.getIconCookie
import org.ucsmconecta.mod.components.icons.getIconEditDocument
import org.ucsmconecta.mod.components.icons.getIconLogout
import org.ucsmconecta.mod.components.icons.getIconQRScanner
import org.ucsmconecta.mod.ui.theme.TertiaryColor

@Composable
fun NavBarStructure(
    selectedItem: Int,
    onItemSelected: (Int) -> Unit
) {

    NavigationBar(
        containerColor = Color.White
    ) {
        NavigationBarItem(
            icon = {
                Icon(
                    imageVector = getIconQRScanner(),
                    contentDescription = "Escanear QR"
                )
            },
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = Color.Black,
                unselectedIconColor = Color.Gray,
                selectedTextColor = Color.Black,
                unselectedTextColor = Color.Gray,
                indicatorColor = TertiaryColor
            ),
            label = {
                Text(
                    text = "Escanear QR",
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            },
            selected = selectedItem == 0,
            onClick = { onItemSelected(0) }
        )
        NavigationBarItem(
            icon = {
                Icon(
                    imageVector = getIconEditDocument(),
                    contentDescription = "Registrar"
                )
            },
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = Color.Black,
                unselectedIconColor = Color.Gray,
                selectedTextColor = Color.Black,
                unselectedTextColor = Color.Gray,
                indicatorColor = TertiaryColor
            ),
            label = {
                Text(
                    text = "Registrar",
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            },
            selected = selectedItem == 1,
            onClick = { onItemSelected(1) }
        )
        NavigationBarItem(
            icon = {
                Icon(
                    imageVector = getIconCookie(),
                    contentDescription = "Refrigerio"
                )
            },
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = Color.Black,
                unselectedIconColor = Color.Gray,
                selectedTextColor = Color.Black,
                unselectedTextColor = Color.Gray,
                indicatorColor = TertiaryColor
            ),
            label = {
                Text(
                    text = "Refrigerio",
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            },
            selected = selectedItem == 2,
            onClick = { onItemSelected(2) }
        )
        NavigationBarItem(
            icon = {
                Icon(
                    imageVector = getIconLogout(),
                    contentDescription = "Salir"
                )
            },
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = Color.Black,
                unselectedIconColor = Color.Gray,
                selectedTextColor = Color.Black,
                unselectedTextColor = Color.Gray,
                indicatorColor = TertiaryColor
            ),
            label = {
                Text(
                    text = "Salir",
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            },
            selected = selectedItem == 3,
            onClick = { onItemSelected(3) }
        )
    }
}