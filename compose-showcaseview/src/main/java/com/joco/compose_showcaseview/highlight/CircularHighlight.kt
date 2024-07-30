package com.joco.compose_showcaseview.highlight

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.layout.LayoutCoordinates
import androidx.compose.ui.layout.boundsInParent
import androidx.compose.ui.layout.boundsInRoot
import com.joco.compose_showcaseview.highlightPaddingPx

/**
 * Draws a circular highlight around the target element.
 *
 * @param coordinates The coordinates of the target element.
 */
fun DrawScope.circularHighlight(
    coordinates: LayoutCoordinates
) {
    val targetRect = coordinates.boundsInRoot()
    val xOffset = targetRect.topLeft.x
    val yOffset = targetRect.topLeft.y
    val rectSize = coordinates.boundsInParent().size
    drawCircle(
        color = Color.White,
        radius = (rectSize.width / 2) + highlightPaddingPx,
        center = Offset(xOffset + rectSize.width / 2, yOffset + rectSize.height / 2),
        blendMode = BlendMode.Clear
    )
}