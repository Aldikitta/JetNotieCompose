package com.aldikitta.crudnoteapp.feature_note.presentation.notes

import androidx.compose.animation.*
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.FilterList
import androidx.compose.material.icons.filled.Sort
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.aldikitta.crudnoteapp.feature_note.domain.util.NoteOrder
import com.aldikitta.crudnoteapp.feature_note.presentation.notes.components.NoteItem
import com.aldikitta.crudnoteapp.feature_note.presentation.notes.components.OrderSection
import com.aldikitta.crudnoteapp.feature_note.presentation.util.Screen
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NotesScreen(
    navController: NavController,
    viewModel: NotesViewModel = hiltViewModel()
) {
    val state = viewModel.state.value
    val scope = rememberCoroutineScope()

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(text = "Your Note")
                },
                actions = {
                    if (state.isOrderSectionVisible) {
                        IconButton(
                            onClick = { viewModel.onEvent(NotesEvent.ToggleOrderSection) }
                        ) {
                            Icon(
                                imageVector = Icons.Filled.FilterList,
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.primary
                            )
                        }
                    } else {
                        IconButton(
                            onClick = { viewModel.onEvent(NotesEvent.ToggleOrderSection) }
                        ) {
                            Icon(
                                imageVector = Icons.Filled.FilterList,
                                contentDescription = null
                            )
                        }
                    }

                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    navController.navigate(Screen.AddEditNoteScreen.route)
                }
            ) {
                Icon(
                    imageVector = Icons.Filled.Add,
                    contentDescription = null
                )
            }
        }
    ) { innerPadding ->
        Column(modifier = Modifier.fillMaxSize()) {
            AnimatedVisibility(
                modifier = Modifier.padding(innerPadding),
                visible = state.isOrderSectionVisible,
                enter = fadeIn() + slideInVertically(),
                exit = fadeOut() + slideOutVertically()
            ) {
                OrderSection(
                    onOrderChange = { viewModel.onEvent(NotesEvent.Order(it)) },
                    noteOrder = state.noteOrder,
                    modifier = Modifier.fillMaxWidth()
                )
            }
            LazyColumn(modifier = Modifier.fillMaxSize().padding(innerPadding)) {
                items(state.notes) { note ->
                    NoteItem(
                        note = note,
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                            navController.navigate(
                                Screen.AddEditNoteScreen.route +
                                        "?noteId=${note.id}&noteColor=${note.color}"
                            )
                            },
                        onDeleteClick = {
                            viewModel.onEvent(NotesEvent.DeleteNote(note))
                            scope.launch {
//                                val result = scaffoldState.snackbarHostState.showSnackbar(
//                                    message = "Note deleted",
//                                    actionLabel = "Undo"
//                                )
//                                if(result == SnackbarResult.ActionPerformed) {
//                                    viewModel.onEvent(NotesEvent.RestoreNote)
//                                }
                            }
                        }
                    )
                }
            }
        }
    }
}