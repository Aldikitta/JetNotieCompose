package com.aldikitta.crudnoteapp.feature_note.data.data_source

import androidx.room.*
import com.aldikitta.crudnoteapp.feature_note.domain.model.Note
import kotlinx.coroutines.flow.Flow

@Dao
interface NoteDao {
    @Query("SELECT * FROM note")
    fun getNotes(): Flow<List<Note>>

    @Query("SELECT * FROM note WHERE id = :id")
    suspend fun getNoteById(id: Int): Note?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNote(note: Note)

    @Delete
    suspend fun deleteNote(note: Note)

//    @Query("SELECT * FROM note WHERE title = :query")
//    fun searchNote(query: String): Flow<List<Note>>

    @Query("SELECT * FROM note WHERE title LIKE :searchQuery OR content LIKE :searchQuery")
    fun searchNote(searchQuery: String): Flow<List<Note>>
}