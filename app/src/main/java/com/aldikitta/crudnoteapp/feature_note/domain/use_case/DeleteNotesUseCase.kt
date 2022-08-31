package com.aldikitta.crudnoteapp.feature_note.domain.use_case

import com.aldikitta.crudnoteapp.feature_note.domain.model.Note
import com.aldikitta.crudnoteapp.feature_note.domain.repository.NoteRepository

class DeleteNotesUseCase(
    private val repository: NoteRepository
) {
    suspend operator fun invoke(note: Note){
        repository.deleteNote(note)
    }
}