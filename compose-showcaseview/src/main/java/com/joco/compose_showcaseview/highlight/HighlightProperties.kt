package com.joco.compose_showcaseview.highlight

import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.layout.LayoutCoordinates

/**
 * Represents the highlight around the target element in the `ShowcaseView`.
 *
 * @property drawHighlight A lambda function that draws the highlight around the target element.
 */
class HighlightProperties internal constructor(
    val drawHighlight: DrawScope.(LayoutCoordinates) -> Unit,
    val highlightBounds: Rect
)