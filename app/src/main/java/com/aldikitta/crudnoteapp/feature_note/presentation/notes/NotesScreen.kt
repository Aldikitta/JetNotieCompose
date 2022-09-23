package com.aldikitta.crudnoteapp.feature_note.presentation.notes

import android.content.res.Configuration
import androidx.activity.compose.BackHandler
import androidx.compose.animation.core.FloatExponentialDecaySpec
import androidx.compose.animation.core.animateDecay
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Velocity
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.ExperimentalLifecycleComposeApi
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.aldikitta.crudnoteapp.feature_note.presentation.notes.components.NoteItemGrid
import com.aldikitta.crudnoteapp.feature_note.presentation.notes.components.OrderSection
import com.aldikitta.crudnoteapp.feature_note.presentation.notes.components.PortraitOrderSection
import com.aldikitta.crudnoteapp.feature_note.presentation.notes.components.toolbar.CollapsingToolbar
import com.aldikitta.crudnoteapp.feature_note.presentation.notes.components.toolbar.ExitUntilCollapsedState
import com.aldikitta.crudnoteapp.feature_note.presentation.notes.components.toolbar.ToolbarState
import com.aldikitta.crudnoteapp.feature_note.presentation.util.Screen
import com.aldikitta.crudnoteapp.ui.theme.spacing
import kotlinx.coroutines.cancelChildren
import kotlinx.coroutines.launch

private val MinToolbarHeight = 80.dp
private val MaxToolbarHeight = 230.dp

@OptIn(
    ExperimentalMaterial3Api::class, ExperimentalLifecycleComposeApi::class,
    ExperimentalMaterialApi::class
)
@Composable
fun NotesScreen(
    navController: NavController,
    viewModel: NotesViewModel = hiltViewModel(),
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    val sheetState = rememberModalBottomSheetState(
        initialValue = ModalBottomSheetValue.Hidden,
        confirmStateChange = { it != ModalBottomSheetValue.HalfExpanded }
    )

    BackHandler(sheetState.isVisible) {
        scope.launch { sheetState.hide() }
    }

    val getNoteCount = state.notes.size

    val toolbarHeightRange = with(LocalDensity.current) {
        MinToolbarHeight.roundToPx()..MaxToolbarHeight.roundToPx()
    }
    val toolbarState = rememberToolbarState(toolbarHeightRange)
    val listState = rememberLazyListState()
    val configuration = LocalConfiguration.current

    val nestedScrollConnection = remember {
        object : NestedScrollConnection {
            override fun onPreScroll(available: Offset, source: NestedScrollSource): Offset {
                toolbarState.scrollTopLimitReached =
                    listState.firstVisibleItemIndex == 0 && listState.firstVisibleItemScrollOffset == 0
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
                        ) { value, _ ->
                            toolbarState.scrollTopLimitReached =
                                listState.firstVisibleItemIndex == 0 && listState.firstVisibleItemScrollOffset == 0
                            toolbarState.scrollOffset =
                                toolbarState.scrollOffset - (value - (toolbarState.height + toolbarState.offset))
                            if (toolbarState.scrollOffset == 0f) scope.coroutineContext.cancelChildren()
                        }
                    }
                }

                return super.onPostFling(consumed, available)
            }
        }
    }
    ModalBottomSheetLayout(
        modifier = Modifier.fillMaxSize(),
        sheetBackgroundColor = MaterialTheme.colorScheme.background,
        sheetState = sheetState,
        sheetContent = {
            Divider(
                modifier = Modifier
                    .width(MaterialTheme.spacing.extraLarge)
                    .align(Alignment.CenterHorizontally)
                    .padding(vertical = MaterialTheme.spacing.medium),
                thickness = 4.dp
            )
            Text(
                text = "Sort by",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .padding(MaterialTheme.spacing.medium)
            )

            when (configuration.orientation) {
                Configuration.ORIENTATION_LANDSCAPE -> {
                    OrderSection(
                        onOrderChange = { viewModel.onEvent(NotesEvent.Order(it)) },
                        noteOrder = state.noteOrder,
                        modifier = Modifier
                            .fillMaxWidth()
                            .fillMaxHeight(0.5f)
                            .navigationBarsPadding()
                    )
                }
                else ->{
                    PortraitOrderSection(
                        onOrderChange = { viewModel.onEvent(NotesEvent.Order(it)) },
                        noteOrder = state.noteOrder,
                        modifier = Modifier
                            .fillMaxWidth()
                            .fillMaxHeight(0.2f)
                            .navigationBarsPadding()
                    )
                }
            }
        }) {
        Scaffold(
            modifier = Modifier.nestedScroll(nestedScrollConnection),
            snackbarHost = { SnackbarHost(snackbarHostState) },
            topBar = {
                CollapsingToolbar(
                    progress = toolbarState.progress,
                    onSortClicked = {
                        scope.launch {
                            if (sheetState.isVisible) {
                                sheetState.hide()
                            } else {
                                sheetState.show()
                            }
                        }
                    },
                    onSearchClicked = { navController.navigate(Screen.SearchScreen.route) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .statusBarsPadding()
                        .height(with(LocalDensity.current) { toolbarState.height.toDp() })
                        .graphicsLayer { translationY = toolbarState.offset },
                    noteCount = getNoteCount
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
            Column(modifier = Modifier.fillMaxSize().padding(MaterialTheme.spacing.small)) {
                NoteItemGrid(
                    state = state,
                    paddingValues = innerPadding,
                    navController = navController,
                    snackbarHostState = snackbarHostState
                )
            }
        }
    }
}

@Composable
private fun rememberToolbarState(toolbarHeightRange: IntRange): ToolbarState {
    return rememberSaveable(saver = ExitUntilCollapsedState.Saver) {
        ExitUntilCollapsedState(toolbarHeightRange)
    }
}
