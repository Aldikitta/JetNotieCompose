import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.aldikitta.crudnoteapp.feature_note.domain.model.Note
import com.aldikitta.crudnoteapp.feature_note.presentation.notes.*
import com.aldikitta.crudnoteapp.feature_note.presentation.util.Screen
import com.aldikitta.crudnoteapp.ui.theme.spacing
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun NoteItemGrid(
    state: NotesUiState,
    paddingValues: PaddingValues,
    navController: NavController,
    viewModel: NotesViewModel = hiltViewModel(),
    snackbarHostState: SnackbarHostState? = null
) {
    val scope = rememberCoroutineScope()
    Column(
        modifier = Modifier
            .verticalScroll(rememberScrollState())
            .padding(paddingValues)
    ) {
        CustomStaggeredVerticalGrid(
            numColumns = 2,
        ) {
            state.notes.forEach { note ->
                var time = note.timeStamp
                var times = SimpleDateFormat("MMM d, yyyy")
                var date = Date(time)
                var final = times.format(date)
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(MaterialTheme.spacing.small)
                            .clickable {
                                navController.navigate(
                                    Screen.AddEditNoteScreen.route +
                                            "?noteId=${note.id}&noteColor=${note.color}"
                                ) {

                                }
                            },
                    ) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .size(MaterialTheme.spacing.small)
                                .background(Color(note.color))
                        )
                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .background(MaterialTheme.colorScheme.inverseOnSurface)
                                .padding(MaterialTheme.spacing.small)
                        ) {
                            Text(
                                text = note.title,
                                style = MaterialTheme.typography.titleLarge,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.padding(bottom = MaterialTheme.spacing.small)
                            )
                            Text(
                                text = note.content,
                                style = MaterialTheme.typography.titleMedium,
                                modifier = Modifier.padding(bottom = MaterialTheme.spacing.small)
                            )
                            Divider(modifier = Modifier.padding(top = MaterialTheme.spacing.small))
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically

                            ) {
                                Text(
                                    text = final, style = MaterialTheme.typography.titleMedium,
                                    color = MaterialTheme.colorScheme.outline
                                )
                                IconButton(
                                    onClick = {
                                        viewModel.onEvent(NotesEvent.DeleteNote(note))
                                        scope.launch {
                                            val result = snackbarHostState!!.showSnackbar(
                                                message = "${note.title} will be deleted!",
                                                actionLabel = "Undo"
                                            )
                                            if (result == SnackbarResult.ActionPerformed) {
                                                viewModel.onEvent(NotesEvent.RestoreNote)
                                            }
                                        }
                                    },
                                ) {
                                    Icon(
                                        imageVector = Icons.Filled.Delete,
                                        contentDescription = null
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun CustomStaggeredVerticalGrid(
    // on below line we are specifying
    // parameters as modifier, num of columns
    modifier: Modifier = Modifier,
    numColumns: Int = 2,
    content: @Composable () -> Unit
) {
    // inside this grid we are creating
    // a layout on below line.
    Layout(
        // on below line we are specifying
        // content for our layout.
        content = content,
        // on below line we are adding modifier.
        modifier = modifier
    ) { measurable, constraints ->
        // on below line we are creating a variable for our column width.
        val columnWidth = (constraints.maxWidth / numColumns)

        // on the below line we are creating and initializing our items constraint widget.
        val itemConstraints = constraints.copy(maxWidth = columnWidth)

        // on below line we are creating and initializing our column height
        val columnHeights = IntArray(numColumns) {
            0
        }

        // on below line we are creating and initializing placebles
        val placeables = measurable.map { measurable ->
            // inside placeble we are creating
            // variables as column and placebles.
            val column = testColumn(columnHeights)
            val placeable = measurable.measure(itemConstraints)

            // on below line we are increasing our column height/
            columnHeights[column] += placeable.height
            placeable
        }

        // on below line we are creating a variable for
        // our height and specifying height for it.
        val height =
            columnHeights.maxOrNull()?.coerceIn(constraints.minHeight, constraints.maxHeight)
                ?: constraints.minHeight

        // on below line we are specifying height and width for our layout.
        layout(
            width = constraints.maxWidth,
            height = height
        ) {
            // on below line we are creating a variable for column y pointer.
            val columnYPointers = IntArray(numColumns) { 0 }

            // on below line we are setting x and y for each placeable item
            placeables.forEach { placeable ->
                // on below line we are calling test
                // column method to get our column index
                val column = testColumn(columnYPointers)

                placeable.place(
                    x = columnWidth * column,
                    y = columnYPointers[column]
                )

                // on below line we are setting
                // column y pointer and incrementing it.
                columnYPointers[column] += placeable.height
            }
        }
    }
}

// on below line we are creating a test column method for setting height.
private fun testColumn(columnHeights: IntArray): Int {
    // on below line we are creating a variable for min height.
    var minHeight = Int.MAX_VALUE

    // on below line we are creating a variable for column index.
    var columnIndex = 0

    // on below line we are setting column  height for each index.
    columnHeights.forEachIndexed { index, height ->
        if (height < minHeight) {
            minHeight = height
            columnIndex = index
        }
    }
    // at last we are returning our column index.
    return columnIndex
}


