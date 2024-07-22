package com.joco.compose_showcaseview

class ShowcaseDuration(val enterMillis: Int, val exitMillis: Int) {
    companion object {
        val Default = ShowcaseDuration(700, 700)
    }
}