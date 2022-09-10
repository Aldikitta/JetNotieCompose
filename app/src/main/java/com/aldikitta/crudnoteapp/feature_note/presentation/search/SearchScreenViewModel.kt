//package com.aldikitta.crudnoteapp.feature_note.presentation.search
//
//import SearchScreenUIState
//import androidx.compose.runtime.getValue
//import androidx.compose.runtime.mutableStateOf
//import androidx.compose.runtime.setValue
//import androidx.lifecycle.ViewModel
//import androidx.lifecycle.viewModelScope
//import com.aldikitta.crudnoteapp.feature_note.domain.model.Note
//import com.aldikitta.crudnoteapp.feature_note.domain.use_case.NoteUseCases
//import com.aldikitta.crudnoteapp.feature_note.presentation.notes.NotesEvent
//import com.aldikitta.crudnoteapp.feature_note.presentation.notes.NotesUiState
//import com.aldikitta.crudnoteapp.feature_note.presentation.util.RequestState
//import dagger.hilt.android.lifecycle.HiltViewModel
//import kotlinx.coroutines.*
//import kotlinx.coroutines.flow.*
//import javax.inject.Inject
//
//@HiltViewModel
//class SearchScreenViewModel @Inject constructor(
//    private val noteUseCases: NoteUseCases
//) : ViewModel() {
//    private val _searhedNote = MutableStateFlow<RequestState<List<Note>>>(RequestState.Idle)
//    val searhedNote: StateFlow<RequestState<List<Note>>> = _searhedNote
//
//    private val _allNotes = MutableStateFlow<RequestState<List<Note>>>(RequestState.Idle)
//    val allNotes: StateFlow<RequestState<List<Note>>> = _allNotes
//
//    var notesUiState by mutableStateOf(SearchScreenUIState())
//        private set
//
//    private val _state = MutableStateFlow(NotesUiState())
//    val state: StateFlow<NotesUiState> = _state
//
//    private var searchTasksJob: Job? = null
//
//    fun onEvent(event: SearchEvent) {
//        when (event) {
//            is SearchEvent.Order -> {
////                if (state.value.noteOrder::class == event.noteOrder::class &&
////                    state.value.noteOrder.orderType == event.noteOrder.orderType
////                ) {
////                    return
////                }
////                getNotes(event.noteOrder)
//            }
//            is SearchEvent.SearchTasks ->{
//                viewModelScope.launch {
//                    searchNoteImpl(event.query)
//                }
//            }
//        }
//    }
//
//    fun searchNote(searchQuery: String) {
//        _searhedNote.value = RequestState.Loading
//        try {
//            viewModelScope.launch {
//                noteUseCases.searchNoteUseCase(searchQuery = "%$searchQuery%")
//                    .collect { searchedNoteResult ->
//                        _searhedNote.value = RequestState.Success(searchedNoteResult)
//                    }
//            }
//        } catch (e: Exception) {
//            _searhedNote.value = RequestState.Error(e)
//        }
//    }
//    private suspend fun searchNoteImpl(searchQuery: String){
//        searchTasksJob?.cancel()
//        searchTasksJob = noteUseCases.searchNoteUseCase(searchQuery).onEach { notes ->
//            notesUiState = notesUiState.copy(
//                searchNote = notes
//            )
//        }.launchIn(viewModelScope)
//    }
//}