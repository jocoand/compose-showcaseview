package com.joco.compose_showcaseview.highlight

import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.layout.LayoutCoordinates
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

/**
 * Provides functions to create highlights for the showcase.
 */
object ShowcaseHighlight {
    fun roundedRect(cornerRadius: Dp = 8.dp): DrawScope.(LayoutCoordinates) -> Unit =
        { roundedRectHighlight(it, cornerRadius.toPx()) }

    val circular: DrawScope.(LayoutCoordinates) -> Unit = { circularHighlight(it) }
}