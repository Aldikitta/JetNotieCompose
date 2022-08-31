package com.aldikitta.crudnoteapp.di

import android.app.Application
import androidx.room.Room
import com.aldikitta.crudnoteapp.feature_note.data.data_source.NoteDatabase
import com.aldikitta.crudnoteapp.feature_note.data.repository.NoteRepositoryImpl
import com.aldikitta.crudnoteapp.feature_note.domain.repository.NoteRepository
import com.aldikitta.crudnoteapp.feature_note.domain.use_case.AddNoteUseCase
import com.aldikitta.crudnoteapp.feature_note.domain.use_case.DeleteNotesUseCase
import com.aldikitta.crudnoteapp.feature_note.domain.use_case.GetNotesUseCase
import com.aldikitta.crudnoteapp.feature_note.domain.use_case.NoteUseCases
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object FeatureNoteModule {

    @Provides
    @Singleton
    fun provideNoteDatabase(app: Application): NoteDatabase {
        return Room.databaseBuilder(
            app,
            NoteDatabase::class.java,
            NoteDatabase.DATABASE_NAME
        ).build()
    }

    @Provides
    @Singleton
    fun provideNoteRepository(db: NoteDatabase): NoteRepository {
        return NoteRepositoryImpl(db.noteDao)
    }

    @Provides
    @Singleton
    fun provideNoteUseCases(repository: NoteRepository): NoteUseCases {
        return NoteUseCases(
            getNotes = GetNotesUseCase(repository),
            deleteNotes = DeleteNotesUseCase(repository),
            addNote = AddNoteUseCase(repository)
        )
    }
}