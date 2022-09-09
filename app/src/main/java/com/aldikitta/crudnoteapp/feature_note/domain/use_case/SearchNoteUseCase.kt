package com.aldikitta.crudnoteapp.feature_note.domain.use_case

import com.aldikitta.crudnoteapp.feature_note.domain.model.Note
import com.aldikitta.crudnoteapp.feature_note.domain.repository.NoteRepository
import kotlinx.coroutines.flow.Flow

class SearchNoteUseCase(
    private val repository: NoteRepository
) {
    suspend operator fun invoke(searchQuery: String): Flow<List<Note>> {
        return repository.searchNote(searchQuery)
    }
}