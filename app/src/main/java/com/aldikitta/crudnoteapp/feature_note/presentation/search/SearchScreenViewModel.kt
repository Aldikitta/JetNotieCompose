package com.aldikitta.crudnoteapp.feature_note.presentation.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aldikitta.crudnoteapp.feature_note.domain.model.Note
import com.aldikitta.crudnoteapp.feature_note.domain.use_case.NoteUseCases
import com.aldikitta.crudnoteapp.feature_note.presentation.util.RequestState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@HiltViewModel
class SearchScreenViewModel @Inject constructor(
    private val noteUseCases: NoteUseCases
) : ViewModel() {
    private val _searhedNote = MutableStateFlow<RequestState<List<Note>>>(RequestState.Idle)
    val searhedNote: StateFlow<RequestState<List<Note>>> = _searhedNote

    private val _allNotes = MutableStateFlow<RequestState<List<Note>>>(RequestState.Idle)
    val allNotes: StateFlow<RequestState<List<Note>>> = _allNotes

    fun searchNote(searchQuery: String) {
        _searhedNote.value = RequestState.Loading
        try {
            viewModelScope.launch {
                noteUseCases.searchNoteUseCase(searchQuery = "%$searchQuery%")
                    .collect { searchedNoteResult ->
                        _searhedNote.value = RequestState.Success(searchedNoteResult)
                    }
            }
        } catch (e: Exception) {
            _searhedNote.value = RequestState.Error(e)
        }
    }
}