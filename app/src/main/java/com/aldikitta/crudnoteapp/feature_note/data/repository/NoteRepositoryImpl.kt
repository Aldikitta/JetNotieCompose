package com.aldikitta.crudnoteapp.feature_note.data.repository

import com.aldikitta.crudnoteapp.feature_note.data.data_source.NoteDao
import com.aldikitta.crudnoteapp.feature_note.domain.model.Note
import com.aldikitta.crudnoteapp.feature_note.domain.repository.NoteRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow

class NoteRepositoryImpl(
    private val dao: NoteDao
) : NoteRepository {
//    var searchResult = MutableStateFlow<List<Note>>()
    override fun getNotes(): Flow<List<Note>> {
        return dao.getNotes()
    }

    override suspend fun getNoteById(id: Int): Note? {
        return dao.getNoteById(id)
    }

    override suspend fun insertNote(note: Note) {
        return dao.insertNote(note)
    }

    override suspend fun deleteNote(note: Note) {
        return dao.deleteNote(note)
    }

    override suspend fun searchNote(searchQuery: String): Flow<List<Note>> {
        return dao.searchNote(searchQuery)
    }
}