package com.aldikitta.crudnoteapp.feature_note.presentation.notes.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
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
//            horizontalArrangement = Arrangement.Center
        ) {
            DefaultRadioButton(
                text = "Title",
                selected = noteOrder is NoteOrder.Title,
                onSelect = { onOrderChange(NoteOrder.Title(noteOrder.orderType)) }
            )
            DefaultRadioButton(
                text = "Date",
                selected = noteOrder is NoteOrder.Date,
                onSelect = { onOrderChange(NoteOrder.Date(noteOrder.orderType)) }
            )
            DefaultRadioButton(
                text = "Color",
                selected = noteOrder is NoteOrder.Color,
                onSelect = { onOrderChange(NoteOrder.Color(noteOrder.orderType)) }
            )
        }
        Row(
            modifier = Modifier.fillMaxWidth(),
        ) {
            DefaultRadioButton(
                text = "Ascending",
                selected = noteOrder.orderType is OrderType.Ascending,
                onSelect = { onOrderChange(noteOrder.copy(OrderType.Ascending)) }
            )
            DefaultRadioButton(
                text = "Descending",
                selected = noteOrder.orderType is OrderType.Descending,
                onSelect = { onOrderChange(noteOrder.copy(OrderType.Descending)) }
            )
        }
    }
}

@Composable
fun PortraitOrderSection(
    modifier: Modifier = Modifier,
    noteOrder: NoteOrder = NoteOrder.Date(OrderType.Descending),
    onOrderChange: (NoteOrder) -> Unit
) {
    Column(
    ) {
        DefaultRadioButton(
            text = "Title",
            selected = noteOrder is NoteOrder.Title,
            onSelect = { onOrderChange(NoteOrder.Title(noteOrder.orderType)) }
        )
        DefaultRadioButton(
            text = "Date",
            selected = noteOrder is NoteOrder.Date,
            onSelect = { onOrderChange(NoteOrder.Date(noteOrder.orderType)) }
        )
        DefaultRadioButton(
            text = "Color",
            selected = noteOrder is NoteOrder.Color,
            onSelect = { onOrderChange(NoteOrder.Color(noteOrder.orderType)) }
        )
        Spacer(modifier = Modifier.height(MaterialTheme.spacing.medium))
        Text(
            text = "Order by",
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .padding(MaterialTheme.spacing.medium)
        )
        DefaultRadioButton(
            text = "Ascending",
            selected = noteOrder.orderType is OrderType.Ascending,
            onSelect = { onOrderChange(noteOrder.copy(OrderType.Ascending)) }
        )
        DefaultRadioButton(
            text = "Descending",
            selected = noteOrder.orderType is OrderType.Descending,
            onSelect = { onOrderChange(noteOrder.copy(OrderType.Descending)) }
        )
        Spacer(modifier = Modifier.height(MaterialTheme.spacing.medium))
    }
}