package com.joco.compose_showcaseview

sealed interface ShowcaseDisplayState {
    data object Appeared : ShowcaseDisplayState
    data object Disappeared : ShowcaseDisplayState
}