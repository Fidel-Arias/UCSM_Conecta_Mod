package org.ucsmconecta.mod.components.navbar

import android.app.Activity
import android.content.Intent
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.ucsmconecta.mod.activities.LoginActivity
import org.ucsmconecta.mod.utils.getTokenStorage

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
                CoroutineScope(Dispatchers.IO).launch {
                    getTokenStorage().clear()
                }
                (context as? Activity)?.let { activity ->
                    activity.startActivity(Intent(activity, LoginActivity::class.java))
                    activity.finish()
                }
            } else {
                onItemSelected(newItem)
            }
        }
    )
}