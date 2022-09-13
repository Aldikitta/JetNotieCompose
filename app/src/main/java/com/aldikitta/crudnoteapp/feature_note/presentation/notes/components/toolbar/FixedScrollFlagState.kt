package com.aldikitta.crudnoteapp.feature_note.presentation.notes.components.toolbar

abstract class FixedScrollFlagState(heightRange: IntRange) : ScrollFlagState(heightRange) {

    final override val offset: Float = 0f

}