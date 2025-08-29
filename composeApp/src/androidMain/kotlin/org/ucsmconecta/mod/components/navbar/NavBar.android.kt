package org.ucsmconecta.mod.components.navbar

import android.app.Activity
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext

@Composable
actual fun NavBar(
    selectedItemNavbar: Int,
    onItemSelected: (Int) -> Unit
) {
    val context = LocalContext.current
    NavBarStructure(
        selectedItem = selectedItemNavbar,
        onItemSelected = { newItem ->
            if (newItem == 3) {
                (context as? Activity)?.finish()
            } else {
                onItemSelected(newItem)
            }
        }
    )
}