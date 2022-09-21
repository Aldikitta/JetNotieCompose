package com.aldikitta.crudnoteapp.feature_note.presentation.notes.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import com.aldikitta.crudnoteapp.feature_note.domain.util.NoteOrder
import com.aldikitta.crudnoteapp.feature_note.domain.util.OrderType
import com.aldikitta.crudnoteapp.ui.theme.spacing

@Composable
fun OrderSection(
    modifier: Modifier = Modifier,
    noteOrder: NoteOrder = NoteOrder.Date(OrderType.Descending),
    onOrderChange: (NoteOrder) -> Unit
) {
    Column(modifier = modifier.padding(MaterialTheme.spacing.small)) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            ChipsOrderFilter(
                selected = noteOrder is NoteOrder.Title,
                onClick = { onOrderChange(NoteOrder.Title(noteOrder.orderType)) },
                title = "Title"
            )
            ChipsOrderFilter(
                selected = noteOrder is NoteOrder.Date,
                onClick = { onOrderChange(NoteOrder.Date(noteOrder.orderType)) },
                title = "Date"
            )
            ChipsOrderFilter(
                selected = noteOrder is NoteOrder.Color,
                onClick = { onOrderChange(NoteOrder.Color(noteOrder.orderType)) },
                title = "Color"
            )
        }
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            ChipsOrderFilter(
                selected = noteOrder.orderType is OrderType.Ascending,
                onClick = { onOrderChange(noteOrder.copy(OrderType.Ascending))},
                title = "Ascending"
            )
            ChipsOrderFilter(
                selected = noteOrder.orderType is OrderType.Descending,
                onClick = { onOrderChange(noteOrder.copy(OrderType.Descending))},
                title = "Descending"
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChipsOrderFilter(
    modifier: Modifier = Modifier,
    selected: Boolean,
    onClick: () -> Unit,
    title: String,
) {
    FilterChip(
        modifier = modifier.padding(end = MaterialTheme.spacing.small),
        selected = selected,
        onClick = onClick,
        label = {
            Text(text = title)
        },
        leadingIcon = {
            if (selected) {
                Icon(
                    imageVector = Icons.Filled.Done,
                    contentDescription = "Localized Description",
                )
            }
        }
    )
}