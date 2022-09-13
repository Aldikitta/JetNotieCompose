package com.aldikitta.crudnoteapp.feature_note.presentation.search

import NoteItemGrid
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.ExperimentalLifecycleComposeApi
import androidx.navigation.NavController
import com.aldikitta.crudnoteapp.feature_note.presentation.notes.NotesEvent
import com.aldikitta.crudnoteapp.feature_note.presentation.notes.NotesViewModel

@OptIn(ExperimentalLifecycleComposeApi::class, ExperimentalMaterial3Api::class)
@Composable
fun SearchScreen(
    navController: NavController,
    viewModel: NotesViewModel = hiltViewModel(),
) {
//    val searchedNote by viewModel.searhedNote.collectAsStateWithLifecycle()
    val state = viewModel.tasksUiState
    Column(
        modifier = Modifier.fillMaxSize(),
    ) {
        var query by rememberSaveable {
            mutableStateOf("")
        }
        LaunchedEffect(query) { viewModel.onEvent(NotesEvent.SearchNotes(query)) }
        val focusRequester = remember { FocusRequester() }
        LaunchedEffect(true) { focusRequester.requestFocus() }
        OutlinedTextField(
            value = query,
            onValueChange = { query = it },
            label = { Text(text = "Search") },
            shape = RoundedCornerShape(15.dp),
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .focusRequester(focusRequester)
        )
        NoteItemGrid(state = state, paddingValues = PaddingValues(), navController)
    }
}