package com.aldikitta.crudnoteapp.feature_note.presentation.notes

import NoteItemGrid
import androidx.compose.animation.*
import androidx.compose.animation.core.FloatExponentialDecaySpec
import androidx.compose.animation.core.animateDecay
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.R
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.FilterList
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Velocity
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.ExperimentalLifecycleComposeApi
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.aldikitta.crudnoteapp.feature_note.presentation.notes.components.OrderSection
import com.aldikitta.crudnoteapp.feature_note.presentation.notes.components.toolbar.CollapsingToolbar
import com.aldikitta.crudnoteapp.feature_note.presentation.notes.components.toolbar.ExitUntilCollapsedState
import com.aldikitta.crudnoteapp.feature_note.presentation.notes.components.toolbar.ToolbarState
import com.aldikitta.crudnoteapp.feature_note.presentation.util.Screen
import kotlinx.coroutines.cancelChildren
import kotlinx.coroutines.launch

private val MinToolbarHeight = 80.dp
private val MaxToolbarHeight = 230.dp
@OptIn(ExperimentalMaterial3Api::class, ExperimentalLifecycleComposeApi::class)
@Composable
fun NotesScreen(
    navController: NavController,
    viewModel: NotesViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val scope = rememberCoroutineScope()


    val toolbarHeightRange = with(LocalDensity.current) {
        MinToolbarHeight.roundToPx()..MaxToolbarHeight.roundToPx()
    }
    val toolbarState = rememberToolbarState(toolbarHeightRange)
    val listState = rememberLazyListState()

    val nestedScrollConnection = remember {
        object : NestedScrollConnection {
            override fun onPreScroll(available: Offset, source: NestedScrollSource): Offset {
                toolbarState.scrollTopLimitReached = listState.firstVisibleItemIndex == 0 && listState.firstVisibleItemScrollOffset == 0
                toolbarState.scrollOffset = toolbarState.scrollOffset - available.y
                return Offset(0f, toolbarState.consumed)
            }

            override suspend fun onPostFling(consumed: Velocity, available: Velocity): Velocity {
                if (available.y > 0) {
                    scope.launch {
                        animateDecay(
                            initialValue = toolbarState.height + toolbarState.offset,
                            initialVelocity = available.y,
                            animationSpec = FloatExponentialDecaySpec()
                        ) { value, velocity ->
                            toolbarState.scrollTopLimitReached = listState.firstVisibleItemIndex == 0 && listState.firstVisibleItemScrollOffset == 0
                            toolbarState.scrollOffset = toolbarState.scrollOffset - (value - (toolbarState.height + toolbarState.offset))
                            if (toolbarState.scrollOffset == 0f) scope.coroutineContext.cancelChildren()
                        }
                    }
                }

                return super.onPostFling(consumed, available)
            }
        }
    }

    Scaffold(
        modifier = Modifier.nestedScroll(nestedScrollConnection),
        topBar = {
            CollapsingToolbar(
//                backgroundImageResId = com.aldikitta.crudnoteapp.R.drawable.toolbar_background,
                progress = toolbarState.progress,
                onSortClicked = {viewModel.onEvent(NotesEvent.ToggleOrderSection)},
                onSearchClicked = {navController.navigate(Screen.SearchScreen.route)},
                modifier = Modifier
                    .fillMaxWidth()
                    .statusBarsPadding()
                    .height(with(LocalDensity.current) { toolbarState.height.toDp() })
                    .graphicsLayer { translationY = toolbarState.offset }
            )
//            CenterAlignedTopAppBar(
//                title = {
//                    Text(text = "Your Note")
//                },
//                actions = {
//                    if (state.isOrderSectionVisible) {
//                        IconButton(
//                            onClick = { viewModel.onEvent(NotesEvent.ToggleOrderSection) }
//                        ) {
//                            Icon(
//                                imageVector = Icons.Filled.FilterList,
//                                contentDescription = null,
//                                tint = MaterialTheme.colorScheme.primary
//                            )
//                        }
//                    } else {
//                        IconButton(
//                            onClick = { viewModel.onEvent(NotesEvent.ToggleOrderSection) }
//                        ) {
//                            Icon(
//                                imageVector = Icons.Filled.FilterList,
//                                contentDescription = null
//                            )
//                        }
//                    }
//                    IconButton(onClick = { navController.navigate(Screen.SearchScreen.route) }) {
//                        Icon(imageVector = Icons.Filled.Search, contentDescription = null)
//                    }
//                }
//            )
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

@Composable
private fun rememberToolbarState(toolbarHeightRange: IntRange): ToolbarState {
    return rememberSaveable(saver = ExitUntilCollapsedState.Saver) {
        ExitUntilCollapsedState(toolbarHeightRange)
    }
}
