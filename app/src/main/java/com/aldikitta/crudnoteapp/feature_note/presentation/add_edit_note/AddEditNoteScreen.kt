package com.aldikitta.crudnoteapp.feature_note.presentation.add_edit_note

import android.content.res.Configuration
import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.rounded.Done
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.ExperimentalLifecycleComposeApi
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.aldikitta.crudnoteapp.feature_note.domain.model.Note
import com.aldikitta.crudnoteapp.feature_note.presentation.add_edit_note.components.TransparentTextField
import com.aldikitta.crudnoteapp.feature_note.presentation.notes.NotesEvent
import com.aldikitta.crudnoteapp.feature_note.presentation.notes.NotesViewModel
import com.aldikitta.crudnoteapp.ui.theme.spacing
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

@OptIn(
    ExperimentalMaterial3Api::class, ExperimentalLifecycleComposeApi::class,
)
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

    val noteScreenViewModel: NotesViewModel = hiltViewModel()
    val noteState by noteScreenViewModel.state.collectAsStateWithLifecycle()

    val noteBackgroundAnimateable = remember {
        Animatable(
            Color((if (noteColor != -1) noteColor else noteColorState))
        )
    }
    val scope = rememberCoroutineScope()
    var expanded by remember { mutableStateOf(false) }
    val configuration = LocalConfiguration.current

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
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(text = "New Notes", fontWeight = FontWeight.Bold)
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
                        Text(
                            text = "Save",
                            color = noteBackgroundAnimateable.value,
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.Medium
                        )

                    }

                }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = MaterialTheme.spacing.medium)
        ) {
            when (configuration.orientation) {
                Configuration.ORIENTATION_LANDSCAPE -> {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = "Pick Note colors",
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.Medium
                        )
                        OutlinedButton(
                            onClick = { expanded = true },
                            colors = ButtonDefaults.buttonColors(containerColor = noteBackgroundAnimateable.value)
                        ) {
                            if (expanded) {
                                Text(text = "Hide", color = MaterialTheme.colorScheme.onPrimary,)
                            } else {
                                Text(text = "Show", color = MaterialTheme.colorScheme.onPrimary,)
                            }
                            DropdownMenu(
                                expanded = expanded,
                                onDismissRequest = { expanded = false }) {
                                Note.noteColors.forEach { color ->
                                    val colorInt = color.toArgb()
                                    DropdownMenuItem(
                                        text = { /*TODO*/ },
                                        onClick = {
                                            scope.launch {
                                                noteBackgroundAnimateable.animateTo(
                                                    targetValue = Color(colorInt),
                                                    animationSpec = tween(300)
                                                )
                                            }
                                            viewModel.onEvent(AddEditNoteEvent.ChangeColor(colorInt))
                                        },
                                        leadingIcon = {
                                            Box(modifier = Modifier
                                                .size(MaterialTheme.spacing.medium)
                                                .clip(CircleShape)
                                                .background(color)
                                                .clickable {
                                                    scope.launch {
                                                        noteBackgroundAnimateable.animateTo(
                                                            targetValue = Color(colorInt),
                                                            animationSpec = tween(300)
                                                        )
                                                    }
                                                    viewModel.onEvent(
                                                        AddEditNoteEvent.ChangeColor(
                                                            colorInt
                                                        )
                                                    )
                                                }
                                            )
                                        },
                                        trailingIcon = {
                                            if (viewModel.noteColor.value == colorInt) {
                                                Icon(
                                                    imageVector = Icons.Rounded.Done,
                                                    contentDescription = null,
                                                    modifier = Modifier
                                                        .fillMaxSize()
                                                        .padding(MaterialTheme.spacing.small),
                                                    tint = Color.White
                                                )
                                            }
                                        }
                                    )
                                    Divider()
                                }
                            }
                        }
                    }
                    Spacer(modifier = Modifier.height(MaterialTheme.spacing.medium))
                }
                else -> {
                    Spacer(modifier = Modifier.height(MaterialTheme.spacing.small))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = "Pick Note colors",
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.Medium
                        )
                        OutlinedButton(
                            onClick = { noteScreenViewModel.onEvent(NotesEvent.ToggleOrderSection) },
                            border = BorderStroke(width = ButtonDefaults.outlinedButtonBorder.width, color = noteBackgroundAnimateable.value),
                        ) {
                            if (noteState.isOrderSectionVisible) {
                                Text(text = "Hide")
                            } else {
                                Text(text = "Show")
                            }

                        }
                    }
                    AnimatedVisibility(
                        visible = noteState.isOrderSectionVisible,
                        enter = fadeIn() + slideInVertically(),
                        exit = fadeOut() + slideOutVertically()
                    ) {
                        Row(
                            modifier = Modifier
                                .padding(vertical = MaterialTheme.spacing.small)
                                .fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceEvenly
                        ) {
                            Note.noteColors.forEach { color ->
                                val colorInt = color.toArgb()
                                Box(modifier = Modifier
                                    .padding(end = MaterialTheme.spacing.extraSmall)
                                    .size(MaterialTheme.spacing.mediumLarge)
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
                                ) {
                                    if (viewModel.noteColor.value == colorInt) {
                                        Icon(
                                            imageVector = Icons.Rounded.Done,
                                            contentDescription = null,
                                            modifier = Modifier
                                                .align(Alignment.Center)
                                                .fillMaxSize()
                                                .padding(MaterialTheme.spacing.small),
                                            tint = Color.White
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }
            Spacer(modifier = Modifier.height(MaterialTheme.spacing.medium))
            Text(
                text = dayOfMonth,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Medium,
                color = MaterialTheme.colorScheme.outline
            )
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
                focusColor = noteBackgroundAnimateable.value,
                textStyle = MaterialTheme.typography.titleLarge
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
                },
                textStyle = MaterialTheme.typography.titleMedium
            )
        }
    }
}