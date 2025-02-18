package com.joco.compose_showcaseview

/**
 * Class that represents the duration of  animations in the `ShowcaseView`.
 *
 * @property enterMillis The duration of the  animation in milliseconds.
 * @property exitMillis The duration of the animation in milliseconds.
 */
class ShowcaseDuration(val enterMillis: Int, val exitMillis: Int) {
    companion object {
        private const val DEFAULT_MILLIS = 700

        val Default = ShowcaseDuration(DEFAULT_MILLIS, DEFAULT_MILLIS)
    }
}