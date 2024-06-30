package com.joco.composeshowcaseview

sealed interface VisibleShowcase {
    data object Greetings : VisibleShowcase
    data object ArticleTitle : VisibleShowcase
    data object Like : VisibleShowcase
    data object Share : VisibleShowcase
    data object None : VisibleShowcase
}