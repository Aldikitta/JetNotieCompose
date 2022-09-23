package com.aldikitta.crudnoteapp.feature_note.presentation.search

import NoteItemGrid
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.ExperimentalLifecycleComposeApi
import androidx.navigation.NavController
import com.aldikitta.crudnoteapp.feature_note.domain.model.Note
import com.aldikitta.crudnoteapp.feature_note.presentation.notes.NotesEvent
import com.aldikitta.crudnoteapp.feature_note.presentation.notes.NotesViewModel
import com.aldikitta.crudnoteapp.ui.theme.spacing

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreen(
    navController: NavController,
    viewModel: NotesViewModel = hiltViewModel(),
) {
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
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .statusBarsPadding(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            IconButton(onClick = { navController.popBackStack() }) {
                Icon(imageVector = Icons.Filled.ArrowBack, contentDescription = "Back")
            }
            TextField(
                value = query,
                onValueChange = { query = it },
                placeholder = { Text(text = "Search title") },
                modifier = Modifier
                    .fillMaxWidth()
                    .focusRequester(focusRequester)
                    .padding(horizontal = MaterialTheme.spacing.small),
                singleLine = true,
                colors = TextFieldDefaults.textFieldColors(
                    containerColor = MaterialTheme.colorScheme.inverseOnSurface,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    disabledIndicatorColor = Color.Transparent
                ),
                shape = RoundedCornerShape(MaterialTheme.spacing.medium),

                trailingIcon = {
                    if (query.isNotEmpty()) {
                        IconButton(onClick = { query = "" }) {
                            Icon(imageVector = Icons.Filled.Close, contentDescription = "close")
                        }
                    }
                }
            )
        }
        NoteItemGrid(
            state = state,
            paddingValues = PaddingValues(top = MaterialTheme.spacing.small),
            navController,
        )
    }
}