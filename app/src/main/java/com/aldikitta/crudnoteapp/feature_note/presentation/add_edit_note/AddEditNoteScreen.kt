package com.aldikitta.crudnoteapp.feature_note.presentation.add_edit_note

import androidx.compose.animation.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Save
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.aldikitta.crudnoteapp.feature_note.domain.model.Note
import com.aldikitta.crudnoteapp.feature_note.presentation.add_edit_note.components.TransparentTextField
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddEditNoteScreen(
    navController: NavController,
    noteColor: Int,
    viewModel: AddEditNoteViewModel = hiltViewModel()
) {
    val titleState = viewModel.noteTitle.value
    val contentState = viewModel.noteContent.value

    val noteBackgroundAnimateable = remember {
        Animatable(
            Color((if (noteColor != -1) noteColor else viewModel.noteColor.value))
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
        floatingActionButton = {
            FloatingActionButton(
                onClick = { viewModel.onEvent(AddEditNoteEvent.SaveNote) }
            ) {
                Icon(imageVector = Icons.Default.Save, contentDescription = "Save note")
            }
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(noteBackgroundAnimateable.value)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Note.noteColors.forEach { color ->
                    val colorInt = color.toArgb()
                    Box(modifier = Modifier
                        .size(50.dp)
                        .clip(CircleShape)
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
                                Color.Black
                            } else Color.Transparent,
                            shape = CircleShape
                        )
                    )
                }
            }
            TransparentTextField(
                text = titleState.text,
                hint = titleState.hint,
                onValueChange = { viewModel.onEvent(AddEditNoteEvent.EnteredTitle(it)) },
                onFocusChange = {
                    viewModel.onEvent(AddEditNoteEvent.ChangeTitleFocus(it))
                },
                isHintVisible = titleState.isHintVisible,
                singleline = true
            )
            TransparentTextField(
                text = contentState.text,
                hint = contentState.hint,
                onValueChange = { viewModel.onEvent(AddEditNoteEvent.EnteredContent(it)) },
                onFocusChange = {
                    viewModel.onEvent(AddEditNoteEvent.ChangeContentFocus(it))
                },
                isHintVisible = contentState.isHintVisible,
                modifier = Modifier.fillMaxHeight()
            )
        }
    }
}