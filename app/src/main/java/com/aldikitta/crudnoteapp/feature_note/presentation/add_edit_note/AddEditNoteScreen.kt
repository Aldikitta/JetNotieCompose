package com.aldikitta.crudnoteapp.feature_note.presentation.add_edit_note

import androidx.compose.animation.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.ExperimentalLifecycleComposeApi
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.aldikitta.crudnoteapp.feature_note.domain.model.Note
import com.aldikitta.crudnoteapp.feature_note.presentation.add_edit_note.components.TransparentTextField
import com.aldikitta.crudnoteapp.ui.theme.spacing
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

@OptIn(ExperimentalMaterial3Api::class, ExperimentalLifecycleComposeApi::class)
@Composable
fun AddEditNoteScreen(
    navController: NavController,
    noteColor: Int,
    viewModel: AddEditNoteViewModel = hiltViewModel()
) {
    val month = SimpleDateFormat("EEE, MMM d")
    val dayOfMonth = month.format(Date())

    val titleState by viewModel.noteTitle.collectAsStateWithLifecycle()
    val contentState by viewModel.noteContent.collectAsStateWithLifecycle()
    val noteColorState by viewModel.noteColor.collectAsStateWithLifecycle()

    val noteBackgroundAnimateable = remember {
        Animatable(
            Color((if (noteColor != -1) noteColor else noteColorState))
        )
    }
    val scope = rememberCoroutineScope()

    LaunchedEffect(key1 = true) {
        viewModel.eventFlow.collectLatest { event ->
            when (event) {
                is AddEditNoteViewModel.UiEvent.ShowSnackbar -> {

                }
                is AddEditNoteViewModel.UiEvent.SaveNote -> {
                    navController.navigateUp()
                }
            }
        }
    }
    Scaffold(
//        floatingActionButton = {
//            FloatingActionButton(
//                onClick = { viewModel.onEvent(AddEditNoteEvent.SaveNote) },
//                containerColor = noteBackgroundAnimateable.value
//            ) {
//                Icon(imageVector = Icons.Default.Save, contentDescription = "Save note")
//            }
//        },
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(text = "Notes", fontWeight = FontWeight.Bold)
                },
                navigationIcon = {
                    IconButton(onClick = { navController.navigateUp() }) {
                        Icon(imageVector = Icons.Filled.ArrowBack, contentDescription = "back")
                    }
                },
                actions = {
                    TextButton(onClick = {
                        viewModel.onEvent(AddEditNoteEvent.SaveNote)
                    }) {
                        Text(text = "Save", color = noteBackgroundAnimateable.value, style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Medium)
                    }
                }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(MaterialTheme.spacing.small)
        ) {
            Text(
                text = "Priority",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Medium
            )
            Row(
                modifier = Modifier
                    .padding(vertical = MaterialTheme.spacing.small)
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Note.noteColors.forEach { color ->
                    val colorInt = color.toArgb()
                    Box(modifier = Modifier
                        .weight(1f)
                        .padding(horizontal = MaterialTheme.spacing.extraSmall)
                        .size(MaterialTheme.spacing.mediumLarge)
                        .clip(MaterialTheme.shapes.large)
                        .background(color)
                        .clickable {
                            scope.launch {
                                noteBackgroundAnimateable.animateTo(
                                    targetValue = Color(colorInt),
                                    animationSpec = tween(300)
                                )
                            }
                            viewModel.onEvent(AddEditNoteEvent.ChangeColor(colorInt))
                        }
                        .border(
                            width = 3.dp,
                            color = if (viewModel.noteColor.value == colorInt) {
                                MaterialTheme.colorScheme.primary
                            } else Color.Transparent,
                            shape = RoundedCornerShape(MaterialTheme.spacing.medium)
                        )
                    )
                }
            }
            Row(
                modifier = Modifier
                    .padding(vertical = MaterialTheme.spacing.small)
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "Date", style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Medium
                )
                Text(
                    text = dayOfMonth, style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Medium
                )
            }
            TransparentTextField(
                modifier = Modifier.padding(vertical = MaterialTheme.spacing.medium),
                text = titleState.text,
                onValueChange = { viewModel.onEvent(AddEditNoteEvent.EnteredTitle(it)) },
                onFocusChange = {
                    viewModel.onEvent(AddEditNoteEvent.ChangeTitleFocus(it))
                },
                singleline = true,
                placeholder = {
                    Text(text = titleState.hint, style = MaterialTheme.typography.titleLarge)
                },
                focusColor = noteBackgroundAnimateable.value
            )
            Spacer(modifier = Modifier.height(MaterialTheme.spacing.small))
            TransparentTextField(
                text = contentState.text,
                onValueChange = { viewModel.onEvent(AddEditNoteEvent.EnteredContent(it)) },
                onFocusChange = {
                    viewModel.onEvent(AddEditNoteEvent.ChangeContentFocus(it))
                },
                modifier = Modifier.fillMaxHeight(),
                focusColor = noteBackgroundAnimateable.value,
                placeholder = {
                    Text(text = contentState.hint, style = MaterialTheme.typography.titleMedium)
                }
            )
        }
    }
}