package com.aldikitta.crudnoteapp.feature_note.presentation.notes

import NoteItemGrid
import androidx.compose.animation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.FilterList
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.ExperimentalLifecycleComposeApi
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.aldikitta.crudnoteapp.feature_note.presentation.notes.components.OrderSection
import com.aldikitta.crudnoteapp.feature_note.presentation.util.Screen

@OptIn(ExperimentalMaterial3Api::class, ExperimentalLifecycleComposeApi::class)
@Composable
fun NotesScreen(
    navController: NavController,
    viewModel: NotesViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
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
                    IconButton(onClick = { navController.navigate(Screen.SearchScreen.route) }) {
                        Icon(imageVector = Icons.Filled.Search, contentDescription = null)
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
            NoteItemGrid(state = state, paddingValues = innerPadding, navController = navController)
        }
    }
}
