package org.ucsmconecta.mod.components.camera

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.dp

@Composable
fun MarcoCamera(modifier: Modifier) {
    Box(modifier = modifier, contentAlignment = Alignment.Center) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            val strokeWidth = 8.dp.toPx()
            val cornerLength = 40.dp.toPx()
            val radius = 16.dp.toPx() // redondeo del codo

            // ----- TOP-LEFT -----
            run {
                val path = Path().apply {
                    // horizontal hacia la esquina
                    moveTo(cornerLength, 0f)
                    lineTo(radius, 0f)
                    // arco: de TOP (270°) a LEFT (180°) => sweep -90°
                    arcTo(
                        Rect(0f, 0f, radius * 2, radius * 2),
                        270f, -90f, false
                    )
                    // vertical hacia abajo
                    lineTo(0f, cornerLength)
                }
                drawPath(
                    path,
                    Color.White,
                    style = Stroke(
                        width = strokeWidth,
                        cap = StrokeCap.Round,
                        join = StrokeJoin.Round
                    )
                )
            }

            // ----- TOP-RIGHT -----
            run {
                val path = Path().apply {
                    moveTo(size.width - cornerLength, 0f)
                    lineTo(size.width - radius, 0f)
                    // de TOP (270°) a RIGHT (0°) => +90°
                    arcTo(
                        Rect(size.width - radius * 2, 0f, size.width, radius * 2),
                        270f, 90f, false
                    )
                    lineTo(size.width, cornerLength)
                }
                drawPath(
                    path,
                    Color.White,
                    style = Stroke(
                        width = strokeWidth,
                        cap = StrokeCap.Round,
                        join = StrokeJoin.Round
                    )
                )
            }

            // ----- BOTTOM-LEFT -----
            run {
                val path = Path().apply {
                    moveTo(0f, size.height - cornerLength)
                    lineTo(0f, size.height - radius)
                    // de LEFT (180°) a BOTTOM (90°) => -90°
                    arcTo(
                        Rect(0f, size.height - radius * 2, radius * 2, size.height),
                        180f, -90f, false
                    )
                    lineTo(cornerLength, size.height)
                }
                drawPath(
                    path,
                    Color.White,
                    style = Stroke(
                        width = strokeWidth,
                        cap = StrokeCap.Round,
                        join = StrokeJoin.Round
                    )
                )
            }

            // ----- BOTTOM-RIGHT -----
            run {
                val path = Path().apply {
                    moveTo(size.width - cornerLength, size.height)
                    lineTo(size.width - radius, size.height)
                    // de BOTTOM (90°) a RIGHT (0°) => -90°
                    arcTo(
                        Rect(size.width - radius * 2, size.height - radius * 2, size.width, size.height),
                        90f, -90f, false
                    )
                    lineTo(size.width, size.height - cornerLength)
                }
                drawPath(
                    path,
                    Color.White,
                    style = Stroke(
                        width = strokeWidth,
                        cap = StrokeCap.Round,
                        join = StrokeJoin.Round
                    )
                )
            }
        }
    }
}



