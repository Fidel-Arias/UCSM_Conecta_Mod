package org.ucsmconecta.mod.interfaceApp

import android.content.Intent
import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalWindowInfo
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.resources.painterResource
import org.ucsmconecta.mod.activities.AsistantActivity
import org.ucsmconecta.mod.components.body.BodyLogin
import org.ucsmconecta.mod.components.body.BodyModeradores
import org.ucsmconecta.mod.components.header.HeaderLogin
import ucsmconectamod.composeapp.generated.resources.Res
import ucsmconectamod.composeapp.generated.resources.campusUcsmTarde

@Composable
actual fun LoginApp() {
    val context = LocalContext.current
    val painter = painterResource(Res.drawable.campusUcsmTarde)
    val headerHeight = 80.dp

    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    var mostrarLogin by remember { mutableStateOf(false) }

    val windowSize = LocalWindowInfo.current.containerSize
    val density = LocalDensity.current
    val screenWidth = with(density) { windowSize.width.toDp() }

    val offsetX by animateDpAsState(
        targetValue = if (mostrarLogin) -screenWidth else 0.dp,
        label = "slideLeft"
    )


    BackHandler(enabled = mostrarLogin) {
        mostrarLogin = false
    }

    MaterialTheme {
        Scaffold(
            snackbarHost = { SnackbarHost(snackbarHostState) },
        ) { innerPadding ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
            ) {
                Image(
                    painter = painter,
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )

                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.Black.copy(alpha = 0.5f))
                )

                HeaderLogin()

                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .offset(x = offsetX)
                        .padding(top = headerHeight)
                ) {
                    BodyModeradores(
                        scope = scope,
                        onCardSelected = {
                            mostrarLogin = true
                        }
                    )
                }

                AnimatedVisibility(
                    visible = mostrarLogin,
                    enter = fadeIn(animationSpec = tween(300)),
                    exit = fadeOut(animationSpec = tween(300)),
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(top = headerHeight)
                ) {
                    BodyLogin(
                        onLoginSuccess = {
                            context.startActivity(Intent(context, AsistantActivity::class.java))
                        }
                    )
                }
            }
        }

    }
}