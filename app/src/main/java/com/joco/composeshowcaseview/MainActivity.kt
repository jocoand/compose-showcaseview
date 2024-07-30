package com.joco.composeshowcaseview

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.LayoutCoordinates
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.composed
import com.joco.compose_showcaseview.ShowcaseView
import com.joco.compose_showcaseview.highlight.circularHighlight
import com.joco.composeshowcaseview.ui.theme.ComposeShowcaseViewTheme

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ComposeShowcaseViewTheme {
                val targets = remember { mutableStateMapOf<String, LayoutCoordinates>() }
                var visibleShowcase by remember { mutableStateOf<VisibleShowcase>(VisibleShowcase.None) }

                Box(modifier = Modifier.fillMaxSize()) {
                    Scaffold(
                        topBar = {
                            TopAppBar(
                                title = { Text(text = "Showcase App") },
                                colors = TopAppBarDefaults.topAppBarColors(
                                    containerColor = MaterialTheme.colorScheme.primary,
                                    titleContentColor = Color.White
                                ),
                            )
                        }
                    ) { innerPadding ->
                        Surface(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(innerPadding),
                            color = MaterialTheme.colorScheme.background
                        ) {
                            val loremIpsumText = """
    Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.
"""
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(16.dp)
                            ) {
                                Greeting(
                                    modifier = Modifier
                                        .align(Alignment.End)
                                        .onGloballyPositioned { coordinates ->
                                            targets[KEY_GREETING] = coordinates
                                        },
                                    name = "Andy",
                                    onClick = { visibleShowcase = VisibleShowcase.Greetings }
                                )
                                Spacer(modifier = Modifier.height(60.dp))
                                Article(
                                    modifier = Modifier
                                        .align(Alignment.Start)
                                        .onGloballyPositioned { coordinates ->
                                            targets[KEY_ARTICLE] = coordinates
                                        }
                                        .noRippleClickable {
                                            visibleShowcase = VisibleShowcase.ArticleTitle
                                        },
                                    subheading = "Today's Article",
                                    paragraph = loremIpsumText
                                )
                                Row(modifier = Modifier.align(Alignment.CenterHorizontally)) {
                                    LikeButton(
                                        modifier = Modifier
                                            .onGloballyPositioned { coordinates ->
                                                targets[KEY_LIKE] = coordinates
                                            }
                                            .noRippleClickable {
                                                visibleShowcase = VisibleShowcase.Like
                                            }
                                    )
                                    Spacer(modifier = Modifier.width(16.dp))
                                    ShareButton(
                                        modifier = Modifier
                                            .onGloballyPositioned { coordinates ->
                                                targets[KEY_SHARE] = coordinates
                                            }
                                            .noRippleClickable {
                                                visibleShowcase = VisibleShowcase.Share
                                            }
                                    )
                                }
                            }
                        }
                    }

                    targets[KEY_GREETING]?.let { coordinates ->
                        ShowcaseView(
                            visible = visibleShowcase == VisibleShowcase.Greetings,
                            targetCoordinates = coordinates,
                        ) {
                            MyShowcaseDialog(
                                text = "Hey, this is Greetings showcase",
                                onClick = { visibleShowcase = VisibleShowcase.None }
                            )
                        }
                    }

                    targets[KEY_ARTICLE]?.let { coordinates ->
                        ShowcaseView(
                            visible = visibleShowcase == VisibleShowcase.ArticleTitle,
                            targetCoordinates = coordinates,
                        ) {
                            MyShowcaseDialog(
                                text = "Yuhuu!\nYou can read awesome article here",
                                onClick = { visibleShowcase = VisibleShowcase.None }
                            )
                        }
                    }

                    targets[KEY_LIKE]?.let { coordinates ->
                        ShowcaseView(
                            visible = visibleShowcase == VisibleShowcase.Like,
                            targetCoordinates = coordinates,
                            drawHighlight = { drawStarHighlight(coordinates, 5) },
                        ) {
                            MyShowcaseDialog(
                                modifier = Modifier.offset(y = 32.dp),
                                text = "Click to Like the article",
                                onClick = { visibleShowcase = VisibleShowcase.None }
                            )
                        }
                    }

                    targets[KEY_SHARE]?.let { coordinates ->
                        ShowcaseView(
                            visible = visibleShowcase == VisibleShowcase.Share,
                            targetCoordinates = coordinates,
                            drawHighlight = { circularHighlight(coordinates) },
                        ) {
                            MyShowcaseDialog(
                                modifier = Modifier.offset(y = 32.dp),
                                text = "Click to Share the article",
                                onClick = { visibleShowcase = VisibleShowcase.None }
                            )
                        }
                    }
                }
            }
        }
    }

    @Composable
    fun Greeting(modifier: Modifier = Modifier, name: String, onClick: () -> Unit) {
        Text(
            text = "Hello, $name!",
            modifier = modifier.noRippleClickable { onClick() },
        )
    }

    @Composable
    fun Article(
        modifier: Modifier = Modifier,
        subheading: String,
        paragraph: String
    ) {
        Column(modifier = modifier.padding(horizontal = 16.dp, vertical = 8.dp)) {
            Text(
                text = subheading,
                style = MaterialTheme.typography.headlineSmall,
                color = MaterialTheme.colorScheme.primary,
                fontWeight = FontWeight.W500
            )
            Text(
                text = paragraph,
                style = MaterialTheme.typography.labelMedium,
                color = MaterialTheme.colorScheme.secondary
            )
        }
    }

    @Composable
    fun LikeButton(modifier: Modifier = Modifier) {
        Row(modifier = modifier, verticalAlignment = Alignment.CenterVertically) {
            Icon(
                imageVector = Icons.Default.Favorite,
                contentDescription = "Like Icon",
                tint = Color.Gray
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = "Love It!",
                style = MaterialTheme.typography.labelMedium,
                color = MaterialTheme.colorScheme.primary
            )
        }
    }

    @Composable
    fun ShareButton(modifier: Modifier = Modifier) {
        Row(modifier = modifier, verticalAlignment = Alignment.CenterVertically) {
            Icon(
                imageVector = Icons.Default.Share,
                contentDescription = "Share Icon",
                tint = Color.Gray
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = "Share",
                style = MaterialTheme.typography.labelMedium,
                color = MaterialTheme.colorScheme.primary
            )
        }
    }

    @Composable
    fun MyShowcaseDialog(modifier: Modifier = Modifier, text: String, onClick: () -> Unit) {
        Column(
            modifier = modifier
                .background(Color.White, shape = RoundedCornerShape(12.dp))
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.End
        ) {
            Text(text = text)
            Spacer(modifier = Modifier.height(8.dp))
            Button(
                shape = RoundedCornerShape(8.dp),
                onClick = onClick
            ) {
                Text("Nice !")
            }
        }
    }

    fun Modifier.noRippleClickable(onClick: () -> Unit) = composed { this.clickable(
        indication = null,
        interactionSource = remember { MutableInteractionSource() },
        onClick = onClick
    ) }

    companion object {
        private const val KEY_GREETING = "KEY_GREETING"
        private const val KEY_ARTICLE = "KEY_ARTICLE"
        private const val KEY_LIKE = "KEY_LIKE"
        private const val KEY_SHARE = "KEY_SHARE"
    }
}