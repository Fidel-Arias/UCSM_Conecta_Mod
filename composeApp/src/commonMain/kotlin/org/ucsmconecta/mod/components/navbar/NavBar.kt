package org.ucsmconecta.mod.components.navbar

import androidx.compose.runtime.Composable

@Composable
expect fun NavBar(
    selectedItemNavbar: Int,
    onItemSelected: (Int) -> Unit
)