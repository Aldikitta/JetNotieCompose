package com.aldikitta.crudnoteapp.feature_note.presentation.search

import com.aldikitta.crudnoteapp.feature_note.domain.model.Note
import com.aldikitta.crudnoteapp.feature_note.domain.util.NoteOrder

sealed class SearchEvent {
    data class Order(val noteOrder: NoteOrder) : SearchEvent()
    data class SearchTasks(val query: String) : SearchEvent()
}