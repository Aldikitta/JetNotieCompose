package com.aldikitta.crudnoteapp.feature_note.presentation.notes

import com.aldikitta.crudnoteapp.feature_note.domain.model.Note
import com.aldikitta.crudnoteapp.feature_note.domain.util.NoteOrder
import com.aldikitta.crudnoteapp.feature_note.domain.util.OrderType

data class NotesUiState(
    val notes: List<Note> = emptyList(),
    val noteOrder: NoteOrder = NoteOrder.Date(OrderType.Descending),
    val isOrderSectionVisible: Boolean = true
)