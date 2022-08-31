package com.aldikitta.crudnoteapp.feature_note.domain.use_case

import com.aldikitta.crudnoteapp.feature_note.domain.model.Note
import com.aldikitta.crudnoteapp.feature_note.domain.model.invalidNoteException
import com.aldikitta.crudnoteapp.feature_note.domain.repository.NoteRepository
import kotlin.jvm.Throws

class AddNoteUseCase(private val noteRepository: NoteRepository) {

    @Throws(invalidNoteException::class)
    suspend operator fun invoke(note: Note){
        if (note.title.isBlank()){
            throw invalidNoteException("The title of the note can't be empty")
        }
        if (note.content.isBlank()){
            throw invalidNoteException("The content of the note can't be empty")
        }
        noteRepository.insertNote(note)
    }
}