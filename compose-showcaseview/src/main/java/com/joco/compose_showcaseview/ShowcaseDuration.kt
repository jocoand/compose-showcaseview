package com.joco.compose_showcaseview

/**
 * Class that represents the duration of  animations in the `ShowcaseView`.
 *
 * @property enterMillis The duration of the  animation in milliseconds.
 * @property exitMillis The duration of the animation in milliseconds.
 */
class ShowcaseDuration(val enterMillis: Int, val exitMillis: Int) {
    companion object {
        val Default = ShowcaseDuration(700, 700)
    }
}