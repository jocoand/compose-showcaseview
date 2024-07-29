package com.joco.compose_showcaseview

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.LayoutCoordinates
import androidx.compose.ui.layout.boundsInRoot
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import com.joco.compose_showcaseview.highlight.drawRoundedRectHighlight

/**
 * A Composable function that displays a dialog with a background overlay.
 * The dialog and overlay are animated to fade in when visible and fade out when not visible.
 *
 * @param visible Determines if the Showcase is visible or not.
 * @param targetCoordinates The coordinates of the target element that the Showcase is highlighting.
 * @param position The position of the dialog relative to the target element.
 * @param alignment The alignment of the dialog relative to the target element.
 * @param duration The duration of the fade in and fade out animation.
 * @param onDisplayStateChanged: A callback function that is invoked when the display state of the Showcase changes.
 * @param drawHighlight Draws the highlight around the target element.
 * @param dialog The content of the dialog.
 */
@Composable
fun ShowcaseView(
    visible: Boolean,
    targetCoordinates: LayoutCoordinates,
    position: ShowcasePosition = ShowcasePosition.Default,
    alignment: ShowcaseAlignment = ShowcaseAlignment.Default,
    duration: ShowcaseDuration = ShowcaseDuration.Default,
    onDisplayStateChanged: (ShowcaseDisplayState) -> Unit = {},
    drawHighlight: DrawScope.(LayoutCoordinates) -> Unit = { drawRoundedRectHighlight(it, 8.dp.toPx()) },
    dialog: @Composable () -> Unit
) {
    val transition =  remember { MutableTransitionState(false) }
    AnimatedVisibility(
        visibleState = transition,
        enter = fadeIn(tween(duration.enterMillis)),
        exit = fadeOut(tween(duration.exitMillis))
    ) {
        Box {
            ShowcaseBackground(
                coordinates = targetCoordinates,
                drawHighlight = { drawHighlight(targetCoordinates) }
            )
            ShowcaseDialog(
                targetRect = targetCoordinates.boundsInRoot(),
                position = position,
                alignment = alignment,
                content = dialog
            )
        }
    }
    LaunchedEffect(key1 = visible) {
        transition.targetState = visible
    }
    LaunchedEffect(key1 = transition.isIdle) {
        if (transition.isIdle) {
            if (transition.targetState) {
                onDisplayStateChanged(ShowcaseDisplayState.Appeared)
            } else {
                onDisplayStateChanged(ShowcaseDisplayState.Disappeared)
            }
        }
    }
}

/**
 * A Composable function that draws the background overlay and the highlight around the target element.
 *
 * @param coordinates The coordinates of the target element that the Showcase is highlighting.
 * @param drawHighlight draws the highlight around the target element.
 */
@Composable
private fun ShowcaseBackground(
    coordinates: LayoutCoordinates,
    drawHighlight: DrawScope.(LayoutCoordinates) -> Unit
) {
    Canvas(
        modifier = Modifier
            .fillMaxSize()
            .graphicsLayer(alpha = 0.6f)
    ) {
        // Overlay
        drawRect(
            Color.Black.copy(alpha = 0.6f),
            size = Size(size.width, size.height)
        )
        drawHighlight(coordinates)
    }
}

/**
 * A Composable function that positions and displays the dialog.
 *
 * @param targetRect The bounding rectangle of the target element.
 * @param position The position of the dialog relative to the target element.
 * @param alignment The alignment of the dialog relative to the target element.
 * @param content The content of the dialog.
 */
@Composable
private fun ShowcaseDialog(
    targetRect: Rect,
    position: ShowcasePosition,
    alignment: ShowcaseAlignment,
    content: @Composable () -> Unit
) {
    var offsetX by remember { mutableFloatStateOf(0f) }
    var offsetY by remember { mutableFloatStateOf(0f) }
    val configuration = LocalConfiguration.current

    val screenHeight = with(LocalDensity.current) {
        configuration.screenHeightDp.dp.toPx()
    }
    val screenWidth = with(LocalDensity.current) {
        configuration.screenWidthDp.dp.toPx()
    }
    Box(
        modifier = Modifier
            .offset(x = offsetX.toDp(), y = offsetY.toDp())
            .onGloballyPositioned {
                val contentHeight = it.size.height
                val contentWidth = it.size.width
                val extraSpacePx = 20 + highlightPaddingPx

                offsetX = when (alignment) {
                    ShowcaseAlignment.Start -> targetRect.left - highlightPaddingPx
                    ShowcaseAlignment.End -> targetRect.right - contentWidth + highlightPaddingPx
                    ShowcaseAlignment.CenterHorizontal -> (targetRect.center.x - contentWidth / 2) - (highlightPaddingPx / 2)
                    ShowcaseAlignment.Default -> {
                        if (targetRect.center.x > screenWidth / 2 + extraSpacePx) {
                            targetRect.right - contentWidth + highlightPaddingPx
                        } else {
                            targetRect.left - highlightPaddingPx
                        }
                    }
                }

                offsetY = when (position) {
                    ShowcasePosition.Top -> targetRect.top - contentHeight - extraSpacePx
                    ShowcasePosition.Bottom -> targetRect.bottom + extraSpacePx
                    ShowcasePosition.Default -> {
                        if (targetRect.center.y > screenHeight / 2 + extraSpacePx) {
                            targetRect.top - contentHeight - extraSpacePx
                        } else {
                            targetRect.bottom + extraSpacePx
                        }
                    }
                }
            }
    ) {
        content()
    }
}

@Composable
fun Float.toDp() = with(LocalDensity.current) {
    toDp()
}