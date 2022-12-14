package com.aldikitta.crudnoteapp.feature_note.domain.use_case

data class NoteUseCases(
    val getNotes: GetNotesUseCase,
    val deleteNotes: DeleteNotesUseCase,
    val addNote: AddNoteUseCase,
    val getNoteByIdUseCase: GetNoteByIdUseCase,
    val searchNoteUseCase: SearchNoteUseCase
)