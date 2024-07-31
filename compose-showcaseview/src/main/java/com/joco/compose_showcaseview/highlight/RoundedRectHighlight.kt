package com.joco.compose_showcaseview.highlight

import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.layout.LayoutCoordinates
import androidx.compose.ui.layout.boundsInParent
import androidx.compose.ui.layout.boundsInRoot
import com.joco.compose_showcaseview.highlightPaddingPx

/**
 * Draws a rounded rectangle highlight around the target element.
 *
 * @param coordinates The coordinates of the target element.
 * @param cornerRadius The corner radius of the rounded rectangle.
 */
internal fun DrawScope.roundedRectHighlight(
    coordinates: LayoutCoordinates,
    cornerRadius: Float
) {
    val targetRect = coordinates.boundsInRoot()
    val xOffset = targetRect.topLeft.x
    val yOffset = targetRect.topLeft.y
    val rectSize = coordinates.boundsInParent().size

    drawRoundRect(
        color = Color.White,
        size = Size(
            rectSize.width + (highlightPaddingPx * 2),
            rectSize.height + (highlightPaddingPx * 2)
        ),
        topLeft = Offset(xOffset - highlightPaddingPx, yOffset - highlightPaddingPx),
        blendMode = BlendMode.Clear,
        cornerRadius = CornerRadius(cornerRadius, cornerRadius)
    )
}